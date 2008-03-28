package org.easyb.maven

import groovy.xml.MarkupBuilder

class StoryReportWriter {
    void write(Story story, OutputStream output) {
        output.withWriter {Writer writer ->
            def report = new MarkupBuilder(writer)
            writeReport(report, story)
        }
    }

    private def writeReport(MarkupBuilder report, Story story) {
        report.html {
            body {
                story.scenarios.each {scenario ->
                    div {
                        scenario.givens.each {div it}
                        scenario.whens.each {div it}
                        scenario.thens.each {div it}
                    }
                }
            }
        }
    }
}