package org.easyb.ui.editor.partitionmodel;

public interface IModelElement {
	public boolean hasChildren();
	
	public IModelElement[] getElements();
	
	public IModelElement getParent();
	
	public String getText();
	
}
