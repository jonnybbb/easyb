package org.easyb.parser;

import groovyjarjarantlr.RecognitionException;
import groovyjarjarantlr.TokenStreamException;
import static org.easyb.parser.EasybSnippetMatcher.hasSnippets;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class WhenParsingGroovyInput {
    @Test
    public void shouldRecognizeBehaviors() throws TokenStreamException, RecognitionException {
        EasybParser parser = new EasybParser("/after.story");

        assertThat(parser.splitBehaviors(), hasSnippets(new EasybSnippet(3, 1), new EasybSnippet(7, 1),
                new EasybSnippet(13, 1), new EasybSnippet(25, 1)));
    }
}
