package org.easyb.ui.editor.reconciler;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;

/**
 * Combines multiple reconcilers. Can help when deriving from 
 * a SoruceConfiguration and want to addan additional 
 * reconciler
 * @author whiteda
 *
 */
public class CompositeReconciler implements IReconciler{
	
	private final IReconciler[] reconcilers;
	
	public CompositeReconciler(IReconciler... reconcilers){
		this.reconcilers = reconcilers;
	}
	/**
	 * Iterates the reconcilers and gets the first matching strategy
	 */
	@Override
	public IReconcilingStrategy getReconcilingStrategy(String contentType) {
		
		IReconcilingStrategy strategy = null;
		for(IReconciler recon : reconcilers){
			if((strategy = recon.getReconcilingStrategy(contentType))!=null){
				return strategy;
			}
		}
		
		return null;
	}
	@Override
	public void install(ITextViewer textViewer) {
		
		for(IReconciler recon : reconcilers){
			recon.install(textViewer);
		}
		
	}
	@Override
	public void uninstall() {
		for(IReconciler recon : reconcilers){
			recon.uninstall();
		}
		
	}
	
}
