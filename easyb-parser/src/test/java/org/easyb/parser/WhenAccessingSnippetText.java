package org.easyb.parser;

import static org.easyb.parser.EasybSnippet.Coordinate.createCoordinate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class WhenAccessingSnippetText {
    private static String NEW_LINE = System.getProperty("line.separator");
    private static final String AFTER_STORY = "/after.story";

    @Test
    public void shouldExtractSingleLine() throws IOException {
        EasybSnippet snippet = new EasybSnippet(AFTER_STORY, createCoordinate(1, 1), createCoordinate(2, 1));
        assertThat(snippet.getText(), is("def foo" + NEW_LINE));
    }

    @Test
    public void shouldExtractMultipleLines() throws IOException {
        EasybSnippet snippet = new EasybSnippet(AFTER_STORY, createCoordinate(1, 1), createCoordinate(7, 1));
        assertThat(countLines(snippet.getText()), is(6));
    }

    private int countLines(String text) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(text));

        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }

        return lines;
    }
}
