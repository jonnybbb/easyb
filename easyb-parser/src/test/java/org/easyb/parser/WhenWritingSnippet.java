package org.easyb.parser;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class WhenWritingSnippet {
    @Test
    public void shouldWriteFriendlyString() {
        EasybSnippet snippet = new EasybSnippet("/behavior.story",
                new EasybSnippet.Coordinate(1, 1), new EasybSnippet.Coordinate(7, 1));
        assertThat(snippet.toString(), is("/behavior.story (1:1 to 7:1)"));
    }
}
