package org.easyb.eclipse.templates.processor;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

//TODO THis class may not be needed
public class BehaviourTemplateProposal extends TemplateProposal{
	private final Template behaviourTemplate;
	
	public BehaviourTemplateProposal(Template template, TemplateContext context, IRegion region, Image image){
		super(template,context,region,image);
		behaviourTemplate = template;
	}
	
	public String getAdditionalProposalInfo(){
		return behaviourTemplate.getDescription();
	}
}
