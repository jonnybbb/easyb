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

public class NewStoryWizardPage extends AbstractNewBehaviourWizardPage{
	
	private Label lblName;
	private Text txtName;

	public NewStoryWizardPage(IStructuredSelection selection){
		super("Create Story","New Story",null,selection);
	}
	
	@Override
	public void createPageControl(Composite parent) {
	
		lblName = new Label(parent,SWT.NONE);
		lblName.setText("Story name:");
		
		txtName = new Text(parent,SWT.SINGLE | SWT.BORDER);
		txtName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtName.addModifyListener(new FileNameModifyListener());
	}
	
	protected String getTemplatePattern(){
		try{
		return TemplateActivator.getEmptyScenarioTemplateText();
		}catch(CoreException cex){
			setErrorMessage("Unable to get empty template for story,check error log");
		}
		return "";
	}
	
	protected String getFileExtension(){
		return IUIConstants.STORY_EXTENSION;
	}
	
	protected String getFileName(){
		return txtName.getText();
	}

}
