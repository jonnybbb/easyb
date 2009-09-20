package org.easyb.ui.newbehaviour;

import org.easyb.ui.EasybUIActivator;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

//TODO If this becomes more complex then possibly move to a seperate 
//plugin
public class NewStoryWizard extends Wizard implements INewWizard{
	private IStructuredSelection selection;
    private NewStoryWizardPage newFileWizardPage;
    private IWorkbench workbench;
    
	public NewStoryWizard(){
		 setWindowTitle("New Story");
	}
	
	 @Override
    public void addPages() {
        newFileWizardPage = new NewStoryWizardPage(selection);
        addPage(newFileWizardPage);
    }
	 
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
        this.selection = selection;
		
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
				EasybUIActivator.Log("Unableto open behaviour editor for new behaviour", e);
			}
    
            return true;
        }
        else{
            return false;
        }
	}

}
