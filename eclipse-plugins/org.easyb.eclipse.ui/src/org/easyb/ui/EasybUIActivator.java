package org.easyb.ui;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EasybUIActivator extends AbstractUIPlugin {
	
	public static final String EASYB_ICON_IMAGE = "EASYB_ICON";
	// The plug-in ID
	public static final String PLUGIN_ID = "org.easyb.ui";

	// The shared instance
	private static EasybUIActivator plugin;

	//
	/**
	 * The constructor
	 */
	public EasybUIActivator() {
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
	
	@Override
	public void initializeImageRegistry(ImageRegistry reg){
		try{
			URL url = FileLocator.toFileURL(
					EasybUIActivator.getDefault().getBundle().getEntry(
							"/resources/images/icons/easyb.png"));
			Image easyb = ImageDescriptor.createFromURL(url).createImage();
			
			reg.put(EASYB_ICON_IMAGE,easyb);
			
		}catch(IOException ex){
			Log("Unable to intialize image registry", ex);
		}
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EasybUIActivator getDefault() {
		return plugin;
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
