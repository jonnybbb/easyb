package org.easyb.launch.newbehaviour;

import org.apache.commons.lang.StringUtils;
import org.easyb.launch.EasybLaunchActivator;
import org.easyb.launch.viewerfilters.PackageViewerFilter;
import org.easyb.launch.viewerfilters.SourceViewerFilter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public abstract class AbstractNewBehaviourWizardPage extends WizardPage{
	private Label lblSource;
	private Text txtSource;
	private Button btnSource;
	
	private Label lblPackage;
	private Text txtPackage;
	private Button btnPackage;
	
	private IJavaElement sourceFolder;
	private IJavaElement sourcePackage;
	
	private IStructuredSelection selection;
	
	private SourceTextListener sourceTxtListener = new SourceTextListener();
	
	private class SourceTextListener extends SelectionAdapter implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
			if(StringUtils.isBlank(txtSource.getText())){
				setEnablePackage(false);
			}else{
				clearPackage();
				setEnablePackage(true);
			}
		}
	}
	
	private SelectionAdapter sourceSelectionAdapter = new SelectionAdapter(){ 
		public void widgetSelected(SelectionEvent e){
			updateSourceFolder(chooseSourceFolder(sourceFolder));
		} 
	};
	
	private SelectionAdapter packageSelectionAdapter = new SelectionAdapter(){ 
		public void widgetSelected(SelectionEvent e){
			updatePackage(choosePackage(sourcePackage,sourceFolder));
		} 
	};
	
	public AbstractNewBehaviourWizardPage(String pageName,String pageTitle,ImageDescriptor img,
											IStructuredSelection selection){
		super(pageName,pageTitle,img);
		this.selection =  selection;
	}
	
	public abstract void createPageControl(Composite parent);
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent,SWT.NONE);
		setControl(comp);
		
		//TODO Possibly refactor to use a simpler layout 
		//for main layout like column layout to make it easier for 
		//derived classes
		GridLayout mainLayout = new GridLayout(3,false);
		comp.setLayout(mainLayout);
	
		createSourceLocationControl(comp);
		
		createPageControl(comp);
		
		initialiseFromSelection();
		
	}
	

	public IFile createNewBehaviour(){
		return null;
	}
	
	protected IJavaElement getSourceFolder(){
		return sourceFolder;
	}
	
	protected IJavaElement getPackage(){
		return sourcePackage;
	}
	
	protected String getSourceFolderText(){
		return txtSource ==null?"":txtSource.getText();
	}
	
	protected String getPackageText(){
		return txtPackage == null?"":txtPackage.getText();
	}
	
	protected boolean isSourceFolderEmpty(){
		return sourceFolder ==null?true:false;
	}
	
	private void createSourceLocationControl(Composite comp){
		
		lblSource = new Label(comp,SWT.NONE);
		lblSource.setText("Source folder:");
		
		txtSource = new Text(comp,SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		txtSource.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtSource.addModifyListener(sourceTxtListener);
		
		btnSource = new Button(comp,SWT.PUSH);
		btnSource.setText("Browse");
		btnSource.addSelectionListener(sourceSelectionAdapter);
		
		lblPackage = new Label(comp,SWT.NONE);
		lblPackage.setText("Package:");
		
		txtPackage = new Text(comp,SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		txtPackage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		btnPackage = new Button(comp,SWT.PUSH);
		btnPackage.setText("Browse");
		btnPackage.addSelectionListener(packageSelectionAdapter);
	}
	private void clearPackage(){
		txtPackage.setText("");
		sourcePackage = null;
	}
	
	private void setEnablePackage(boolean enable){
		txtPackage.setEnabled(enable);
		btnPackage.setEnabled(enable);
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
				setErrorMessage("You can not set a project as a source folder");
				return null;
			}
			
			return jElement;
		}
		return null;
	}
	
	private IJavaElement choosePackage(IJavaElement initElement,IJavaElement sourceElement) {
		StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setTitle("Packages");
		dialog.setMessage("Choose a package");
		dialog.addFilter(new PackageViewerFilter());
		dialog.setInput(JavaCore.create(ResourcesPlugin.getWorkspace().getRoot()));
		dialog.setInitialSelection(initElement);
		dialog.setAllowMultiple(false);
		dialog.setInput(sourceElement);
		
		if (dialog.open() == Window.OK) {
			IJavaElement jElement= (IJavaElement)dialog.getFirstResult();
			
			if(jElement.getElementType()==IJavaElement.JAVA_PROJECT){
				setErrorMessage("You can not set a project as a package");
				return null;
			}
			
			return (IJavaElement)jElement;
		}
		return null;
	}
	
	private void initialiseFromSelection(){
		try{
			
			Object element = selection.getFirstElement();
			
			if(!(element instanceof IResource)){
				return;
			}
			
			IJavaElement jElement = JavaCore.create((IResource)element);
			
			if(jElement==null){
				return;
			}
			
			switch(jElement.getElementType())
			{
				case IJavaElement.JAVA_PROJECT:{
					IJavaProject proj = jElement.getJavaProject();
					IPackageFragmentRoot[] roots = proj.getPackageFragmentRoots();
					
					if(roots==null){
						break;
					}
					
					for(IPackageFragmentRoot root : roots){
						if(root.isArchive()){
							continue;
						}
						
						updateSourceFolder(root);
						break;
					}
					
					break;
				}
				case IJavaElement.PACKAGE_FRAGMENT_ROOT:{
					
					updateSourceFolder(jElement);
					break;
				}
				case IJavaElement.PACKAGE_FRAGMENT:{
					updateSourceFolder(jElement.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT));
					updatePackage(jElement);
					break;
				}
			}	
			
			if(isSourceFolderEmpty()){
				setEnablePackage(false);
			}
		}catch(JavaModelException mex){
			EasybLaunchActivator.Log("Unable to intialise new behaviour from selection", mex);
			this.setErrorMessage("Unable to intialise new behaviour from selection");
		}
		
	}
}
