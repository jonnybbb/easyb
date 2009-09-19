package org.easyb.eclipse.templates.processor;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import org.easyb.eclipse.templates.TemplateActivator;
import org.easyb.eclipse.templates.TemplateManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;

public class BehaviourCompletionProcessor extends TemplateCompletionProcessor {
	private static final String REGEX= "\\w*";
	
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
	public  ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		ITextSelection selection= (ITextSelection) viewer.getSelectionProvider().getSelection();
		// adjust offset to end of normalized selection
		if (selection.getOffset() == offset){
			offset= selection.getOffset() + selection.getLength();
		}
		
		String prefix= extractPrefix(viewer, offset);	
		
		Region region= new Region(offset - prefix.length(), prefix.length());
		TemplateContext context= createContext(viewer, region);
		
		if(context==null){
			return super.computeCompletionProposals(viewer,offset);
		}
		
		Template[] templates = getTemplates(context.getContextType().getId());
		
		//Try and match the templates by the name
		List<ICompletionProposal> matches= new ArrayList<ICompletionProposal>();
		for(Template template : templates){
			if(isMatch(template.getName(),prefix)){
				matches.add(createProposal(
						template, context, (IRegion) region, getRelevance(template, prefix)));
			}
		}
		
		if(matches.size()==0){
			return super.computeCompletionProposals(viewer,offset);
		}
		
		return matches.toArray(new ICompletionProposal[matches.size()]);
		
	}
	
	protected static boolean isMatch(String keyword,String txt){
		//TODO handle _
		return keyword.matches(txt+REGEX);
	}
	
}
