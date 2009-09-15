package org.easyb.easybplugin.newbehaviour;

import org.easyb.easybplugin.EasybActivator;
import org.easyb.easybplugin.viewerfilters.ContainerViewerFilter;
import org.easyb.easybplugin.viewerfilters.PackageViewerFilter;
import org.easyb.easybplugin.viewerfilters.SourceViewerFilter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class NewStoryWizardPage extends WizardPage{
	private IStructuredSelection selection;
	
	private Label lblSource;
	private Text txtSource;
	private Button btnSource;
	
	private Label lblPackage;
	private Text txtPackage;
	private Button btnPackage;
	
	private Label lblName;
	private Text txtName;
	
	private IJavaElement sourceFolder;
	private IJavaElement sourcePackage;
	
	private SelectionAdapter sourceSelectionAdapter = new SelectionAdapter(){ 
		public void widgetSelected(SelectionEvent e){
			updateSourceFolder(chooseSourceFolder(sourceFolder));
		} 
	};
	
	private SelectionAdapter packageSelectionAdapter = new SelectionAdapter(){ 
		public void widgetSelected(SelectionEvent e){
			updatePackage(choosePackage(sourcePackage));
		} 
	};
	
	public NewStoryWizardPage(IStructuredSelection selection){
		super("Create Story","New Story",null);
		this.selection = selection;
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent,SWT.NONE);
		setControl(comp);
		
		GridLayout mainLayout = new GridLayout(3,false);
		comp.setLayout(mainLayout);
	
		createSourceLocationControl(comp);
	}
	
	private void createSourceLocationControl(Composite comp){
		
		lblSource = new Label(comp,SWT.NONE);
		lblSource.setText("Source folder:");
		
		txtSource = new Text(comp,SWT.SINGLE | SWT.BORDER);
		txtSource.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		btnSource = new Button(comp,SWT.PUSH);
		btnSource.setText("Browse");
		btnSource.addSelectionListener(sourceSelectionAdapter);
		
		lblPackage = new Label(comp,SWT.NONE);
		lblPackage.setText("Package:");
		
		txtPackage = new Text(comp,SWT.SINGLE | SWT.BORDER);
		txtPackage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		btnPackage = new Button(comp,SWT.PUSH);
		btnPackage.setText("Browse");
		btnPackage.addSelectionListener(packageSelectionAdapter);
		
		lblName = new Label(comp,SWT.NONE);
		lblName.setText("Behaviour name:");
		
		txtName = new Text(comp,SWT.SINGLE | SWT.BORDER);
		txtName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	
	private void updateSourceFolder(IJavaElement element){
		
		if(element ==null){
			return;
		}
		
		sourceFolder = element;
		txtSource.setText(sourceFolder.getPath().toPortableString());
	}
	
	private void updatePackage(IJavaElement element){
		if(element ==null){
			return;
		}
		
		sourcePackage = element;
		
		if(element.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT){
			txtPackage.setText("");
		}else{
			txtPackage.setText(element.getElementName());
		}
	}
	
	private IJavaElement chooseSourceFolder(IJavaElement initElement) {
		StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setTitle("Source Folders");
		dialog.setMessage("Choose a source folder");
		dialog.addFilter(new SourceViewerFilter());
		dialog.setInput(JavaCore.create(ResourcesPlugin.getWorkspace().getRoot()));
		dialog.setInitialSelection(initElement);
		dialog.setAllowMultiple(false);

		if (dialog.open() == Window.OK) {
			IJavaElement jElement= (IJavaElement)dialog.getFirstResult();
			
			if(jElement.getElementType() == IJavaElement.JAVA_PROJECT){
				setMessage("You can not set a project as a source folder");
				return null;
			}
			
			return jElement;
		}
		return null;
	}
	
	private IJavaElement choosePackage(IJavaElement initElement) {
		StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setTitle("Packages");
		dialog.setMessage("Choose a package");
		dialog.addFilter(new PackageViewerFilter());
		dialog.setInput(JavaCore.create(ResourcesPlugin.getWorkspace().getRoot()));
		dialog.setInitialSelection(initElement);
		dialog.setAllowMultiple(false);

		if (dialog.open() == Window.OK) {
			IJavaElement jElement= (IJavaElement)dialog.getFirstResult();
			
			if(jElement.getElementType()==IJavaElement.JAVA_PROJECT){
				setMessage("You can not set a project as a package");
				return null;
			}
			
			return (IJavaElement)jElement;
		}
		return null;
	}
	
	public IFile createNewBehaviour(){
		return null;
	}

}
