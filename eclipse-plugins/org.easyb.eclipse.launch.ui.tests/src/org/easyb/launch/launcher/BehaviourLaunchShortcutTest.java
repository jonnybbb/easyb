package org.easyb.launch.launcher;

import junit.framework.TestCase;

import org.easyb.eclipse.test.tools.LaunchConfigurationBuilder;
import org.easyb.eclipse.test.tools.ProjectTool;
import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.jmock.Expectations;
import org.jmock.Mockery;

public class BehaviourLaunchShortcutTest extends TestCase{

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
	
	public void testRunLaunch()throws Exception{
		IFile file = 
			ProjectTool.setupProjectAndBehaviourFile("test.story","test",getStoryText());
		
		setUpEditor();
		setUpEditorInput(file);
		LaunchConfigurationBuilder.buildConfiguration("testConfig",file.getRawLocation().toOSString());
		
		BehaviourLaunchShortcut shortcut = new BehaviourLaunchShortcut();
		shortcut.launch(editor,ILaunchManager.RUN_MODE);
		
		context.assertIsSatisfied();
	}
}
