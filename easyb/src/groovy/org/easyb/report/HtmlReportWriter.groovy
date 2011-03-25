package org.easyb.report

import groovy.text.SimpleTemplateEngine
import org.easyb.listener.ResultsAmalgamator

class HtmlReportWriter implements ReportWriter {
  private String location
  private String templateFolder

  private static final String DEFAULT_LOC_NAME = "easyb-report.html";

  public HtmlReportWriter() {
    this(DEFAULT_LOC_NAME, null)
  }

  HtmlReportWriter(String location, String templateFolder) {
    this.location = (location != null ? location : DEFAULT_LOC_NAME);
    this.templateFolder = templateFolder;
  }

  public void writeReport(ResultsAmalgamator amal) {
    def results = amal.getResultsReporter()

    writeResourceToDisk("prototype.js")

    ['img01.gif', 'img02.jpg', 'img03.jpg', 'img04.jpg', 'img05.jpg', 'img06.jpg', 'spacer.gif', 'report.css'].each {
      writeResourceToDisk("easyb_${it}")
    }

    InputStream mainTemplateInputStream = HtmlReportHelper.getResourceInputStream(templateFolder, "easyb_report_main.tmpl");
    def outputReportBufferedWriter = new File(location).newWriter();
    def templateBinding = ["results": results, "reportWriter": outputReportBufferedWriter, "templateFolder": templateFolder]
    def templateEngine = new SimpleTemplateEngine()
    def reportTemplate = templateEngine.createTemplate(mainTemplateInputStream.newReader()).make(templateBinding)
    reportTemplate.writeTo(outputReportBufferedWriter)

  }

  private writeResourceToDisk(String resourceName) {
    InputStream inputStream = HtmlReportHelper.getResourceInputStream(templateFolder, resourceName);
    if (inputStream != null) {
      FileOutputStream resourceWriter = new FileOutputStream("${locationDir}${File.separator}${resourceName}")
      inputStream.eachByte {singleByte ->
        resourceWriter.write(singleByte)
      }
      resourceWriter.close()
    }
  }

  private getLocationDir() {
    String normalizedPath = new File(location).canonicalPath
    if (normalizedPath.contains("\\")) {
      return normalizedPath.substring(0, normalizedPath.lastIndexOf("\\"))
    } else if (location.contains("/")) {
      return normalizedPath.substring(0, normalizedPath.lastIndexOf("/"))
    } else {
      return "."
    }
  }
}