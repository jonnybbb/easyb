package org.easyb.ui.preferences;

import org.easyb.eclipse.templates.preference.TemplatePreferenceManager;
import org.easyb.ui.EasybUIActivator;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Preference page for settign the template preferences.
 * @author whiteda
 */
//TODO Ideally this would be in the  org.easyb.eclipse.templates
//but there doesn`t appear to be a way to have different plugins
//share the same tempalte page hiearchy in the preference page dialog
public class BehaviourTemplatePreferencePage extends TemplatePreferencePage {
	
	public BehaviourTemplatePreferencePage(){
		setupPreferences();
	}
	
	public boolean performOk() {
		boolean ok= super.performOk();
		//Save the preferences
		try{
			TemplatePreferenceManager.savePreferences();
		}catch(BackingStoreException bex){
			EasybUIActivator.Log("Unable to save easyb template preferences due to problem with TemplateStore", bex);
			setErrorMessage("Unable to save easyb template preferences");
		}
		
		return ok;
	}
	
	private void setupPreferences(){
		setPreferenceStore(TemplatePreferenceManager.getTemplatePreferenceStore());
		setTemplateStore(TemplatePreferenceManager.getTemplateStore());
		setContextTypeRegistry(TemplatePreferenceManager.getContextTypeRegistry());
	}
}
