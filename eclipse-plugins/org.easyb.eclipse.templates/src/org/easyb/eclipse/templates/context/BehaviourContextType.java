package org.easyb.eclipse.templates.context;

import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.TemplateContextType;

public class BehaviourContextType extends TemplateContextType{
	public static final String CONTEXT_TYPE = 
		"org.easyb.eclipse.templates.context.BehaviourContextType";
	
	public BehaviourContextType() {
		addResolver(new GlobalTemplateVariables.Cursor());
		addResolver(new GlobalTemplateVariables.WordSelection());
		addResolver(new GlobalTemplateVariables.LineSelection());
	}
}
