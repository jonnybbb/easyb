package org.easyb.easybplugin.launch;

import java.util.ArrayList;
import java.util.List;

import org.easyb.easybplugin.EasybActivator;
import org.easyb.easybplugin.IEasybLaunchConfigConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.pde.ui.launcher.AbstractLaunchShortcut;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/**
 * Shortcut used to launch a Easyb story from project explorer
 * Will only launch on fields ending with *.story as defined
 * in the plugin.xml 
 * @author whiteda
 *
 */
public class EasybLaunchShortcut extends AbstractLaunchShortcut{
	private IFile file=null;
	
	@Override
	protected String getLaunchConfigurationTypeName() {
		return EasybLaunchConfigurationDelegate.ID;
	}

	@Override
	protected void initializeConfiguration(ILaunchConfigurationWorkingCopy wc) {
		String filePath = file.getRawLocation().toOSString();
		List<String> stories = new ArrayList<String>();
		stories.add(filePath);
		
		wc.setAttribute(IEasybLaunchConfigConstants.LAUNCH_ATTR_STORIES_FULL_PATH,stories);
	}

	@Override
	protected boolean isGoodMatch(ILaunchConfiguration config){
		
		try {
			List<String> stories = config.getAttribute(
					IEasybLaunchConfigConstants.LAUNCH_ATTR_STORIES_FULL_PATH,
					new ArrayList<String>(0));

			if (stories.size() == 0 || stories.size()>1)  {
				return false;
			}
			
			String filePath = file.getRawLocation().toOSString();
			if(stories.get(0).equals(filePath)){
				return true;
			}

		} catch (CoreException ce) {
			EasybActivator.Log("Unable to get stories for launch", ce);
		}
		return false;
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput)input;
			file =fileInput.getFile();
			launch(mode);
		}
	}

	@Override
	public void launch(ISelection selection, String mode) {
		Object[] selectArr = null;
		if (selection instanceof IStructuredSelection) {
			selectArr = ((IStructuredSelection)selection).toArray();
			
			if(selectArr.length>0){
				file = (IFile)selectArr[0];
			}

			launch(mode);
        } 
	}

}
