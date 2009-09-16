package org.easyb.eclipse.templates.preferences;

import org.easyb.eclipse.templates.TemplateManager;
import org.easyb.eclipse.templates.TemplateActivator;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;
import org.osgi.service.prefs.BackingStoreException;


public class BehaviourTemplatePreferencePage extends TemplatePreferencePage {
	
	public BehaviourTemplatePreferencePage(){
		setupPreferences();
	}
	
	public boolean performOk() {
		boolean ok= super.performOk();
		//Save the preferences
		try{
			TemplateManager.getInstance().savePreferences();
		}catch(BackingStoreException bex){
			TemplateActivator.Log("Unable to save easyb template preferences due to problem with TemplateStore", bex);
			setErrorMessage("Unable to save easyb template preferences");
		}
		
		return ok;
	}
	
	private void setupPreferences(){
	try{
		setPreferenceStore(TemplateActivator.getDefault().getPreferenceStore());
		setTemplateStore(TemplateManager.getInstance().getTemplateStore());
		setContextTypeRegistry(TemplateManager.getInstance().getContextTypeRegistry());
		}catch(Exception ex){
			TemplateActivator.Log("Unable to construct BehaviourTemplatePreferencePage",ex);
			setErrorMessage("Unable to setup behaviour template preference page");
		}
	}
}
