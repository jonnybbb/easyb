package org.easyb.easybplugin.editor;

import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class BehaviourEditor extends AbstractTextEditor{
	
	public BehaviourEditor(){
		setSourceViewerConfiguration(new BehaviourSourceViewerConfiguration());
		setDocumentProvider(new TextFileDocumentProvider());
	}
}
