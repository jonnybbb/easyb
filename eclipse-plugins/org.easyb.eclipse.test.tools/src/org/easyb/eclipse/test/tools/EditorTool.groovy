package org.easyb.eclipse.test.tools;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class EditorTool {

	private EditorTool(){
		
	}
		
	static IEditorPart openEditor(IFile file){
		IWorkbenchPage page = 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			
		IEditorDescriptor desc =
				PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName())

		page.openEditor(new FileEditorInput(file), desc.getId())	
	}

	static IDocument getDocument(IEditorPart editor){

		IDocumentProvider provider = editor?.getDocumentProvider()

		if(!provider){
			return null
		}

		provider.getDocument(editor.getEditorInput())
	}

	static ITextViewer getTextViewer(IEditorPart editor){
		(ITextViewer)editor.getAdapter(ITextOperationTarget.class);
	}
}
