package org.easyb.launch.preference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class LaunchPreferenceManagerTest extends TestCase {
	private static final String EASYB_JAR = "test_easyb.jar";
	private static final String COMMONS_JAR = "test_commons.jar";
	private static final String GROOVY_JAR = "test_groovy.jar";
	private static final String FILE_REGEX = "commons-cli-[\\d|.]*jar,\\s*easyb-[\\d|.]*jar,\\s*groovy-[\\d|.]*jar";
	private static final Pattern regexPattern = Pattern.compile(FILE_REGEX);
		
	public void testLaunchPreferences()throws Exception{
		LaunchPreferenceManager.setCommoncCLIJarLocation(COMMONS_JAR);
		LaunchPreferenceManager.setEasybJarLocation(EASYB_JAR);
		LaunchPreferenceManager.setGroovyJarLocation(GROOVY_JAR);
		
		this.assertEquals(COMMONS_JAR,LaunchPreferenceManager.getCommoncCLIJar());
		this.assertEquals(EASYB_JAR,LaunchPreferenceManager.getEasybJarLocation());
		this.assertEquals(GROOVY_JAR,LaunchPreferenceManager.getGroovyJarLocation());
		
		String jars = LaunchPreferenceManager.getFormattedDefaultJarNames();
		
		Matcher matcher = regexPattern.matcher(jars);
		
		this.assertTrue(matcher.find());
		
		LaunchPreferenceManager.setDefaults();
		
		this.assertEquals("",LaunchPreferenceManager.getCommoncCLIJar());
		this.assertEquals("",LaunchPreferenceManager.getEasybJarLocation());
		this.assertEquals("",LaunchPreferenceManager.getGroovyJarLocation());

	}
}
