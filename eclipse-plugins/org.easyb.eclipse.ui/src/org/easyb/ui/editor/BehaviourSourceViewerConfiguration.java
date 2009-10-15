package org.easyb.ui.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.groovy.eclipse.editor.GroovyColorManager;
import org.codehaus.groovy.eclipse.editor.GroovyConfiguration;
import org.easyb.ui.editor.reconciler.BehaviourReconcilerStrategy;
import org.easyb.ui.editor.reconciler.CompositeReconciler;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;

public class BehaviourSourceViewerConfiguration extends GroovyConfiguration{

	private ITextEditor textEditor;
	private BehaviourTagScanner tagScanner;
	public BehaviourSourceViewerConfiguration(GroovyColorManager colorManager, IPreferenceStore preferenceStore, ITextEditor textEditor){
			super(colorManager,preferenceStore,textEditor);
			this.textEditor = textEditor;
			this.tagScanner = new BehaviourTagScanner(colorManager); 
	}
	
    @Override
    protected RuleBasedScanner getCodeScanner() {
        return tagScanner;
    }
	
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer)
	{
		List<String> contentTypes = new ArrayList<String>();
		Collections.addAll(contentTypes,PartitionScannerBuilder.EASYB_ALL_PARTITION_TYPES);
		Collections.addAll(contentTypes,super.getConfiguredContentTypes(sourceViewer));
		
		return contentTypes.toArray(new String[contentTypes.size()]);
	}
	
    
	/*@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return PartitionScannerBuilder.PARTITIONER_ID;
	}*/
	
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer){


		MonoReconciler bReconciler = 
			new MonoReconciler(new BehaviourReconcilerStrategy((BehaviourEditor)textEditor),false); 
		bReconciler.setIsAllowedToModifyDocument(false);
		
		return new CompositeReconciler(super.getReconciler(sourceViewer),bReconciler);
	}
}
