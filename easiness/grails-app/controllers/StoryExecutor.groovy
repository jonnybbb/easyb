/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Sep 17, 2008
 * Time: 9:21:44 PM
 * To change this template use File | Settings | File Templates.
 */

import org.disco.easyb.ant.Report
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.disco.easyb.report.XmlReportWriter
import org.disco.easyb.ConsoleReporter
import org.disco.easyb.BehaviorRunner
import org.disco.easyb.domain.BehaviorFactory;


class StoryExecutor {


   def exec( Story s, def storyDir, def story_filename, def outputDir ) {

      def appCtx = [:]



      FileUtil.createDirIfNeeded(outputDir)

      def story_basename = FileUtil.getMixedCaseName(s.title)

      //def reports = [new Report(location:"${outputDir}/${story_file}.xml",format:"xml",type:"easyb")]

      def fileList = ["${outputDir}/${story_basename}.xml"]

      def reports = []

      fileList.each{  file ->

         reports << new XmlReportWriter( file )
      }


      def runner = new BehaviorRunner( reports, new ConsoleReporter())


      def behaviors = []

      String story_fullName = "${storyDir}/${story_filename}.story"

      //behaviors << BehaviorFactory.createBehavior(new GroovyShellConfiguration(), "${storyDir}/${story_filename}")
      behaviors << BehaviorFactory.createBehavior(new File(story_fullName))


      runner.runBehavior( behaviors )

      


//      BehaviorRunner br = new BehaviorRunner(reports, appCtx, ApplicationHolder.application);
//      br.runBehavior(testSource)




      
   }


}