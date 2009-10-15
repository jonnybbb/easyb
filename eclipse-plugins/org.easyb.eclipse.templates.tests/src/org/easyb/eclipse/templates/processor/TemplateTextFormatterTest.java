package org.easyb.eclipse.templates.processor;

import junit.framework.TestCase;

public class TemplateTextFormatterTest extends TestCase {
	
	public void testGetEmptyTemplateText()throws Exception{
		String text = 
			TemplateTextFormatter.getEmptySpecificationTemplateText();
		
		assertTrue(text.length()>0);
		
		text = TemplateTextFormatter.getEmptyScenarioTemplateText();
		
		assertTrue(text.length()>0);
	}
}
