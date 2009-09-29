package org.easyb.eclipse.test.tools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * Tools to help with setting up of project 
 * @author whiteda
 *
 */
public class ProjectTool {
	private ProjectTool(){
		
	}
	
	public static IProject setupProject(String projName)throws Exception{
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject rootProj = root.getProject(projName);
		
		if(!rootProj.exists()){
			rootProj.create(null);
		}
		
		if(!rootProj.isOpen()){
			rootProj.open(null);
		}
		
		return rootProj;
	}
	
	public static IFile createBehaviourFile(IProject project,String fileName,String text)throws Exception{
		IFile file = project.getFile(fileName);
		
		if(!file.exists()){
				
			InputStream stream = new ByteArrayInputStream(text.getBytes());
			file.create(stream,IResource.NONE,null);
		}
		
		return file;
	}
	
	public static  IFile setupProjectAndBehaviourFile(String fileName,String project,String text)throws Exception{
		//TODO move to a helper class
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject rootProj = setupProject(project);
		
		return createBehaviourFile(rootProj,fileName,text);
	}
}
