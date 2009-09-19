package org.easyb.eclipse.templates.processor;

import org.easyb.eclipse.templates.TemplateManager;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;

public class BehaviourCompletionProcessor extends TemplateCompletionProcessor {

	@Override
	protected TemplateContextType getContextType(ITextViewer viewer,
			IRegion region) {

		return TemplateManager.getInstance().getBehaviourContextType();
	}

	@Override
	protected Image getImage(Template template) {
		return null;
	}

	@Override
	protected Template[] getTemplates(String contextTypeId) {
		return TemplateManager.getInstance().getTemplates();
	}

	@Override
	protected String extractPrefix(ITextViewer viewer, int offset) {
		
		return "";
	}
	
}
