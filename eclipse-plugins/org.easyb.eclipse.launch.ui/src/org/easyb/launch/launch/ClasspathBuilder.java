package org.easyb.launch.launch;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easyb.launch.EasybLaunchActivator;
import org.easyb.launch.ILaunchConfigConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class ClasspathBuilder {
	
	public static final String[] getRuntimeClasspath(String[] classpath)throws CoreException{
		
		try
		{
			List<String> classpathEntries = new ArrayList<String>();
			
			if(classpath!=null){
				Collections.addAll(classpathEntries,classpath);
			}
			
			File libDir = new File(FileLocator.toFileURL(
					EasybLaunchActivator.getDefault().getBundle().getEntry(
							"/"+ILaunchConfigConstants.EASYB_RUNNER_LIB_NAME)).toURI());  
			   
			if(!libDir.exists()){
				throw new CoreException(
						new Status(IStatus.ERROR, EasybLaunchActivator.PLUGIN_ID,"Easyb runtime lib "+ILaunchConfigConstants.EASYB_RUNNER_LIB_NAME+" not found in plugin root dir"));
			}
				
			for(String fileName : libDir.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar");
				}
			})){
				classpathEntries.add(libDir.getAbsolutePath()+File.separator+fileName);
			}
		
			return classpathEntries.toArray(new String[classpathEntries.size()]);
			
		}catch(IOException ioex){
			Status status = new Status(IStatus.ERROR, EasybLaunchActivator.PLUGIN_ID, 0,
					"Unable to get Easyb runtime classapth due to IO Exception",ioex);
			throw new CoreException(status);
		}catch(URISyntaxException uriex){
			Status status = new Status(IStatus.ERROR, EasybLaunchActivator.PLUGIN_ID, 0,
					"Unable to get Easyb runtime classapth due to URI Exception",uriex);
			throw new CoreException(status);
		}
		
	}
}
