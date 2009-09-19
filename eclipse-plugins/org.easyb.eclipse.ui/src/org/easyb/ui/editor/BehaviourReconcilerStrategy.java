package org.easyb.ui.editor;

import org.easyb.ui.EasybUIActivator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

/**
 * Reconciler strategy for easyb behaviours. A reconciler runs in the 
 * background. Messages are sent to it when the editor is updated 
 * and its the job of the strategy to apply these changes to 
 * the model used by the the editor and any dependents like Content 
 * Outline Page.
 * @author whiteda
 *
 */
//TODO currently rebuilds the model from the document every time
//this maybe too inefficient so it could just update the model where necessary
//TODO could make this class more generic so it doesn`t just use a BehaviourEditor 
public class BehaviourReconcilerStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension{
	private BehaviourEditor editor;
	public BehaviourReconcilerStrategy(BehaviourEditor editor){
		this.editor = editor;
	}
	
	@Override
	public void initialReconcile() {
		updateUIThread();	
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		updateUIThread();
		
	}

	@Override
	public void reconcile(IRegion partition) {	
		updateUIThread();
	}

	public void updateUIThread(){
		Display display = 
			EasybUIActivator.getDefault().getWorkbench().getDisplay();
		
		display.asyncExec(new Runnable() {
	        public void run() {
	        	editor.updateOutline();
	        	}
	        });
	}
	
	@Override
	public void setDocument(IDocument document) {
	}

}
