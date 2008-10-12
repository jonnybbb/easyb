package org.easyb.parser;

import groovyjarjarantlr.RecognitionException;
import groovyjarjarantlr.TokenStreamException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import static java.util.Arrays.asList;
import java.util.List;

public class WhenParsingGroovyInput {
    @Test
    public void shouldRecognizeBehaviors() throws TokenStreamException, RecognitionException {
        EasybParser parser = new EasybParser("/after.story");

        List<EasybSnippet> expected = asList(new EasybSnippet(3, 1), new EasybSnippet(7, 1), new EasybSnippet(13, 1),
                new EasybSnippet(25, 1));
        assertThat(parser.splitBehaviors(), is(expected));
    }
}
