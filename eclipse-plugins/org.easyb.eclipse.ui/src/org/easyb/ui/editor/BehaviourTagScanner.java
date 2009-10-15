package org.easyb.ui.editor;

import java.util.Arrays;

import org.codehaus.groovy.eclipse.editor.GroovyTagScanner;
import org.eclipse.jdt.ui.text.IColorManager;

/**
 * Extends the groovy v2 plugin tag scanner by adding additonal 
 * behaviour specific tags
 * @author whiteda
 *
 */
public class BehaviourTagScanner extends GroovyTagScanner{

	//TODO Add rules so scans based on context 
	//i.e rather then just the keyword given
	//search for given "",{
	
	public BehaviourTagScanner(IColorManager manager){
		super(manager,null,Arrays.asList(KeywordEnum.toStringArray()));
	}
}
