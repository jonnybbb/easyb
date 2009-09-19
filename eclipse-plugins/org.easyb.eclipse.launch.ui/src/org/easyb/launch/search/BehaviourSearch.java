package org.easyb.launch.search;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Searches Project or Folder for any files with *.story
 * will also allow wild cards 
 * @author whiteda
 *
 */
//TODO Large projects may need to index stories
//and store index file with plugin obviously when a new story is 
//added index would have to be rebuilt.May not be necessary though.
public class BehaviourSearch {
	
	//TODO possibly run as an eclipse job
	public static List<String> findStoryPaths(IResource resource)throws CoreException{
		BehaviourPathVisitor vistor = new BehaviourPathVisitor();
		resource.accept(vistor, IResource.NONE);
		return vistor.getPaths();
	}
	
	//TODO possibly run as an eclipse job
	public static IFile[] findStoryFiles(IResource resource)throws CoreException{ 
		BehaviourFileVisitor vistor = new BehaviourFileVisitor();
		resource.accept(vistor, IResource.NONE);
		return vistor.getFiles().toArray(new IFile[vistor.getFiles().size()]);
	}
}
