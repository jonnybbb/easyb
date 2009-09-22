package org.easyb.eclipse.templates.preference;

import org.easyb.eclipse.templates.TemplateActivator;
import org.easyb.eclipse.templates.manager.TemplateManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Wrapper around the activator which is externally available 
 * to clients.
 * @author whiteda
 *
 */
public class TemplatePreferenceManager {
	
	public static IPreferenceStore getTemplatePreferenceStore(){
		return TemplateActivator.getDefault().getPreferenceStore();
	}
	
	public static TemplateStore getTemplateStore(){
		return TemplateManager.getInstance().getTemplateStore();
	}
	
	public static ContextTypeRegistry getContextTypeRegistry(){
		return TemplateManager.getInstance().getContextTypeRegistry();
	}
	
	public static void savePreferences()throws BackingStoreException{
		TemplateManager.getInstance().savePreferences();
	}
}
