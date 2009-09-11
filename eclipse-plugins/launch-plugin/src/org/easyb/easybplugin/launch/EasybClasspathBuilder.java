package org.easyb.easybplugin.launch;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easyb.easybplugin.EasybActivator;
import org.easyb.easybplugin.IEasybLaunchConfigConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class EasybClasspathBuilder {
	
	public static final String[] getRuntimeClasspath(String[] classpath)throws CoreException{
		
		try
		{
			List<String> classpathEntries = new ArrayList<String>();
			
			if(classpath!=null){
				Collections.addAll(classpathEntries,classpath);
			}
			
			File libDir = new File(FileLocator.toFileURL(
					EasybActivator.getDefault().getBundle().getEntry(
							"/"+IEasybLaunchConfigConstants.EASYB_RUNNER_LIB_NAME)).toURI());  
			   
			if(!libDir.exists()){
				throw new CoreException(
						new Status(IStatus.ERROR, EasybActivator.PLUGIN_ID,"Easyb runtime lib "+IEasybLaunchConfigConstants.EASYB_RUNNER_LIB_NAME+" not found in plugin root dir"));
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
			Status status = new Status(IStatus.ERROR, EasybActivator.PLUGIN_ID, 0,
					"Unable to get Easyb runtime classapth due to IO Exception",ioex);
			throw new CoreException(status);
		}catch(URISyntaxException uriex){
			Status status = new Status(IStatus.ERROR, EasybActivator.PLUGIN_ID, 0,
					"Unable to get Easyb runtime classapth due to URI Exception",uriex);
			throw new CoreException(status);
		}
		
	}
}
