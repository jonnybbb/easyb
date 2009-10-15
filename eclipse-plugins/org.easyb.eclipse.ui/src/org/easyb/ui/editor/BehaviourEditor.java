package org.easyb.ui.editor;

import org.codehaus.groovy.eclipse.GroovyPlugin;
import org.codehaus.groovy.eclipse.editor.GroovyEditor;
import org.codehaus.groovy.eclipse.editor.GroovyTextTools;
import org.easyb.ui.EasybUIActivator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class BehaviourEditor extends GroovyEditor
{
	private BehaviourOutlinePage outlinePage = null;
	
	public BehaviourEditor(){
	}
	
	//Called from the reconciler which runs as a background task
	//so made the whole method synchronized. If extra 
	//code is added to the updateOutline which is on the stack 
	//then change to lock an object rather then whole method
	public synchronized void updateOutline(){
		outlinePage.update();
	}
	
	@Override
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage= new BehaviourOutlinePage(this);
				if (getEditorInput() != null){
					outlinePage.setInput(getEditorInput());
				}
			}
			return outlinePage;
		}
		return super.getAdapter(required);
	}
	
	//GroovyEditor overrides this so we need to do the 
	//same to get the easyb image
    @Override
    public Image getTitleImage() {
    	Image image = 
    		EasybUIActivator.getDefault().getImageRegistry().get(EasybUIActivator.EASYB_ICON_IMAGE); 
    	
    	if(image!=null){
    		return image;
    	}
    	
    	return super.getTitleImage();
    }
    
	
	@Override
	public void dispose(){
		super.dispose();
		if (outlinePage != null){
			outlinePage.setInput(null);
		}
	}
	
	@Override
	 protected void setPreferenceStore(IPreferenceStore store) {
		 super.setPreferenceStore(store);
		 GroovyTextTools textTools= GroovyPlugin.getDefault().getTextTools();
		 setSourceViewerConfiguration(new BehaviourSourceViewerConfiguration(textTools.getColorManager(),getPreferenceStore(),this));
	 }
	 
}
