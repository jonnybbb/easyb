package org.easyb.launch.search;

import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.easyb.eclipse.test.tools.ProjectTool;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;

public class BehaviourSearchTest extends TestCase {
	private static final String NO_PCKG_FILE_REGEX = "[\\w|\\s|:|\\\\|\\-|_]*\\\\test\\\\test.story";
	private static final String PCKG_FILE_REGEX =    "[\\w|\\s|:|\\\\|\\-|_]*\\\\test\\\\testPackage\\\\test2.story";
	
	private static final Pattern NO_PCKG_PATTERN = Pattern.compile(NO_PCKG_FILE_REGEX);
	private static final Pattern PCKG_PATTERN = Pattern.compile(PCKG_FILE_REGEX);
	
	private IJavaProject proj;

	@Override
	protected void setUp()throws Exception{
		
		proj = ProjectTool.setupJavaProject("test");
		ProjectTool.createBehaviourFile(proj,"test.story",ProjectTool.getStoryText());
		
		IPackageFragment frag = ProjectTool.createPackage(proj,"testPackage");
		ProjectTool.createBehaviourInPackage(frag,"test2.story",ProjectTool.getStoryText());
	}
	
	public void testFindStoryPaths()throws Exception{
		
		List<String> paths = BehaviourSearch.findStoryPaths(proj.getProject());
		
		boolean noPckgFound = false;
		boolean pckFound = false;
		for(String path : paths){
			
			if(NO_PCKG_PATTERN.matcher(path).find()){
				noPckgFound = true;
			}
			
			if(PCKG_PATTERN.matcher(path).find()){
				pckFound =true;
			}
		}
		
		assertTrue(noPckgFound);
		assertTrue(pckFound);
	}
	
	public void testFindStoryFiles()throws Exception{
		IFile[] files = BehaviourSearch.findStoryFiles(proj.getProject());
		
		boolean noPckgFound = false;
		boolean pckFound = false;
		for(IFile file : files){
			String filePath = file.getRawLocation().toOSString();
			if(NO_PCKG_PATTERN.matcher(filePath).find()){
				noPckgFound = true;
			}
			
			if(PCKG_PATTERN.matcher(filePath).find()){
				pckFound =true;
			}
		}
		
		assertTrue(noPckgFound);
		assertTrue(pckFound);
	}
}
