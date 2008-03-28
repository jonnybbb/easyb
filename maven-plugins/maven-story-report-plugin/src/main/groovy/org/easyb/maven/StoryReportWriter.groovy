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
                h1 'Story: ' + story.name
                story.scenarios.each {scenario ->
                    h3 'Scenario: ' + scenario.name
                    div 'class': 'scenario', {
                        scenario.givens.each {div 'class': 'given', 'given ' + it}
                        scenario.whens.each {div 'class': 'when', 'when ' + it}
                        scenario.thens.each {div 'class': 'then', 'then ' + it}
                    }
                }
            }
        }
    }
}