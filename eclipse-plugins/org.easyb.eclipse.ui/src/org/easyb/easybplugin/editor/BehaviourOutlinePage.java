package org.easyb.easybplugin.editor;

import org.easyb.easybplugin.EasybActivator;
import org.easyb.easybplugin.editor.partitionmodel.Behaviour;
import org.easyb.easybplugin.editor.partitionmodel.IModelElement;
import org.easyb.easybplugin.editor.partitionmodel.PartitionModelException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class BehaviourOutlinePage extends ContentOutlinePage {
	private Object input;
	private IDocumentProvider docProvider;
	
	private class OutlineContentLabelProvider extends LabelProvider{
		
		public String getText(Object element){
			return ((IModelElement)element).getText();
		}
	}
	
	private class OutlineContentProvider implements ITreeContentProvider{
		
		Behaviour model = new Behaviour();
		
		@Override
		public Object[] getChildren(Object element) {
			return ((IModelElement)element).getElements();
		}

		@Override
		public Object getParent(Object element) {
			return ((IModelElement)element).getParent();
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((IModelElement)element).hasChildren();
		}

		@Override
		public Object[] getElements(Object element) {
			return model.getElements();
		}

		@Override
		public void dispose() {
			model.clear();
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

			try {
				
				model.clear();
				
				if (newInput != null) {
					IDocument document= docProvider.getDocument(newInput);
					if (document != null) {
						model.createModel(document);
					}
				}
			} catch (PartitionModelException e) {
				EasybActivator.Log("Unable to update behaviour model for outline view",e);
			}
		}
	}
	
	public BehaviourOutlinePage(IDocumentProvider docProvider){
		this.docProvider = docProvider;
	}
	
	public void createControl(Composite parent){
		super.createControl(parent);
		TreeViewer viewer= getTreeViewer();
		viewer.setContentProvider(new OutlineContentProvider());
		viewer.setLabelProvider(new OutlineContentLabelProvider());
		
		if(input!=null){
			viewer.setInput(input);
		}
	}
	
	/**
	 * Sets the input of the outline page in the tree viewer 
	 * and holds a reference to the input for updating
	 * @param input
	 */
	public void setInput(Object input){
		this.input = input;
		TreeViewer viewer= getTreeViewer();
		
		if(viewer==null){
			return;
		}
		
		Control control= viewer.getControl();
		if (control != null && !control.isDisposed()) {
			control.setRedraw(false);
			viewer.setInput(input);
			viewer.expandAll();
			control.setRedraw(true);
		}
	}
	
	/**
	 * Updates the outline page
	 */
	public void update() {
		TreeViewer viewer= getTreeViewer();

		if (viewer != null) {
			Control control= viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(input);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}
}
