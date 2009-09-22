package org.easyb.launch;

/**
 * Constants to be used by plugin
 * @author whiteda
 *
 */
public interface ILaunchConstants {
	
	//Launch configuration attributes
	public static final String LAUNCH_ATTR_CONTAINER_HANDLE= "EASYB_CONTAINER_HANDLE";
	public static final String LAUNCH_ATTR_STORY_PATH = "EASYB_FILE_PATH";
	public static final String LAUNCH_ATTR_STORIES_FULL_PATH = "EASYB_STORIES_FULL_PATH";
	public static final String LAUNCH_ATTR_IS_SINGLE_STORY  =  "EASYB_IS_SINGLE_STORY";
	
	//Class to run behaviours
	public static final String BEHAVIOR_RUNNER_CLASS = "org.easyb.BehaviorRunner";

	//Plugin internal lib with jar files
	public static final String EASYB_RUNNER_LIB_NAME = "resources/easyblib";

}
