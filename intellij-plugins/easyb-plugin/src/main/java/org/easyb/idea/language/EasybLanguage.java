package org.easyb.idea.language;

import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.*;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.parameterInfo.ParameterInfoHandler;
import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.lang.surroundWith.SurroundDescriptor;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.annotator.GroovyAnnotator;
import org.jetbrains.plugins.groovy.findUsages.GroovyFindUsagesProvider;
import org.jetbrains.plugins.groovy.formatter.GroovyFormattingModelBuilder;
import org.jetbrains.plugins.groovy.highlighter.GroovyBraceMatcher;
import org.jetbrains.plugins.groovy.highlighter.GroovyCommenter;
import org.jetbrains.plugins.groovy.highlighter.GroovySyntaxHighlighter;
import org.jetbrains.plugins.groovy.lang.documentation.GroovyDocumentationProvider;
import org.jetbrains.plugins.groovy.lang.editor.GroovyImportOptimizer;
import org.jetbrains.plugins.groovy.lang.folding.GroovyFoldingBuilder;
import org.jetbrains.plugins.groovy.lang.parameterInfo.GroovyParameterInfoHandler;
import org.jetbrains.plugins.groovy.lang.parser.GroovyParserDefinition;
import org.jetbrains.plugins.groovy.lang.parser.GroovyReferenceAdjuster;
import org.jetbrains.plugins.groovy.lang.surroundWith.descriptors.GroovyStmtsSurroundDescriptor;
import org.jetbrains.plugins.groovy.overrideImplement.ImplementMethodsHandler;
import org.jetbrains.plugins.groovy.overrideImplement.OverrideMethodsHandler;
import org.jetbrains.plugins.groovy.refactoring.GroovyRefactoringSupportProvider;
import org.jetbrains.plugins.groovy.structure.GroovyStructureViewBuilder;

public class EasybLanguage extends Language {
    public EasybLanguage() {
        super("Easyb");
    }

    public ParserDefinition getParserDefinition() {
        return new GroovyParserDefinition();
    }

    public FoldingBuilder getFoldingBuilder() {
        return new GroovyFoldingBuilder();
    }

    @NotNull
    public SyntaxHighlighter getSyntaxHighlighter(Project project, final VirtualFile virtualFile) {
        return new GroovySyntaxHighlighter();
    }

    @Nullable
    public Commenter getCommenter() {
        return new GroovyCommenter();
    }

    @Nullable
    public Annotator getAnnotator() {
        return GroovyAnnotator.INSTANCE;
    }

    @NotNull
    public FindUsagesProvider getFindUsagesProvider() {
        return GroovyFindUsagesProvider.INSTANCE;
    }

    @NotNull
    public RefactoringSupportProvider getRefactoringSupportProvider() {
        return GroovyRefactoringSupportProvider.INSTANCE;
    }

    @Nullable
    public PsiReferenceAdjuster getReferenceAdjuster() {
        return GroovyReferenceAdjuster.INSTANCE;
    }

    @Nullable
    public FormattingModelBuilder getFormattingModelBuilder() {
        return new GroovyFormattingModelBuilder();
    }

    @Nullable
    public PairedBraceMatcher getPairedBraceMatcher() {
        return new GroovyBraceMatcher();
    }

    public StructureViewBuilder getStructureViewBuilder(PsiFile psiFile) {
        return new GroovyStructureViewBuilder(psiFile);
    }

    @NotNull
    public SurroundDescriptor[] getSurroundDescriptors() {
        return new SurroundDescriptor[]{new GroovyStmtsSurroundDescriptor()};
    }

    @Nullable
    public ImportOptimizer getImportOptimizer() {
        return new GroovyImportOptimizer();
    }

    @Nullable
    public LanguageCodeInsightActionHandler getOverrideMethodsHandler() {
        return new OverrideMethodsHandler();
    }

    @Nullable
    public LanguageCodeInsightActionHandler getImplementMethodsHandler() {
        return new ImplementMethodsHandler();
    }

    @Nullable
    public DocumentationProvider getDocumentationProvider() {
        return new GroovyDocumentationProvider();
    }

    @Nullable
    public ParameterInfoHandler[] getParameterInfoHandlers() {
        return new ParameterInfoHandler[]{new GroovyParameterInfoHandler()};
    }
}
