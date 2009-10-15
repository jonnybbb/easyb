package org.easyb.eclipse.templates.processor;

import org.eclipse.jdt.internal.corext.template.java.JavaDocContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

//TODO THis class may not be needed
public class BehaviourTemplateProposal extends TemplateProposal{
	private final Template template;
	private final TemplateContext context;
	private final IRegion region;
	private int relevance;
	
	public BehaviourTemplateProposal(Template template, TemplateContext context, IRegion region, Image image){
		super(template,context,region,image);
		this.template = template;
		this.context = context;
		this.region = region;
		relevance = computeRelevance();
	}
	
	public String getAdditionalProposalInfo(){
		return template.getDescription();
	}
	
	private int computeRelevance() {
		// see org.eclipse.jdt.internal.codeassist.RelevanceConstants
		final int R_DEFAULT= 0;
		final int R_INTERESTING= 5;
		final int R_CASE= 10;
		final int R_NON_RESTRICTED= 3;
		final int R_EXACT_NAME = 4;
		final int R_INLINE_TAG = 31;

		int base= R_DEFAULT + R_INTERESTING + R_NON_RESTRICTED;
		try {
			if (context instanceof DocumentTemplateContext) {
				DocumentTemplateContext templateContext= (DocumentTemplateContext) context;
				IDocument document= templateContext.getDocument();

				String content= document.get(region.getOffset(), region.getLength());
				if (template.getName().startsWith(content))
					base += R_CASE;
				if (template.getName().equalsIgnoreCase(content))
					base += R_EXACT_NAME;
				if (context instanceof JavaDocContext)
					base += R_INLINE_TAG;
			}
		} catch (BadLocationException e) {
			// ignore - not a case sensitive match then
		}

		// see CompletionProposalCollector.computeRelevance
		// just under keywords, but better than packages
		final int TEMPLATE_RELEVANCE= 1;
		return base * 16 + TEMPLATE_RELEVANCE;
	}
	
	@Override
	public int getRelevance() {
		return relevance;
	}
	
	public String getTemplateName(){
		return template.getName();
	}
	
	public String getTemplatePattern(){
		if(template==null){
			return "";
		}
		return template.getPattern();
	}
	
	public void setRelevance(int relevance){
		this.relevance = relevance;
	}
	
}
