package org.easyb.ui.newbehaviour;

import org.eclipse.jface.viewers.IStructuredSelection;

public class NewSpecificationWizard  extends AbstractBehaviourWizard{
	
	public NewSpecificationWizard(){
		super("New Specification");
	}

	@Override
	protected AbstractNewBehaviourWizardPage getNewBehaviourWizardPage(IStructuredSelection selection){
		return new NewSpecificationWizardPage(selection);
	}
}
