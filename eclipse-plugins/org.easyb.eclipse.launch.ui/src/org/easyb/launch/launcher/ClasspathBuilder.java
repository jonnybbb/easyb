package org.easyb.launch.launcher;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.easyb.launch.EasybLaunchActivator;
import org.easyb.launch.ILaunchConstants;
import org.easyb.launch.preference.LaunchPreferenceManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class ClasspathBuilder {
	
	public static final String[] getRuntimeClasspath(String[] classpath)throws CoreException{
		
		List<String> classpathEntries = new ArrayList<String>();
		
		if(classpath!=null){
			Collections.addAll(classpathEntries,classpath);
		}
		
		//Check to see if the user has set the jars
		//if not get from the plugins easyblib folder
		String[] prefJarPaths = getJarEntriesFromPreferences();
		if(prefJarPaths.length>0){
			Collections.addAll(classpathEntries, prefJarPaths);
		}
		else{	
			
			File libDir = getLibraryFolder();
			for(String fileName : getDefaultJarNames(libDir)){
				classpathEntries.add(libDir.getAbsolutePath()+File.separator+fileName);
			}
		}
	
		return classpathEntries.toArray(new String[classpathEntries.size()]);	
	}
	
	public static File getLibraryFolder()throws CoreException{
		
		try{
			return  new File(FileLocator.toFileURL(
					EasybLaunchActivator.getDefault().getBundle().getEntry(
							File.separator+ILaunchConstants.EASYB_RUNNER_LIB_NAME)).toURI());
		}
		catch(URISyntaxException uriex){
			Status status = new Status(IStatus.ERROR, EasybLaunchActivator.PLUGIN_ID, 0,
					"Unable to find default jar files due to URI Exception",uriex);
			throw new CoreException(status);
		}
		catch(IOException ex){
			throw new CoreException(
					new Status(IStatus.ERROR, EasybLaunchActivator.PLUGIN_ID,0,"Unable to find default jar files due to IOExcepetion",ex));
		}
	}
	
	public static String[] getDefaultJarNames()throws CoreException{
		return getDefaultJarNames(getLibraryFolder());
	}
	
	public static String[] getDefaultJarNames(File libDir)throws CoreException{ 
		if(!libDir.exists()){
			throw new CoreException(
					new Status(IStatus.ERROR, EasybLaunchActivator.PLUGIN_ID,"Easyb runtime lib "+ILaunchConstants.EASYB_RUNNER_LIB_NAME+" not found in plugin root dir"));
		}
		
		return libDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
	}
	
	public static String[] getJarEntriesFromPreferences(){
		
		String easybJarPath = LaunchPreferenceManager.getEasybJarLocation();
		
		String cliJarPath = LaunchPreferenceManager.getCommoncCLIJar();
		
		String groovyJarPath = LaunchPreferenceManager.getGroovyJarLocation();
		
		//All paths must be set for them to be used
		if(StringUtils.isBlank(easybJarPath)||
				StringUtils.isBlank(cliJarPath)||
					StringUtils.isBlank(groovyJarPath)){
				return new String[0];
			}
	
		return new String[]{easybJarPath,cliJarPath,groovyJarPath};
	}
	
	
}
