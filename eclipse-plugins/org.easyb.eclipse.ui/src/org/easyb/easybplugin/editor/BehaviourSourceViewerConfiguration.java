package org.easyb.easybplugin.editor;

import org.easyb.eclipse.templates.processor.BehaviourCompletionProcessor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

public class BehaviourSourceViewerConfiguration extends TextSourceViewerConfiguration{

	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant= new ContentAssistant();
		assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		IContentAssistProcessor processor= new BehaviourCompletionProcessor();
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		//assistant.setContentAssistProcessor(processor, XMLPartitionScanner.XML_TAG);

		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

		return assistant;
	}
}
