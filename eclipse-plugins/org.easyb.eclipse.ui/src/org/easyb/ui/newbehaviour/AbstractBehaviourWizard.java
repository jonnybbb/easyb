package org.easyb.ui.newbehaviour;

import org.easyb.ui.EasybUIActivator;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

public abstract class AbstractBehaviourWizard  extends Wizard implements INewWizard{
	private IStructuredSelection selection;
    private AbstractNewBehaviourWizardPage newFileWizardPage;
    private IWorkbench workbench;
    
	public AbstractBehaviourWizard(String title){
		 setWindowTitle(title);
	}
	
	protected abstract AbstractNewBehaviourWizardPage getNewBehaviourWizardPage(IStructuredSelection selection);
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
        this.selection = selection;
        newFileWizardPage = getNewBehaviourWizardPage(selection);
	}
	
	 @Override
    public void addPages() {
        addPage(newFileWizardPage);
    }
	 
	@Override
	public boolean performFinish() {
		IFile file = newFileWizardPage.createNewBehaviour();
        if (file != null){
        	IWorkbenchPage wkPage = 
        		workbench.getActiveWorkbenchWindow().getActivePage();
        	
        	IEditorDescriptor desc = 
        		workbench.getEditorRegistry().getDefaultEditor(file.getName());
        	
        	try {
				wkPage.openEditor(new FileEditorInput(file), desc.getId());
			} catch (PartInitException e) {
				EasybUIActivator.Log("Unable to open behaviour editor for new behaviour", e);
			}
    
            return true;
        }
        else{
            return false;
        }
	}
	
	protected ISelection getSelection(){
		return selection;
	}
	

}
