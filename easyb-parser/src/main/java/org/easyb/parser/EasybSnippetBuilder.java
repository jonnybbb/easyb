package org.easyb.parser;

public class EasybSnippetBuilder {
    private String specification;

    public EasybSnippetBuilder(String specification) {
        this.specification = specification;
    }

    public EasybSnippet build(EasybSnippet.Coordinate start, EasybSnippet.Coordinate end) {
        return new EasybSnippet(specification, start, end);
    }
}
