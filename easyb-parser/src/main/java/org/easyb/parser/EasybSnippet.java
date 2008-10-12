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

    @SuppressWarnings({"RedundantIfStatement"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasybSnippet that = (EasybSnippet) o;

        if (column != that.column) return false;
        if (line != that.line) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = line;
        result = 31 * result + column;
        return result;
    }
}
