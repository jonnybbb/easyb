package org.easyb.ui.newbehaviour;

import org.easyb.eclipse.templates.TemplateActivator;
import org.easyb.ui.utils.IUIConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewSpecificationWizardPage extends AbstractNewBehaviourWizardPage{
	private Label lblName;
	private Text txtName;
	
	public NewSpecificationWizardPage(IStructuredSelection selection){
		super("Create a specification","New Specification",null,selection);
	}
	
	@Override
	protected void createPageControl(Composite parent) {
		lblName = new Label(parent,SWT.NONE);
		lblName.setText("Specification name:");
		
		txtName = new Text(parent,SWT.SINGLE | SWT.BORDER);
		txtName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtName.addModifyListener(new FileNameModifyListener());	
	}

	@Override
	protected String getFileExtension() {
		return IUIConstants.SPECIFICATION_EXTENSION;
	}

	@Override
	protected String getFileName() {
		return txtName.getText();
	}

	@Override
	protected String getTemplatePattern() {
	try{	
		return TemplateActivator.getEmptySpecificationTemplateText();
	}catch(CoreException cex){
		setErrorMessage("Unable to get empty template for specification,check error log");
	}
	
	return "";
	}

}
