package org.easyb.eclipse.templates.processor;

import org.easyb.eclipse.templates.TemplateActivator;
import org.easyb.eclipse.templates.manager.TemplateManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateException;

/**
 * External clients can access this class to get formatted 
 * template patterns
 * @author whiteda
 *
 */
public class TemplateTextFormatter {
	public static final String SCENARIO_TEMPLATE_NAME = "scenario";
	public static final String IT_TEMPLATE_NAME ="it";
	
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
			IStatus status = new Status(IStatus.ERROR, TemplateActivator.PLUGIN_ID, 0,"Unable to get template pattern due to template exception",e);
			TemplateActivator.Log(status);
			throw new CoreException(status);
			
		} catch (BadLocationException e) {
			IStatus status = new Status(IStatus.ERROR, TemplateActivator.PLUGIN_ID, 0,"Unable to get template pattern due to location exception",e);
			TemplateActivator.Log(status);
			throw new CoreException(status);
		}
	}
}
