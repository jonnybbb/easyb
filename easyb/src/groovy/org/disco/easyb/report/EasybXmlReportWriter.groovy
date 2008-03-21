package org.disco.easyb.report

import groovy.xml.MarkupBuilder
import org.disco.easyb.util.BehaviorStepType
import org.disco.easyb.BehaviorStep

class EasybXmlReportWriter implements ReportWriter {

  def report
  def listener

  EasybXmlReportWriter(inReport, inListener) {
    listener = inListener
    report = inReport
  }

  def buildFailureMessage(result) {
    def buff = new StringBuffer()
    for (i in 1..10) {
      // TODO needs better formatting ?
      buff << result.cause()?.getStackTrace()[i]
      buff << "\n"
    }
    return buff.toString()
  }

  def walkChildren(MarkupBuilder xml, BehaviorStep step) {
    if (step.childSteps.size() == 0) {
      if (step.result == null) {
        xml."${step.stepType.type()}"(name: step.name)
      } else {
        xml."${step.stepType.type()}"(name: step.name, result: step.result.status) {
          if (step.result.failed()) {
            failuremessage(buildFailureMessage(step.result))
          }
        }
      }
    } else {
      def stepSpecifications = step.childSteps.inject(0) {count, item -> count + item.getChildStepResultCount()}
      def stepFailedSpecifications = step.childSteps.inject(0) {count, item -> count + item.getChildStepFailureResultCount()}
      def stepPendingSpecifications = step.childSteps.inject(0) {count, item -> count + item.getChildStepPendingResultCount()}

      xml."${step.stepType.type()}"(name: step.name, specifications: stepSpecifications, failedspecifications: stepFailedSpecifications, pendingspecifications: stepPendingSpecifications) {
    	  if(step.description){
    		xml.description(step.description)
    	  }
    	  for (child in step.childSteps) {
          walkChildren(xml, child)
        }
      }
    }

  }

  public void writeReport() {

    Writer writer = new BufferedWriter(new FileWriter(new File(report.location)))


    def xml = new MarkupBuilder(writer)

    xml.EasybRun(time: new Date(), totalspecifications: listener.getSpecificationCount(), totalfailedspecifications: listener.getFailedSpecificationCount(), totalpendingspecifications: listener.getPendingSpecificationCount()) {
      def storyChildren = listener.genesisStep.getChildrenOfType(BehaviorStepType.STORY)
      def storyChildrenSpecifications = storyChildren.inject(0) {count, item -> count + item.getChildStepResultCount()}
      def storyChildrenFailedSpecifications = storyChildren.inject(0) {count, item -> count + item.getChildStepFailureResultCount()}
      def storyChildrenPendingSpecifications = storyChildren.inject(0) {count, item -> count + item.getChildStepPendingResultCount()}
      stories(specifications: storyChildrenSpecifications, failedspecifications: storyChildrenFailedSpecifications, pendingspecifications: storyChildrenPendingSpecifications) {
        listener.genesisStep.getChildrenOfType(BehaviorStepType.STORY).each {genesisChild ->
          walkChildren(xml, genesisChild)
        }
      }
      def specificationChildren = listener.genesisStep.getChildrenOfType(BehaviorStepType.SPECIFICATION)
      def specificationChildrenSpecifications = specificationChildren.inject(0) {count, item -> count + item.getChildStepResultCount()}
      def specificationChildrenFailedSpecifications = specificationChildren.inject(0) {count, item -> count + item.getChildStepFailureResultCount()}
      def specificationChildrenPendingSpecifications = specificationChildren.inject(0) {count, item -> count + item.getChildStepPendingResultCount()}
      specifications(specifications: specificationChildrenSpecifications, failedspecifications: specificationChildrenFailedSpecifications, pendingspecifications: specificationChildrenPendingSpecifications) {
        listener.genesisStep.getChildrenOfType(BehaviorStepType.SPECIFICATION).each {genesisChild ->
          walkChildren(xml, genesisChild)
        }
      }
    }
    writer.close()
  }

}