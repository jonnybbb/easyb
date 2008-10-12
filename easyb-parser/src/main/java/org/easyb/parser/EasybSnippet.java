package org.easyb.parser;

public class EasybSnippet {
    private String specificationPath;
    public final Coordinate start;
    public final Coordinate end;

    public EasybSnippet(String specificationPath, Coordinate start, Coordinate end) {
        this.specificationPath = specificationPath;
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return specificationPath + " (" + start + " to " + end + ")";
    }

    static class Coordinate {
        public final int line;
        public final int column;

        public Coordinate(int line, int column) {
            this.line = line;
            this.column = column;
        }

        public static Coordinate createCoordinate(int line, int column) {
            return new Coordinate(line, column);
        }

        public String toString() {
            return line + ":" + column;
        }
    }
}
