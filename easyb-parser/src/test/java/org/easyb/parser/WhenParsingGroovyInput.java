package org.easyb.parser;

import groovyjarjarantlr.RecognitionException;
import groovyjarjarantlr.TokenStreamException;
import static org.easyb.parser.EasybSnippet.Coordinate.EOF;
import static org.easyb.parser.EasybSnippet.Coordinate.createCoordinate;
import static org.easyb.parser.EasybSnippetMatcher.hasSnippets;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class WhenParsingGroovyInput {
    private static final String AFTER_STORY_PATH = "/after.story";
    
    private EasybSnippet.Coordinate coord1 = createCoordinate(1, 1);
    private EasybSnippet.Coordinate coord2 = createCoordinate(3, 1);
    private EasybSnippet.Coordinate coord3 = createCoordinate(7, 1);
    private EasybSnippet.Coordinate coord4 = createCoordinate(13, 1);
    private EasybSnippet.Coordinate coord5 = createCoordinate(25, 1);
    private EasybSnippet.Coordinate coord6 = EOF;

    @Test
    public void shouldRecognizeBehaviors() throws TokenStreamException, RecognitionException {
        EasybParser parser = new EasybParser(AFTER_STORY_PATH);

        EasybSnippetBuilder builder = new EasybSnippetBuilder(AFTER_STORY_PATH);
        assertThat(parser.splitBehaviors(), hasSnippets(builder.build(coord1, coord2), builder.build(coord2, coord3),
                builder.build(coord3, coord4), builder.build(coord4, coord5), builder.build(coord5, coord6)));
    }
}
