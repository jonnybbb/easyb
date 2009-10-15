package org.easyb.eclipse.templates.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.easyb.eclipse.templates.TemplateActivator;
import org.easyb.eclipse.templates.context.BehaviourContextType;
import org.easyb.eclipse.templates.processor.BehaviourTemplateProposal;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;
import org.osgi.service.prefs.BackingStoreException;

public class TemplateManager {
	private static final String REGEX= "\\w*";
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
	
	public Template[] getTemplates(){
		return getTemplateStore().getTemplates();
	}
	
	public TemplateContextType getBehaviourContextType(){
		return getContextTypeRegistry().getContextType(BehaviourContextType.CONTEXT_TYPE);
	}
	
	public Template getTemplate(String templateName){
		Template[] templates = getTemplates();
		
		for(Template template : templates){
			if(template.getName().equals(templateName)){
				return template;
			}
		}
		
		return null;
	}
	
	public String getEmptyDocumentResolvedPattern(Template template)throws TemplateException,BadLocationException{
		IDocument document = new Document("");
		return getResolvedPattern(template,document,0,document.getLength());
	}

	public String getResolvedPattern(Template template,IDocument document,int offset,int length)throws TemplateException,BadLocationException{

		DocumentTemplateContext ctx = 
			new DocumentTemplateContext(getBehaviourContextType(),document,offset,length);
		
		TemplateBuffer buffer = ctx.evaluate(template);
		
		return buffer.getString();
	}
	
	public BehaviourTemplateProposal[] getTemplateProposals(ITextViewer viewer,int offset){
		
		ITextSelection selection= (ITextSelection) viewer.getSelectionProvider().getSelection();
		// adjust offset to end of normalized selection
		if (selection.getOffset() == offset){
			offset= selection.getOffset() + selection.getLength();
		}
		
		Position position = null;
		int startOffset= offset;
		String prefix = "";
		IDocument document= viewer.getDocument();
		if (startOffset <= document.getLength())
		{
			try {
					while (startOffset > 0) {
						char ch= document.getChar(startOffset - 1);
						if (!Character.isJavaIdentifierPart(ch))
							break;
						startOffset--;
					}
					
					prefix = document.get(startOffset, offset - startOffset);
					position = new Position(startOffset,offset - startOffset);
				} catch (BadLocationException e) {
					TemplateActivator.Log("Unable to get prefix for template match", e);//$NON-NLS-1$
				}
		}else{
			position = new Position(offset,offset);
		}
		
		DocumentTemplateContext ctx = new DocumentTemplateContext(getBehaviourContextType(), document,position);
		
		int start= ctx.getStart();
		int end= ctx.getEnd();
		IRegion region= new Region(start, end - start);
		
		Template[] templates = getTemplates();
		List<BehaviourTemplateProposal> proposals = new ArrayList<BehaviourTemplateProposal>();
		for(int i = 0;i<templates.length;++i){
			if(isMatch(templates[i].getName(),prefix)){
				proposals.add( new BehaviourTemplateProposal(templates[i],ctx,region,null));
			}
		}
		
		return proposals.toArray(new BehaviourTemplateProposal[proposals.size()]);
	}
	
	protected static boolean isMatch(String keyword,String txt){
		return keyword.matches(txt+REGEX);
	}
}
