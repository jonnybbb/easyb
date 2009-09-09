package org.easyb.easybplugin.launch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.easyb.easybplugin.EasybActivator;
import org.easyb.easybplugin.IEasybLaunchConfigConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

/**
 * Used to launch stories using the BehaviourRunner in a separate JVM
 * @author whiteda
 *
 */
public class EasybLaunchConfigurationDelegate extends JavaLaunchDelegate{
	public static final String ID = "org.easyb.easybplugin.launch.EasybLaunchConfigurationDelegate"; 
	
	@Override
	public String getMainTypeName(ILaunchConfiguration configuration) throws CoreException {
		return IEasybLaunchConfigConstants.BEHAVIOR_RUNNER_CLASS;
	}
	
	/**
	 * Need to add the plugin jars to the classpath as well as any user jars
	 * @return
	 */
	@Override
	public String[] getClasspath(ILaunchConfiguration config)throws CoreException{
		//TODO possible issue with clash of jar file versions with 
		//easyb and users classpath.Handle this.
		return EasybClasspathBuilder.getRuntimeClasspath(super.getClasspath(config));
	}
	
	/**
	 * Add additional commands to the command line. Such as the stories to be executed
	 */
	@Override
	public String getProgramArguments(ILaunchConfiguration config)throws CoreException{
		
		StringBuilder args = new StringBuilder(super.getProgramArguments(config));
		
		List<String> storyPaths = 
			config.getAttribute(IEasybLaunchConfigConstants.LAUNCH_ATTR_STORIES_FULL_PATH,new ArrayList(0));
		
		for(String storyPath : storyPaths){
			args.append(" ").append(storyPath);
		}
		
		return args.toString();
	}

}
