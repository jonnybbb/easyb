package org.easyb.maven

import org.junit.Test

class StoryReportWriterTest {
    @Test
    void shouldWriteStory() {
        Story story = new Story(name: 'test story')
        story.scenarios += new Scenario(givens: ['given something'], whens: ['when something happens'], thens: ['then happened'])
        story.scenarios += new Scenario(givens: ['given other'], whens: ['when other happens'], thens: ['then other happened'])

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new StoryReportWriter().write(story, outputStream)

        String output = outputStream.toString()
        story.scenarios.each {scenario ->
            scenario.givens.each {assert output.contains(it)}
            scenario.whens.each {assert output.contains(it)}
            scenario.thens.each {assert output.contains(it)}
        }
    }
}