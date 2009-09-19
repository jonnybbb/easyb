package org.easyb.launch.newbehaviour;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

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
        if (file != null)
            return true;
        else
            return false;
	}

}
