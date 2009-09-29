package org.easyb.launch.launcher;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easyb.launch.ILaunchConstants;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.jmock.Expectations;
import org.jmock.Mockery;

public class BehaviourLaunchConfigurationDelegateTest extends TestCase{
	 private Mockery context = new Mockery();
	 private ILaunchConfiguration config;
	 
	@Override
	protected void setUp(){
		config = context.mock(ILaunchConfiguration.class);
	}
	
	private List<String> setUpMockForArgs()throws Exception{
		final List<String> stories = new ArrayList<String>();
		stories.add("C:\\test\\my1_story.story");
		stories.add("C:\\test2\\my2_specification.specification");
		
		context.checking(new Expectations() {{
			atLeast(1).of (config).getAttribute(with(equal(ILaunchConstants.LAUNCH_ATTR_STORIES_FULL_PATH)),with(any(ArrayList.class)));
            will(returnValue(stories));
		}});
		
		context.checking(new Expectations() {{
			atLeast(1).of (config).getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS,"");
            	will(returnValue(""));
		}});
		
		return stories;
	}
	
	public void testGetProgramArgumentsWindows()throws Exception
	{
		List<String> stories = setUpMockForArgs();
		
		BehaviourLaunchConfigurationDelegate launchDelegate = 
			new BehaviourLaunchConfigurationDelegate();
		
		launchDelegate.setPlatformOS(Platform.WS_WIN32);
		
		String args = launchDelegate.getProgramArguments(config);
		
		//Check that the "`s are added to the path names on windows
		String expected = " \""+stories.get(0)+"\" \""+stories.get(1)+"\"";
		
		assertEquals(expected,args);
		context.assertIsSatisfied();
	}
	
	public void testGetProgramArgumentsNoneWindows()throws Exception
	{
		List<String> stories = setUpMockForArgs();
		
		BehaviourLaunchConfigurationDelegate launchDelegate = 
			new BehaviourLaunchConfigurationDelegate();
		
		launchDelegate.setPlatformOS(Platform.WS_CARBON);
		
		String args = launchDelegate.getProgramArguments(config);
		
		String expected = " "+stories.get(0)+" "+stories.get(1);
		
		assertEquals(expected,args);
		context.assertIsSatisfied();
	}
}
