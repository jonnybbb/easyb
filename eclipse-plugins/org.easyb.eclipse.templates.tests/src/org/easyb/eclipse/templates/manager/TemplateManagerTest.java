package org.easyb.eclipse.templates.manager;

import junit.framework.TestCase;

import org.easyb.eclipse.templates.processor.BehaviourTemplateProposal;
import org.easyb.eclipse.test.tools.EditorTool;
import org.easyb.eclipse.test.tools.ProjectTool;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.ui.IEditorPart;

public class TemplateManagerTest extends TestCase{

	private static final String GIVEN_TEMPLATE = "given";
	private IEditorPart editor;
	@Override
	protected void setUp()throws Exception{
		IFile file = 
			ProjectTool.setupJavaProjectAndBehaviourFile("test.story","test",getStory());
		
		editor = EditorTool.openEditor(file);
	}
	
	private String getStory(){
		return "given \"blah blah\"{}";
	}
	public void testGetTemplates(){
		Template[] templates = 
			TemplateManager.getInstance().getTemplates();
		
		assertNotNull(templates);
		
		for(Template template : templates){
			assertNotNull(template.getPattern());
			assertNotNull(template.getName());
			assertTrue(template.getPattern().trim().length()>0);
			assertTrue(template.getName().trim().length()>0);
		}
		
	}
	
	public void testGetTemplate(){
		Template template = 
			TemplateManager.getInstance().getTemplate("given");
		
		assertNotNull(template);
		assertTrue(template.getPattern().trim().length()>0);
		assertTrue(template.getName().trim().length()>0);
	}
	
	public void testGetEmptyDocument()throws Exception{
		Template template = 
			TemplateManager.getInstance().getTemplate(GIVEN_TEMPLATE);
		
		String pattern = 
			TemplateManager.getInstance().getEmptyDocumentResolvedPattern(template);
		
		assertTrue(pattern.length()>0);
		assertFalse(pattern.contains("$"));
	}
	
	
	public void testGetTemplateProposals(){

		ITextViewer viewer = EditorTool.getTextViewer(editor);
		
		BehaviourTemplateProposal[] proposals = 
			TemplateManager.getInstance().getTemplateProposals(viewer,1);
		
		//Should only find the "given" template
		assertTrue(proposals.length==1);
		assertEquals("given",proposals[0].getTemplateName());
	}
}
