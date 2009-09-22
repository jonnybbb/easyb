package org.easyb.launch.preference;

import org.easyb.launch.EasybLaunchActivator;
import org.easyb.launch.launcher.ClasspathBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * An externally visible client for the launch 
 * preferences.
 * @author whiteda
 */
public class LaunchPreferenceManager {
	private static final String PREF_EASYB_JAR_LOCATION = "easyb_jar_location";
	private static final String PREF_CLI_JAR_LOCATION = "cli_jar_location";
	private static final String PREF_GROOVY_JAR_LOCATION = "groovy_jar_location";
	
	public static void setDefaults(){
		setEasybJarLocation("");
		setCommoncCLIJarLocation("");
		setGroovyJarLocation("");
	}
	
	public static void setEasybJarLocation(String easybJarLocation){
		getPrefStore().setValue(PREF_EASYB_JAR_LOCATION,easybJarLocation);
	}
	
	public static String getEasybJarLocation(){
		return getPrefStore().getString(PREF_EASYB_JAR_LOCATION);
	}
	
	public static void setCommoncCLIJarLocation(String easybJarLocation){
		getPrefStore().setValue(PREF_CLI_JAR_LOCATION,easybJarLocation);
	}
	
	public static String getCommoncCLIJar(){
		return getPrefStore().getString(PREF_CLI_JAR_LOCATION);
	}
	
	public static void setGroovyJarLocation(String easybJarLocation){
		getPrefStore().setValue(PREF_GROOVY_JAR_LOCATION,easybJarLocation);
	}
	
	public static String getGroovyJarLocation(){
		return getPrefStore().getString(PREF_GROOVY_JAR_LOCATION);
	}
	
	public static String getFormattedDefaultJarNames()throws CoreException{
		StringBuilder builder = new StringBuilder();
		String[] defaultJars = ClasspathBuilder.getDefaultJarNames();
		for(int i = 0;i<defaultJars.length;++i){
			builder.append(defaultJars[i]);
			
			if(i+1<defaultJars.length){
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	private static IPreferenceStore getPrefStore(){
		return EasybLaunchActivator.getLaunchPreferenceStore();
	}
}
