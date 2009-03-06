package org.easyb.parser;

import org.codehaus.groovy.antlr.parser.GroovyLexer;
import org.codehaus.groovy.antlr.parser.GroovyRecognizer;
import org.codehaus.groovy.antlr.UnicodeEscapingReader;
import org.codehaus.groovy.antlr.SourceBuffer;
import static org.easyb.parser.EasybSnippet.Coordinate.createCoordinate;
import static org.easyb.parser.EasybSnippet.Coordinate.EOF;

import java.io.Reader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import groovyjarjarantlr.TokenStreamException;
import groovyjarjarantlr.RecognitionException;
import groovyjarjarantlr.collections.AST;

public class EasybParser {
    private String specificationPath;

    /**
     * Construct an EasybParser
     *
     * @param specificationPath Path to search for specification on the classpath.  Ex. "/abehavior.story".
     */
    public EasybParser(String specificationPath) {
        this.specificationPath = specificationPath;
    }

    public List<EasybSnippet> splitBehaviors() throws TokenStreamException, RecognitionException {
        GroovyRecognizer parser = createParser();

        parser.compilationUnit();

        List<EasybSnippet> behaviors = new ArrayList<EasybSnippet>();
        EasybSnippet.Coordinate lastCoordinate = createCoordinate(1, 1);

        AST ast = parser.getAST();
        do {
            if (ast.getType() == GroovyLexer.EXPR) {
                EasybSnippet.Coordinate coordinate = createCoordinate(ast.getLine(), ast.getColumn());
                behaviors.add(new EasybSnippet(specificationPath, lastCoordinate, coordinate));
                lastCoordinate = coordinate;
            }
            ast = ast.getNextSibling();
        } while (ast != null);
        behaviors.add(new EasybSnippet(specificationPath, lastCoordinate, EOF));

        return behaviors;
    }

    private GroovyRecognizer createParser() {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream(specificationPath));
        GroovyLexer lexer = new GroovyLexer(new UnicodeEscapingReader(reader, new SourceBuffer()));
        
        return GroovyRecognizer.make(lexer);
    }
}
