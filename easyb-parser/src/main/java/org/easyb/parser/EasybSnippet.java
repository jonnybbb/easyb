package org.easyb.parser;

public class EasybSnippet {
    public final int line;
    public final int column;

    public EasybSnippet(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public String toString() {
        return line + ":" + column;
    }
}
