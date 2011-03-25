package org.easyb.report

import groovy.text.SimpleTemplateEngine
import org.easyb.listener.ResultsAmalgamator
import org.easyb.listener.ResultsReporter

class HtmlReportWriter implements ReportWriter {
  /* the template folder inside the easyb.jar */
  private static final String DEFAULT_TEMPLATE_FOLDER = "reports/"
  private static final String DEFAULT_TEMPLATE_NAME = "easyb_report_main.tmpl"
  private static final String DEFAULT_OUT_NAME = "easyb-report.html";

  private String templateLocation // null -> take templates from easyb.jar
  private String outputLocation

  private ResultsReporter results
  private Writer outWriter

  public HtmlReportWriter() {
    this(null, null)
  }

  HtmlReportWriter(String templateLocation, String outputLocation) {
    this.templateLocation = templateLocation;
    this.outputLocation = (outputLocation != null ? outputLocation : DEFAULT_OUT_NAME);
  }

  public void writeReport(ResultsAmalgamator resultsAmalgamator) {
    results = resultsAmalgamator.getResultsReporter()
    outWriter = new File(outputLocation).newWriter();

    copyResourcesToOutFolder()
    writeReportToOutFolder()
  }

  private copyResourcesToOutFolder() {
    if(getTemplateFolder() != null) {
      new File(getTemplateFolder()).eachFile { file ->
        if(file.file && !file.name.endsWith(".tmpl")) {
          copyFile(file, new File(getOutputFolder()+file.name))
        }
      }
    } else {
      writeResource("prototype.js")
      ['img01.gif', 'img02.jpg', 'img03.jpg', 'img04.jpg', 'img05.jpg', 'img06.jpg', 'spacer.gif', 'report.css'].each {
        writeResource("easyb_${it}")
      }
    }
  }

  private writeReportToOutFolder() {
    InputStream templateInputStream = getTemplateResourceInputStream(getTemplateName());
    def reportTemplate = new SimpleTemplateEngine().createTemplate(templateInputStream.newReader()).make(["report": this])
    reportTemplate.writeTo(outWriter)
  }

  private copyFile(File sourceFile, File destFile) {
    InputStream is = sourceFile.newInputStream()
    destFile.withOutputStream { os ->
      os << is
    }
    is.close()
  }

  private writeResource(String resourceName) {
    InputStream is = getTemplateResourceInputStream(resourceName);
    new File(getOutputFolder()+resourceName).withOutputStream { os ->
      os << is
    }
    is.close()
  }

  /**
   * Returns a input stream from the given resource in the template folder. If
   * no template folder was provided by the user, the resource is taken from the
   * easyb.jar.
   *
   * @param resourceName the file name of the resource
   * @return the input stream to the given resource
   */
  private InputStream getTemplateResourceInputStream(String resourceName) {
    if (getTemplateFolder() != null) {
      return new FileInputStream(getTemplateFolder() + resourceName)
    } else {
      return HtmlReportHelper.getClassLoader().getResourceAsStream(DEFAULT_TEMPLATE_FOLDER + resourceName)
    }
  }

  private getTemplateName() {
    String templateName = null;
    if (templateLocation != null) {
      if (templateLocation.contains(File.separator)) {
        templateName = templateLocation.substring(templateLocation.lastIndexOf(File.separator)+1, templateLocation.length())
      } else {
        templateName = templateLocation
      }
    }
    return templateName != null && templateName.length() > 0 ? templateName : DEFAULT_TEMPLATE_NAME
  }

  private getTemplateFolder() {
    if (templateLocation != null && templateLocation.contains(File.separator)) {
        return templateLocation.substring(0, templateLocation.lastIndexOf(File.separator)+1)
    }
    return null
  }

  private getOutputFolder() {
    String outPath = new File(outputLocation).canonicalPath
    if (outPath.contains(File.separator)) {
      return outPath.substring(0, outPath.lastIndexOf(File.separator)+1)
    } else {
      return "."
    }
  }

  void includeTemplate(String templateFilename) {
    InputStream genericListTemplateInputStream = getTemplateResourceInputStream(templateFilename);
    def reportTemplate = new SimpleTemplateEngine().createTemplate(genericListTemplateInputStream.newReader()).make(["report": this])
    reportTemplate.writeTo(outWriter)
  }
}