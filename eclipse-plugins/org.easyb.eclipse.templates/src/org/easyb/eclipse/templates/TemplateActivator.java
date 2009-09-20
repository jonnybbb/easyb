package org.easyb.eclipse.templates;

import java.io.IOException;

import org.easyb.eclipse.templates.manager.TemplateManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TemplateActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.easyb.eclipse.templates";

	// The shared instance
	private static TemplateActivator plugin;
	
	public static final String SCENARIO_TEMPLATE_NAME = "scenario";
	public static final String IT_TEMPLATE_NAME ="it";
	
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
	
	public static String getEmptyScenarioTemplateText()throws CoreException{
		Template template = 
			TemplateManager.getInstance().getTemplate(SCENARIO_TEMPLATE_NAME);
			
		return getEmptyTemplateText(template);
	}
	
	public static String getEmptySpecificationTemplateText()throws CoreException{
		Template template = 
			TemplateManager.getInstance().getTemplate(IT_TEMPLATE_NAME);
			
		return getEmptyTemplateText(template);
	}
	
	private static String getEmptyTemplateText(Template template)throws CoreException{
		if(template==null){
			return "";
		}
		
		//resolve the template
		try {
			return TemplateManager.getInstance().getEmptyDocumentResolvedPattern(template);
		} catch (TemplateException e) {
			IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, 0,"Unable to get template pattern due to template exception",e);
			Log(status);
			throw new CoreException(status);
			
		} catch (BadLocationException e) {
			IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, 0,"Unable to get template pattern due to location exception",e);
			Log(status);
			throw new CoreException(status);
		}
	}
}
