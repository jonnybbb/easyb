package org.easyb.easybplugin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;

/**
 * Provides methods to check whether a resource is a easyb story file
 * based on the file extension type or filename.
 * 
 * Easyb stories should:-
 * Have a file extension of .story
 * Or have a full name of story.groovy
 * Or have a full name of specification.groovy
 * @author whiteda
 */
public class StoryFileMatcher {
	public static final String FILE_MATCH_REGEX = "^[_a-z0-9\\-]*\\.story$|^specification.groovy$|^story.groovy$"; 
	static Pattern regexPattern = Pattern.compile(FILE_MATCH_REGEX,Pattern.CASE_INSENSITIVE);

	/**
	 * Checks if a IResourceProxy is a story thats not in any output 
	 * folders. 
	 * First it checks if the resource is a file,its not hidden,
	 * its accessible, and its not derived (i.e in output folder)
	 * It then delegates to the other methods in this class
	 * to check if structure matches a easyb story file  
	 * @param proxy
	 * @return
	 */
	public static boolean isStoryFile(IResourceProxy proxy){
		if(proxy ==null || IResource.FILE != proxy.getType() || 
				proxy.isHidden() || !proxy.isAccessible() ||  proxy.isDerived()){
			return false;
		}
		
		return isExtensionOrNameMatch(proxy);
	}
	
	/**
	 * Matches a IResourceProxy simple file name against a regex 
	 * The regex used is ^[_a-z0-9\-]*\.story$|^specification.groovy$|^story.groovy$
	 * Use the IResoruce.getName() as this is a inexpensive call which doesn`t actually
	 * resolve the resource
	 * @param proxy
	 * @return
	 */
	public static boolean isExtensionOrNameMatch(IResourceProxy proxy){
		String name = proxy.getName();
		
		Matcher match = regexPattern.matcher(name);
		return match.find();
	}
}
