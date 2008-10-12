package org.easyb.parser;

import groovyjarjarantlr.RecognitionException;
import groovyjarjarantlr.TokenStreamException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import static java.util.Arrays.asList;

public class WhenParsingGroovyInput {
    @Test
    public void shouldRecognizeBehaviors() throws TokenStreamException, RecognitionException {
        EasybParser parser = new EasybParser("/after.specification");
        assertThat(parser.splitBehaviors(), is(asList("1:1", "5:1")));
    }
}
