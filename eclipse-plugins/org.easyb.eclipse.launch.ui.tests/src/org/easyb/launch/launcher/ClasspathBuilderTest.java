package org.easyb.launch.launcher;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class ClasspathBuilderTest extends TestCase{
	private static final String EASYB_JAR_NAME = "easyb-0.9.6.jar";
	private static final String GROOVY_JAR_NAME = "groovy-1.6.4.jar";
	private static final String CLI_JAR_NAME = "commons-cli-1.2.jar";
	private static final String BUNDLE_PATH = File.separator+
												"org.easyb.eclipse.launch.ui"+
												File.separator+"resources"+
												File.separator+"easyblib"+
												File.separator;
	@Override
	protected void setUp() throws Exception {
	
	}
	
	public void testGetRuntimeClassPath()throws Exception{
		String[] clzPath = new String[]{"test.jar","test2.jar"};
		
		String[] newClzPath = 
			ClasspathBuilder.getRuntimeClasspath(clzPath);
		
		//Absolute classpath so shouldn`t start at 0
		//for bundle plugins
		int foundPath = 0;
		for(String path : newClzPath){
			if(path.lastIndexOf(BUNDLE_PATH+EASYB_JAR_NAME)>0){
				foundPath++;
			}else if(path.lastIndexOf(BUNDLE_PATH+GROOVY_JAR_NAME)>0){
				foundPath++;
			}else if(path.lastIndexOf(BUNDLE_PATH+CLI_JAR_NAME)>0){
				foundPath++;
			}else if(path.lastIndexOf(clzPath[0])>=0){
				foundPath++;
			}else if(path.lastIndexOf(clzPath[1])>=0){
				foundPath++;
			}
		}
		
		assertEquals(5,foundPath);
	}
	
	public void testGetLibraryFolder()throws Exception{
		File folder = ClasspathBuilder.getLibraryFolder();
		
		assertTrue(folder.exists());
		assertTrue(folder.canRead());
		assertTrue(folder.isDirectory());
		assertFalse(folder.isHidden());
		
	}
	
	public void testGetDefaultJarNames()throws Exception{
		
		String[] newClzPath = ClasspathBuilder.getDefaultJarNames();
		
		List<String> listPath = Arrays.asList(newClzPath);
		assertTrue(listPath.contains(EASYB_JAR_NAME));
		assertTrue(listPath.contains(GROOVY_JAR_NAME));
		assertTrue(listPath.contains(CLI_JAR_NAME));
	}
	
}
