package org.easyb.parser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.internal.matchers.IsCollectionContaining;

import java.util.List;
import java.util.ArrayList;

public class EasybSnippetMatcher extends TypeSafeMatcher<EasybSnippet> {
    private EasybSnippet snippet;

    public EasybSnippetMatcher(EasybSnippet snippet) {
        this.snippet = snippet;
    }

    public static EasybSnippetMatcher matchesSnippet(EasybSnippet snippet) {
        return new EasybSnippetMatcher(snippet);
    }

    public static Matcher<Iterable<EasybSnippet>> hasSnippets(EasybSnippet... snippets) {
        List<EasybSnippetMatcher> matchers = new ArrayList<EasybSnippetMatcher>();
        for (EasybSnippet each : snippets) {
            matchers.add(new EasybSnippetMatcher(each));
        }
        return IsCollectionContaining.hasItems(matchers.toArray(new EasybSnippetMatcher[matchers.size()]));
    }

    public boolean matchesSafely(EasybSnippet other) {
        return snippet.line == other.line && snippet.column == other.column;
    }

    public void describeTo(Description description) {
        description.appendText(snippet.toString());
    }
}
