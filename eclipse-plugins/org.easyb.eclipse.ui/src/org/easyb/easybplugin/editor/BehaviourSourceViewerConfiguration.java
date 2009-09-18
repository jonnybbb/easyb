package org.easyb.easybplugin.editor;

import org.easyb.eclipse.templates.processor.BehaviourCompletionProcessor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.ITextEditor;

public class BehaviourSourceViewerConfiguration extends TextSourceViewerConfiguration{

	private BehaviourEditor textEditor;
	
	public BehaviourSourceViewerConfiguration(BehaviourEditor textEditor){
		this.textEditor = textEditor;
	}
	
	//TODO finish Content assist to restrict types 
	//of available templates depending on where the 
	//cursor is in the document
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant= new ContentAssistant();
		assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		IContentAssistProcessor processor= new BehaviourCompletionProcessor();
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);

		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

		return assistant;
	}
	
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer)
	{
		return PartitionScannerBuilder.EASYB_BEHAVIOUR_PARTITION_TYPES;
	}
	
	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return PartitionScannerBuilder.PARTITIONER_ID;
	}
	
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer){

		IReconciler reconciler = 
			new MonoReconciler(new BehaviourReconcilerStrategy(textEditor),false); 
		return reconciler;	
	}
}
