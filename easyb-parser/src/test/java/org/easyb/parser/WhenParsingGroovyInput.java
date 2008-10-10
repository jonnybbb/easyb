package org.easyb.parser;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.codehaus.groovy.antlr.parser.GroovyRecognizer;
import org.codehaus.groovy.antlr.parser.GroovyLexer;
import org.codehaus.groovy.antlr.UnicodeEscapingReader;
import org.codehaus.groovy.antlr.SourceBuffer;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.io.Reader;
import java.io.InputStreamReader;

public class WhenParsingGroovyInput {
    @Test
    public void shouldProduceAbstractSyntaxTree() {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/after.specification"));
        GroovyLexer lexer = new GroovyLexer(new UnicodeEscapingReader(reader, new SourceBuffer()));
        GroovyRecognizer parser = GroovyRecognizer.make(lexer);

        assertThat(parser, is(notNullValue()));
    }
}
