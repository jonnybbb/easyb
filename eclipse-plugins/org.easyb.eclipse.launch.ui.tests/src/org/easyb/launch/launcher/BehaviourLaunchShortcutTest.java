package org.easyb.launch.launcher;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.easyb.launch.EasybLaunchActivator;
import org.easyb.launch.ILaunchConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.debug.ui.actions.ILaunchable;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.javadocexport.JavadocExportMessages;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.jmock.Expectations;
import org.jmock.Mockery;

import junit.framework.TestCase;

public class BehaviourLaunchShortcutTest extends TestCase{

	private static final String LAUNCH_CONFIG_TYPE = "org.easyb.launch.launcher.BehaviourLaunchConfigurationDelegate";
	 private Mockery context = new Mockery();
	 private IEditorPart editor;
	 private IFileEditorInput fileInput;
	@Override
	public void setUp(){
		editor = context.mock(IEditorPart.class);
		fileInput = context.mock(IFileEditorInput.class);
	}
	
	private String getStoryText(){
		return "scenario \"a test\"{}";
	}
	private IFile getBehaviourFile(String fileName)throws Exception{
		//TODO move to a helper class
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("Test");
		
		if(!project.exists()){
			project.create(null);
		}
		
		if(!project.isOpen()){
			project.open(null);
		}
		
		IFile file = project.getFile(fileName);
		
		if(!file.exists()){
				
			InputStream stream = new ByteArrayInputStream(getStoryText().getBytes());
			file.create(stream,IResource.NONE,null);
		}
		
		return file;
	}
	
	private void setUpEditor(){
		context.checking(new Expectations() {{
			atLeast(1).of (editor).getEditorInput();
            will(returnValue((IEditorInput)fileInput));
		}});
	}
	
	private void setUpEditorInput(final IFile file){
		context.checking(new Expectations() {{
			atLeast(1).of (fileInput).getFile();
            will(returnValue(file));
		}});
	}
	
	private void setUpLaunchConfigurations(String launchConfigName,String storyPath)throws Exception{
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType lcType= launchManager.getLaunchConfigurationType(LAUNCH_CONFIG_TYPE);
	
		ILaunchConfigurationWorkingCopy wc= lcType.newInstance(null,launchConfigName);
		
		List<String> paths = new ArrayList<String>();
		paths.add(storyPath);
		
		wc.setAttribute(ILaunchConstants.LAUNCH_ATTR_STORIES_FULL_PATH,paths);
		ILaunchConfiguration config = wc.doSave();
		ILaunch newLaunch= new Launch(config,ILaunchManager.RUN_MODE, null);
		DebugPlugin.getDefault().getLaunchManager().addLaunch(newLaunch);
		
	}
	public void testRunLaunch()throws Exception{
		IFile file = getBehaviourFile("test.story");
		
		setUpEditor();
		setUpEditorInput(file);
		setUpLaunchConfigurations("testConfig",file.getRawLocation().toOSString());
		
		BehaviourLaunchShortcut shortcut = new BehaviourLaunchShortcut();
		shortcut.launch(editor,ILaunchManager.RUN_MODE);
		
		context.assertIsSatisfied();
	}
}
