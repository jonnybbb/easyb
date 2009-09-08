package org.easyb.easybplugin.launch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.easyb.easybplugin.EasybActivator;
import org.easyb.easybplugin.IEasybLaunchConfigConstants;
import org.easyb.easybplugin.search.StorySearch;
import org.easyb.easybplugin.utils.WidgetUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
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
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * Main Easyb configuration tab allowing the user to select a single 
 * story in a project or multiple stories from a folder or project.
 * Thanks to JUnit plugin where most the inspiration for this class came 
 * from. 
 * @author whiteda
 *
 */
public class EasybMainTab extends AbstractLaunchConfigurationTab
{
	private final static String MAIN_TAB_NAME = "easyb_main_tab";
	
	//Single story controls
	private Text txtProject;
	private Text txtStory;
	private Button btnRadioSingleStory;
	private Button btnProject;
	private Button btnStory;
	private Label lblProj;
	private Label lblStory;
	
	//Multi story controls
	private Button btnRadioMultiStory;
	private Text txtMultiStories;
	private Button btnMultiStory;
	
	private IJavaElement element;
	private IFile storyFile; 
	
	private final ILabelProvider elementLabelProvider= new JavaElementLabelProvider();
	
	private class EasybMainTabListener extends SelectionAdapter implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent event) {
			updateLaunchConfigurationDialog();
		}
	}
	
	private SelectionAdapter projectSelectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e){
			IJavaProject proj = chooseJavaProject();
			txtProject.setText(proj.getElementName());
			updateLaunchConfigurationDialog();
		}
	};
	
	private SelectionAdapter resourceSelectionAdapter = new SelectionAdapter(){ 
		public void widgetSelected(SelectionEvent e){
			IJavaElement newElement = chooseContainer(element);
			
			if(newElement!=null){
				updateElement(newElement);
			}
		} 
	};
	
	private EasybMainTabListener tabListener = new EasybMainTabListener();

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent,SWT.NONE);
		setControl(comp);
		
		GridLayout mainLayout = new GridLayout(3,false);
		comp.setLayout(mainLayout);
		
		createSingleStoryControls(comp);
		createMultiStoryControls(comp);
		
	}
	
	@Override
	public String getName() {
		return MAIN_TAB_NAME;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration config) {

		try {
			txtProject.setText(config.getAttribute(
					IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""));
		} catch (CoreException ce) {
			EasybActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, EasybActivator.PLUGIN_ID, 0,
							"Unable to set project name for launch", ce));

			setErrorMessage("Unable to set project from configuration");
		}

		initialiseStoriesfromConfiguration(config);
	}
	
	public void dispose() {
		super.dispose();
		elementLabelProvider.dispose();
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		
		
			config.setAttribute(
					IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME,StringUtils.trimToEmpty(txtProject.getText()));
			
			try{
				List<String> stories = getStoriesFullPaths();
				config.setAttribute( IEasybLaunchConfigConstants.LAUNCH_ATTR_STORIES_FULL_PATH,stories);
			}catch(CoreException cex){
				EasybActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, EasybActivator.PLUGIN_ID, 0,
								"Unable apply configuration due to exception while retrieving story locations", cex));
				setErrorMessage("Unable apply configuration due to exception while retrieving story locations");
			}
			
			config.setAttribute(
					IEasybLaunchConfigConstants.LAUNCH_ATTR_PACKAGE_FOLDER,StringUtils.trimToEmpty(txtMultiStories.getText()));
		
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		
		if(!super.isValid(launchConfig)){
			return false;
		}
		
		if(StringUtils.isBlank(txtProject.getText())){
			setErrorMessage("A project has not been set");
			return false;
		}
		
		if(StringUtils.isBlank(txtStory.getText()) && 
				StringUtils.isBlank(txtMultiStories.getText())){
			setErrorMessage("A story has not been set");
			return false;
		}
		
		return true;
	}
	
	protected void initialiseStoriesfromConfiguration(ILaunchConfiguration config){
		List<String> stories = null;
		try {
			stories = config.getAttribute(
					IEasybLaunchConfigConstants.LAUNCH_ATTR_STORIES_FULL_PATH,
					new ArrayList<String>(0));

			if (stories.size() == 0) {
				return;
			}

		} catch (CoreException ce) {
			EasybActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, EasybActivator.PLUGIN_ID, 0,
							"Unable to get stories for launch", ce));

			setErrorMessage("Unable to get stories for launch");
		}

		String packageFolder = "";
		try {
			packageFolder = config.getAttribute(
					IEasybLaunchConfigConstants.LAUNCH_ATTR_PACKAGE_FOLDER, "");
		} catch (CoreException ce) {
			EasybActivator
					.getDefault()
					.getLog()
					.log(
							new Status(
									IStatus.ERROR,
									EasybActivator.PLUGIN_ID,
									0,
									"Unable to set project,resource or package name for launch",
									ce));

			setErrorMessage("Unable to set project,folder or package for stories from configuration");
		}

		if (!StringUtils.isBlank(packageFolder)) {

			txtMultiStories.setText(packageFolder);
		} else if (stories != null && stories.size() > 0) {
			txtStory.setText(stories.get(0));
		}
	}
	
	protected List<String> getStoriesFullPaths()throws CoreException{
		List<String> stories = null;;
	
		if(btnRadioSingleStory.getSelection()){
			String singleStory = StringUtils.trimToEmpty(txtStory.getText());
			stories = new ArrayList<String>(1);
			stories.add(singleStory);
			
		}else{
			stories = StorySearch.findStoryPaths(element.getResource());
		}
		
		return stories;
	}
	
	private void createSingleStoryControls(Composite comp){
		
		btnRadioSingleStory = new Button(comp,SWT.RADIO);
		btnRadioSingleStory.setText("Run a single story");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		btnRadioSingleStory.setLayoutData(gridData);
		btnRadioSingleStory.setSelection(true);
		btnRadioSingleStory.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (btnRadioSingleStory.getSelection()){
					changeStoryMode();
				}	
			}
		});
		
		lblProj = new Label(comp,SWT.NONE);
		lblProj.setText("Project:");
		gridData = new GridData();
		gridData.horizontalIndent = 25;
		lblProj.setLayoutData(gridData);
		
		txtProject = new Text(comp, SWT.SINGLE | SWT.BORDER);
		txtProject.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtProject.addModifyListener(tabListener);
		
		btnProject = new Button(comp,SWT.PUSH);
		btnProject.setText("Browse");
		btnProject.addSelectionListener(projectSelectionListener);
		setButtonGridData(btnProject);
		
		lblStory = new Label(comp,SWT.NONE);
		lblStory.setText("Story:");
		gridData = new GridData();
		gridData.horizontalIndent = 25;
		lblStory.setLayoutData(gridData);
		
		txtStory = new Text(comp, SWT.SINGLE | SWT.BORDER);
		txtStory.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtStory.addModifyListener(tabListener);
		
		btnStory = new Button(comp,SWT.PUSH);
		btnStory.setText("Browse");
		setButtonGridData(btnStory);
	}
	
	private void createMultiStoryControls(Composite comp){
		btnRadioMultiStory = new Button(comp,SWT.RADIO);
		btnRadioMultiStory.setText("Run stories from project,package or source folder");
		GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		btnRadioMultiStory.setLayoutData(gridData);
		btnRadioMultiStory.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (btnRadioMultiStory.getSelection()){
					changeStoryMode();
				}	
			}
		});
		 
		txtMultiStories = new Text(comp, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		txtMultiStories.addModifyListener(tabListener);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalIndent= 25;
		gridData.horizontalSpan = 2;
		txtMultiStories.setLayoutData(gridData);
		
		btnMultiStory = new Button(comp,SWT.PUSH);
		btnMultiStory.setText("Browse");
		btnMultiStory.addSelectionListener(resourceSelectionAdapter);
		setButtonGridData(btnMultiStory);
	}
	
	private void setButtonGridData(Button button){
		GridData gridData= new GridData();
		button.setLayoutData(gridData);
		WidgetUtil.setButtonDimensionHint(button);
	}
	
	private void changeStoryMode(){
		boolean singleStory  = btnRadioSingleStory.getSelection();
		setEnableSingleStory(singleStory);
		setEnableMultiStory(!singleStory);
	}
	
	private void setEnableSingleStory(boolean enabled){
		txtProject.setEnabled(enabled);
		txtStory.setEnabled(enabled);
		btnProject.setEnabled(enabled);
		btnStory.setEnabled(enabled);
		lblProj.setEnabled(enabled);
		lblStory.setEnabled(enabled);
	}
	
	private void setEnableMultiStory(boolean enabled){
		txtMultiStories.setEnabled(enabled);
		btnMultiStory.setEnabled(enabled);
	}
	
	private void updateElement(IJavaElement newElement){
		if(newElement==null){
			return;
		}
		
		element = newElement;
		
		txtMultiStories.setText(elementLabelProvider.getText(element));
		updateLaunchConfigurationDialog();
	}
	
	private IWorkspaceRoot getWorkspaceRoot(){
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	private IJavaProject chooseJavaProject() {
		IJavaProject[] projects;
		try {
			projects= JavaCore.create(getWorkspaceRoot()).getJavaProjects();
		} catch (JavaModelException e) {
			EasybActivator.getDefault().getLog().log(e.getStatus());
			projects= new IJavaProject[0];
		}

		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle("Projects");
		dialog.setMessage("Please choose a project");
		dialog.setElements(projects);

		IJavaProject javaProject = getJavaProject();
		if (javaProject != null) {
			dialog.setInitialSelections(new Object[] { javaProject });
		}
		if (dialog.open() == Window.OK) {
			return (IJavaProject) dialog.getFirstResult();
		}
		return null;
	}
	
	private IJavaProject getJavaProject(){
		
		String projTxt = StringUtils.trimToEmpty(txtProject.getText());
		
		if(StringUtils.isBlank(projTxt)){
			return null;
		}
		
		IJavaModel model = JavaCore.create(getWorkspaceRoot());
		return model.getJavaProject(projTxt);
	}
	
	//TODO Refactor to not use restricted API 
	private IJavaElement chooseContainer(IJavaElement initElement) {
		Class[] acceptedClasses= new Class[] { IPackageFragmentRoot.class, IJavaProject.class, IPackageFragment.class };
		TypedElementSelectionValidator validator= new TypedElementSelectionValidator(acceptedClasses, false) {
			public boolean isSelectedValid(Object element) {
				return true;
			}
		};

		acceptedClasses= new Class[] { IJavaModel.class, IPackageFragmentRoot.class, IJavaProject.class, IPackageFragment.class };
		ViewerFilter filter= new TypedViewerFilter(acceptedClasses) {
			public boolean select(Viewer viewer, Object parent, Object element) {
			    if (element instanceof IPackageFragmentRoot && ((IPackageFragmentRoot)element).isArchive())
			        return false;
			    try {
					if (element instanceof IPackageFragment && !((IPackageFragment) element).hasChildren()) {
						return false;
					}
				} catch (JavaModelException e) {
					return false;
				}
				return super.select(viewer, parent, element);
			}
		};

		StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setValidator(validator);
		dialog.setComparator(new JavaElementComparator());
		dialog.setTitle("Elements");
		dialog.setMessage("Choos package or folder");
		dialog.addFilter(filter);
		dialog.setInput(JavaCore.create(getWorkspaceRoot()));
		dialog.setInitialSelection(initElement);
		dialog.setAllowMultiple(false);

		if (dialog.open() == Window.OK) {
			Object element= dialog.getFirstResult();
			return (IJavaElement)element;
		}
		return null;
	}
	
	
	private IFile chooseStory()throws CoreException{
		IJavaProject javaProject = getJavaProject();
		StorySearch.findStoryFiles(javaProject.getResource());
		return null;
	}
	

}
