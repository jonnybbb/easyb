package org.easyb.easybplugin.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;

/**
 * Constructs the Launch tabs needed to configure 
 * a Easyb launch
 * @author whiteda
 *
 */
public class EasybLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup{

	@Override
	public void createTabs(ILaunchConfigurationDialog arg0, String arg1) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new EasybMainTab(),
				new JavaArgumentsTab(),
				new JavaClasspathTab(), 
				new JavaJRETab(),
				new EnvironmentTab(),
				new CommonTab()
		};
		
		setTabs(tabs);
	}

}
