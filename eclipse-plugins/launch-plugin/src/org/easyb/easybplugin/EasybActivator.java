package org.easyb.easybplugin;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EasybActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "EasybPlugin";

	// The shared instance
	private static EasybActivator plugin;
	
	/**
	 * The constructor
	 */
	public EasybActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EasybActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static void Log(String message,Exception ex){
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0,message,ex));
	}
	
	public static void Log(String message){
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID,message));
	}
	
	public static void Log(IStatus status){
		getDefault().getLog().log(status);
	}
}
