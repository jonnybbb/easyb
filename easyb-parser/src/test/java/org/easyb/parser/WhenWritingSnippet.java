package org.easyb.parser;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class WhenWritingSnippet {
    @Test
    public void shouldIncludeLineAndColumn() {
        EasybSnippet snippet = new EasybSnippet(7, 1);
        assertThat(snippet.toString(), is("7:1"));
    }
}
