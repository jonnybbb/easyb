package org.easyb.eclipse.test.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;

/**
 * Enables a configuration to be built and also 
 * saves a configuration 
 * @author whiteda
 *
 */
public class LaunchConfigurationBuilder {
	public static final String LAUNCH_CONFIG_TYPE = "org.easyb.launch.launcher.BehaviourLaunchConfigurationDelegate";
	
	private LaunchConfigurationBuilder(){
		
	}
	
	public static ILaunchConfiguration buildConfiguration(String launchConfigName, String behaviourPath)throws Exception{
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType lcType= launchManager.getLaunchConfigurationType(LAUNCH_CONFIG_TYPE);
		ILaunchConfigurationWorkingCopy wc= lcType.newInstance(null,launchConfigName);
		List<String> paths = new ArrayList<String>();
		paths.add(behaviourPath);
		
		wc.setAttribute("EASYB_STORIES_FULL_PATH",paths);
		ILaunchConfiguration config = wc.doSave();
		ILaunch newLaunch= new Launch(config,ILaunchManager.RUN_MODE, null);
		DebugPlugin.getDefault().getLaunchManager().addLaunch(newLaunch);
		return config;
	}
}
