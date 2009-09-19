package org.easyb.launch.editor;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class BehaviourEditor extends TextEditor
{
	private BehaviourOutlinePage outlinePage = null;
	
	public BehaviourEditor(){
	}
	
	protected  void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new BehaviourSourceViewerConfiguration(this));
		//setDocumentProvider(input);
	}
	
	public void updateOutline(){
		outlinePage.update();
	}
	
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage= new BehaviourOutlinePage(getDocumentProvider());
				if (getEditorInput() != null){
					outlinePage.setInput(getEditorInput());
				}
			}
			return outlinePage;
		}
		return super.getAdapter(required);
	}
	
	@Override
	public void dispose(){
		super.dispose();
		if (outlinePage != null){
			outlinePage.setInput(null);
		}
	}
}
