package org.easyb.ui.newbehaviour;

import org.eclipse.jface.viewers.IStructuredSelection;

//TODO If this becomes more complex then possibly move to a seperate 
//plugin
public class NewStoryWizard extends AbstractBehaviourWizard{
    
	public NewStoryWizard(){
		super("New Story");
	}

	@Override
	protected AbstractNewBehaviourWizardPage getNewBehaviourWizardPage(IStructuredSelection selection){
		return new NewStoryWizardPage(selection);
	}

}
