package org.easyb.launch.search;

import java.util.ArrayList;
import java.util.List;

import org.easyb.launch.utils.BehaviourFileMatcher;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * Matches any files in a resource which end with .story
 * @author whiteda
 *
 */
public class BehaviourPathVisitor implements IResourceProxyVisitor{
	private List<String> storyPaths = new ArrayList<String>();
	
	//TODO exclude output folders as otherwise stories 
	//could be included twice if part of a package
	@Override
	public boolean visit(IResourceProxy proxy) throws CoreException {
		
		if(BehaviourFileMatcher.isStoryFile(proxy)){
			IFile file = (IFile)proxy.requestResource();
			storyPaths.add(file.getRawLocation().toOSString());
		}
		
		return true;
	}
	
	public List<String> getPaths(){
		return storyPaths;
	}

}
