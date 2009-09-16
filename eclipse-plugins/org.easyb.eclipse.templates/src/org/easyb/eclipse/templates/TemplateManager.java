package org.easyb.eclipse.templates;

import java.io.IOException;

import org.easyb.eclipse.templates.context.BehaviourContextType;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.osgi.service.prefs.BackingStoreException;

public class TemplateManager {
	private static final String CUSTOM_TEMPLATES_KEY= "org.easyb.eclipse.templates.customtemplate"; 
	
	private static TemplateManager theInstance = new TemplateManager();
	
	private TemplateStore tempStore;
	private ContributionContextTypeRegistry ctxTypeRegistry;
	
	
	public static TemplateManager getInstance(){
		return theInstance;
	}
	
	public TemplateStore getTemplateStore() {
		if (tempStore == null) {
			tempStore= new ContributionTemplateStore(
					getContextTypeRegistry(),TemplateActivator.getDefault().getPreferenceStore(), CUSTOM_TEMPLATES_KEY);
			try {
				tempStore.load();
			} catch (IOException ex) {
				TemplateActivator.Log("Unable to get Template store",ex);
			}
		}
		return tempStore;
	}
	
	/**
	 * Returns this plug-in's context type registry.
	 * @return the context type registry for this plug-in instance
	 */
	public ContextTypeRegistry getContextTypeRegistry() {
		if (ctxTypeRegistry == null) {
			// create an configure the contexts available in the template editor
			ctxTypeRegistry= new ContributionContextTypeRegistry();
			ctxTypeRegistry.addContextType(BehaviourContextType.CONTEXT_TYPE);
		}
		return ctxTypeRegistry;
	}
	
	public void savePreferences()throws BackingStoreException{
		(new InstanceScope()).getNode(TemplateActivator.PLUGIN_ID).flush();
	}
	
}
