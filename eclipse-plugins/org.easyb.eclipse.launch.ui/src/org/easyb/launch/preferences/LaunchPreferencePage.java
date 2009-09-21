package org.easyb.launch.preferences;

import org.apache.commons.lang.StringUtils;
import org.easyb.launch.EasybLaunchActivator;
import org.easyb.launch.ILaunchConstants;
import org.easyb.launch.launcher.ClasspathBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Sets alternate jars to use for launching behaviours.
 * All jars have to be set other wise the default ones bundled 
 * with plugin will be used.
 * @author whiteda
 *
 */
public class LaunchPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private static final String FILE_LOOK_UP_TXT = "...";
	private static final String JAR_EXTENSION = "*.jar";
	private Label lblEasybJar;
	private Text txtEasybJar;
	private Button btnEasybJar;
	
	private Label lblCliJar;
	private Text txtCliJar;
	private Button btnCliJar;
	
	private Label lblGroovyJar;
	private Text txtGroovyJar;
	private Button btnGroovyJar;
	
	private Label lblDescription;
	private Label lblDefaultJars;
	
	private FileDialog fDlg;
	private String easybPath="";
	private String cliPath = "";
	private String groovyPath = "";
	
	private ModifyListener txtModifyListener = new ModifyListener(){
		@Override
		public void modifyText(ModifyEvent e) {
			if(isJarsSet()|| isJarsEmpty()){
				setValid(true);
			}else{
				setValid(false);
			}
			
		}
	};
	
	private SelectionListener btnEasybSelect = new SelectionAdapter(){

		@Override
		public void widgetSelected(SelectionEvent e) {
			fDlg.setText("Choose easyb jar");
			fDlg.setFilterPath(txtEasybJar.getText());
			easybPath = fDlg.open();
			txtEasybJar.setText(easybPath);
		}
	
	};
	
	private SelectionAdapter btnCliSelect = new SelectionAdapter(){

		@Override
		public void widgetSelected(SelectionEvent e) {
			fDlg.setFilterPath(txtCliJar.getText());
			fDlg.setText("Choose commons-cli jar");
			cliPath = fDlg.open();
			txtCliJar.setText(cliPath);
		}
	
	};
	
	private SelectionAdapter btnGroovySelect = new SelectionAdapter(){

		@Override
		public void widgetSelected(SelectionEvent e) {
			fDlg.setText("Choose groovy jar");
			fDlg.setFilterPath(txtGroovyJar.getText());
			groovyPath = fDlg.open();
			txtGroovyJar.setText(groovyPath);
		}
	
	};
	
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected Control createContents(Composite parent) {
		
		//Set up file dialog
		fDlg = new FileDialog(getShell(),SWT.OPEN);
		fDlg.setFilterExtensions(new String[]{JAR_EXTENSION});
		
		//Set controls
		
		lblDescription = new Label(parent,SWT.NONE);
		lblDescription.setText("Specifiy alternative jars to the default ones bundled with the plugin:");
		
		lblDefaultJars = new Label(parent,SWT.NONE);
		lblDefaultJars.setText(getDefaultJars());
		
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText("Set jar paths");
		
		GridLayout layout = new GridLayout();
		
		layout.numColumns = 3;
		layout.marginBottom = 0;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		
		group.setLayout(layout);
		
		lblEasybJar = new Label(group,SWT.NONE);
		lblEasybJar.setText("Easyb jar:");
		
		txtEasybJar = new Text(group,SWT.SINGLE | SWT.BORDER);
		txtEasybJar.addModifyListener(txtModifyListener);
		
		btnEasybJar = new Button(group,SWT.PUSH);
		btnEasybJar.setText(FILE_LOOK_UP_TXT);
		btnEasybJar.addSelectionListener(btnEasybSelect);
		
		lblCliJar = new Label(group,SWT.NONE);
		lblCliJar.setText("Commons cli jar:");
		
		txtCliJar = new Text(group,SWT.SINGLE | SWT.BORDER);
		txtCliJar.addModifyListener(txtModifyListener);
		
		btnCliJar = new Button(group,SWT.PUSH);
		btnCliJar.setText(FILE_LOOK_UP_TXT);
		btnCliJar.addSelectionListener(btnCliSelect);
		
		lblGroovyJar = new Label(group,SWT.NONE);
		lblGroovyJar.setText("Groovy jar:");
		
		txtGroovyJar = new Text(group,SWT.SINGLE | SWT.BORDER);
		txtGroovyJar.addModifyListener(txtModifyListener);
		
		btnGroovyJar = new Button(group,SWT.PUSH);
		btnGroovyJar.setText(FILE_LOOK_UP_TXT);
		btnGroovyJar.addSelectionListener(btnGroovySelect);
		
		intialiseFromPreferences();
		
		return group;
	}
	
	@Override 
	protected void performDefaults(){
		getPrefStore().setValue(ILaunchConstants.PREF_EASYB_JAR_PATH,"");
		getPrefStore().setValue(ILaunchConstants.PREF_CLI_JAR_PATH,"");
		getPrefStore().setValue(ILaunchConstants.PREF_GROOVY_JAR_PATH,"");
		super.performDefaults();
	}
	
	@Override
	protected void performApply() {
			
		if(!StringUtils.isBlank(txtEasybJar.getText())){
			getPrefStore().setValue(ILaunchConstants.PREF_EASYB_JAR_PATH,txtEasybJar.getText());
		}
		
		if(!StringUtils.isBlank(txtCliJar.getText())){
			getPrefStore().setValue(ILaunchConstants.PREF_CLI_JAR_PATH,txtCliJar.getText());
		}
		
		if(!StringUtils.isBlank(txtGroovyJar.getText())){
			getPrefStore().setValue(ILaunchConstants.PREF_GROOVY_JAR_PATH,txtGroovyJar.getText());
		}
		
		super.performApply();
	}
	
	@Override
	public boolean performOk() {
		
		//Either all set or all empty
		if(isJarsSet()||isJarsEmpty()){
			return true;
		}
	
		return super.performOk();
	}
	
	private boolean isJarsSet(){
		
		if(!StringUtils.isBlank(txtEasybJar.getText()) && 
				!StringUtils.isBlank(txtCliJar.getText()) && 
					!StringUtils.isBlank(txtGroovyJar.getText())){
			return true;
		}
		
		return false;
	}
	
	private boolean isJarsEmpty(){
		if(StringUtils.isBlank(txtEasybJar.getText()) && 
				StringUtils.isBlank(txtCliJar.getText()) && 
					StringUtils.isBlank(txtGroovyJar.getText())){
			return true;
		}
		
		return false;
	}

	private IPreferenceStore getPrefStore(){
		return EasybLaunchActivator.getDefault().getPreferenceStore();
	}
	
	private void intialiseFromPreferences(){
		
		String easybJarPath = 
			getPrefStore().getString(ILaunchConstants.PREF_EASYB_JAR_PATH);
		
		txtEasybJar.setText(easybJarPath);
		
		String cliJarPath = 
			getPrefStore().getString(ILaunchConstants.PREF_CLI_JAR_PATH);
		
		txtCliJar.setText(cliJarPath);
		
		String groovyJarPath = 
			getPrefStore().getString(ILaunchConstants.PREF_GROOVY_JAR_PATH);
		
		txtGroovyJar.setText(groovyJarPath);
	}
	
	private String getDefaultJars(){
		StringBuilder builder = new StringBuilder();
		try{

			String[] defaultJars = ClasspathBuilder.getDefaultJarNames();
			for(int i = 0;i<defaultJars.length;++i){
				builder.append(defaultJars[i]);
				
				if(i+1<defaultJars.length){
					builder.append(", ");
				}
			}
		}catch(CoreException cex){
			String error = "Unable to display default jars";
			EasybLaunchActivator.Log(error, cex);
			this.setErrorMessage(error);
			return "<"+error+">";
		}
		
		return builder.toString();
	}

}
