package org.easyb.eclipse.templates;

import org.easyb.eclipse.templates.manager.TemplateManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

/**
 * The activator class controls the plug-in life cycle
 */
public class TemplateActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.easyb.eclipse.templates";

	// The shared instance
	private static TemplateActivator plugin;
	
	/**
	 * The constructor
	 */
	public TemplateActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TemplateActivator getDefault() {
		return plugin;
	}
	
	public static void Log(String message){
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID,message));
	}

	public static void Log(String message,Exception ex){
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID,0,message,ex));
	}
	
	public static void Log(IStatus status){
		getDefault().getLog().log(status);
	}
}
