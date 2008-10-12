package org.easyb.parser;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class WhenComparingSnippets {
    private EasybSnippet snippet;

    @Before
    public void setUp() {
        snippet = new EasybSnippet(7, 1);
    }

    @Test
    public void shouldTreatSameInstanceAsEqual() {
        assertTrue(snippet.equals(snippet));
    }

    @SuppressWarnings({"ObjectEqualsNull"})
    @Test
    public void shouldTreatNullAsNotEqual() {
        assertFalse(snippet.equals(null));
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Test
    public void shouldTreatDifferentTypesAsNotEqual() {
        assertFalse(snippet.equals("other"));
    }

    @Test
    public void shouldTreatSameLineAndColumnAsEqual() {
        assertTrue(snippet.equals(new EasybSnippet(7, 1)));
    }

    @Test
    public void shouldTreatDifferentLineAsNotEquals() {
        assertFalse(snippet.equals(new EasybSnippet(6, 1)));
    }

    @Test
    public void shouldTreatDifferentColumnAsNotEquals() {
        assertFalse(snippet.equals(new EasybSnippet(7, 2)));
    }

    @Test
    public void shouldReceiveDifferentHashCodes() {
        EasybSnippet another = new EasybSnippet(5, 2);
        assertThat(snippet.hashCode(), is(not(another.hashCode())));
    }
}
