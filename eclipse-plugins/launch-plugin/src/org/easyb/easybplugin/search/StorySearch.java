package org.easyb.easybplugin.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * Searches Project or Folder for any files with *.story
 * will also allow wild cards 
 * @author whiteda
 *
 */
public class StorySearch {
	
	//TODO possibly run as an eclipse job
	public static List<String> findStoryPaths(IResource resource)throws CoreException{
		StoryPathVisitor vistor = new StoryPathVisitor();
		resource.accept(vistor, IResource.NONE);
		return vistor.getPaths();
	}
	
	//TODO possibly run as an eclipse job
	public static List<IFile> findStoryFiles(IResource resource)throws CoreException{ 
		StoryFileVisitor vistor = new StoryFileVisitor();
		resource.accept(vistor, IResource.NONE);
		return vistor.getFiles();
	}
}
