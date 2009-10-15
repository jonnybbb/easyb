package org.easyb.eclipse.templates.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.easyb.eclipse.templates.TemplateActivator;
import org.easyb.eclipse.templates.manager.TemplateManager;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

/**
 * Calculates Behaviour completion proposals 
 * Based on AbstractTemplateCompletionProposalComputer
 * @author whiteda
 *
 */
public class BehaviourTemplateCompletionProposalComputer implements IJavaCompletionProposalComputer{

	@Override
	public List computeCompletionProposals(ContentAssistInvocationContext context, IProgressMonitor monitor) {
		if (!(context instanceof JavaContentAssistInvocationContext))
			return Collections.EMPTY_LIST;

		JavaContentAssistInvocationContext javaContext= (JavaContentAssistInvocationContext) context;
		ICompilationUnit unit= javaContext.getCompilationUnit();
		
		if (unit == null){
			return Collections.EMPTY_LIST;
		}

		//TODO when a EasybCompilationUnit can be added filter here 
		//so only stories/specifications have templates resolved not 
		//Java or Groovy compilation units
		try {
			if(!TemplateActivator.getDefault().isBehaviourFile(unit.getCorrespondingResource())){
				return Collections.EMPTY_LIST;
			}
		} catch (JavaModelException e) {
			TemplateActivator.Log("Unable to check if resouce is a easyb behaviour",e);
			return Collections.EMPTY_LIST;
		}
		
		BehaviourTemplateProposal[] templateProposals= 
			TemplateManager.getInstance().getTemplateProposals(javaContext.getViewer(),javaContext.getInvocationOffset());
		
		IJavaCompletionProposal[] keyWordResults= javaContext.getKeywordProposals();
		
		if (keyWordResults.length == 0){
			return Arrays.asList(templateProposals);
		}

		/* Update relevance of template proposals that match with a keyword
		 * give those templates slightly more relevance than the keyword to
		 * sort them first.
		 */
		for (int k= 0; k < templateProposals.length; k++) {
			BehaviourTemplateProposal curr= templateProposals[k];
			String name= curr.getTemplateName();
			for (int i= 0; i < keyWordResults.length; i++) {
				String keyword= keyWordResults[i].getDisplayString();
				if (name.startsWith(keyword)) {
					String content= curr.getTemplatePattern();
					if (content.startsWith(keyword)) {
						curr.setRelevance(keyWordResults[i].getRelevance() + 1);
						break;
					}
				}
			}
		}
			
		return Arrays.asList(templateProposals);
	}
	
	@Override
	public List computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sessionEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionStarted() {
		// TODO Auto-generated method stub
		
	}

}
