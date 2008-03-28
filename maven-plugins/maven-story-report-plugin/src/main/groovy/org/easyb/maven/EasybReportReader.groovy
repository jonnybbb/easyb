package org.easyb.maven

class EasybReportReader {
    List<Story> stories

    EasybReportReader(InputStream reportStream) {
        stories = new ArrayList<Story>()
        new XmlParser().parse(reportStream).stories.story.each {story ->
            def scenarios = []
            story.scenario.each {scenario ->
                scenarios += new Scenario(givens: parseGivens(scenario), whens: parseWhens(scenario), thens: parseThens(scenario))
            }
            stories.add(new Story(name: story.@name, scenarios: scenarios))
        }
    }

    List<Story> getStories() {
        return stories
    }

    List parseGivens(Node scenario) {
        return parse(scenario.given)
    }

    List parseWhens(Node scenario) {
        return parse(scenario.when)
    }

    List parseThens(Node scenario) {
        return parse(scenario.then)
    }

    List parse(List elements) {
        def names = []
        elements.each {
            names += it.@name
        }
        return names
    }
}