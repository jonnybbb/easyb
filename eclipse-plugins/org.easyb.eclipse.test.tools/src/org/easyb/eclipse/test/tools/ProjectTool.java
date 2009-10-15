package org.easyb.eclipse.test.tools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

/**
 * Tools to help with setting up of project 
 * @author whiteda
 *
 */
public class ProjectTool {
	private ProjectTool(){
		
	}
	
	public static String getStoryText(){
		return "scenario \"a test\"{}";
	}
	
	public static IPackageFragment createPackage(IJavaProject proj,String pkgName)throws Exception{
		IPackageFragmentRoot[] pckgs = proj.getPackageFragmentRoots();
		return pckgs[0].createPackageFragment(pkgName,true,new NullProgressMonitor());
	}
	
	public static IFile createBehaviourInPackage(IPackageFragment pkgFrag,String fileName,String text) throws Exception{
		IFolder folder = (IFolder)pkgFrag.getResource();
		IFile file = folder.getFile(fileName);
		
		writeToFile(file,text);
		return file;
		
	}
	
	//see http://dev.eclipse.org/newslists/news.eclipse.platform/msg75802.html
	public static IJavaProject setupJavaProject(String projectName)throws Exception{
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		IProject project = root.getProject(projectName);
		
		if(project.exists()){
			// Clean up any old project information.
			project.delete(true, true,null);

		}
		
		final IJavaProject javaProject = JavaCore.create(project);
		final IProjectDescription projectDescription =
				workspace.newProjectDescription(projectName);
		projectDescription.setLocation(null);
		project.create(projectDescription,null);
		
		final List<IClasspathEntry> classpathEntries =
				new ArrayList<IClasspathEntry>();
		/*if (referencedProjects.size() != 0) {
			projectDescription.setReferencedProjects(referencedProjects
					.toArray(new IProject[referencedProjects.size()]));
			for (final IProject referencedProject : referencedProjects) {
				final IClasspathEntry referencedProjectClasspathEntry =
						JavaCore.newProjectEntry(referencedProject
								.getFullPath());
				classpathEntries.add(referencedProjectClasspathEntry);
			}
		}*/

		projectDescription.setNatureIds(new String[] { JavaCore.NATURE_ID,
				"org.eclipse.pde.PluginNature" });

		final ICommand java = projectDescription.newCommand();
		java.setBuilderName(JavaCore.BUILDER_ID);

		final ICommand manifest = projectDescription.newCommand();
		manifest.setBuilderName("org.eclipse.pde.ManifestBuilder");

		final ICommand schema = projectDescription.newCommand();
		schema.setBuilderName("org.eclipse.pde.SchemaBuilder");

		projectDescription.setBuildSpec(new ICommand[] { java, manifest,
				schema });

		project.open(null);
		project.setDescription(projectDescription,null);
		
		List<String> srcFolders = new ArrayList<String>(1);
		srcFolders.add("src");
		
		createSrcFolders(project,srcFolders, classpathEntries);

		classpathEntries.add(JavaCore.newContainerEntry(new Path(
				"org.eclipse.jdt.launching.JRE_CONTAINER")));
		classpathEntries.add(JavaCore.newContainerEntry(new Path(
				"org.eclipse.pde.core.requiredPlugins")));
	
		//Takes ages
		//javaProject.setRawClasspath(classpathEntries
		//		.toArray(new IClasspathEntry[classpathEntries.size()]),null);

		javaProject.setOutputLocation(
				new Path("/" + projectName + "/bin"),null);

		workspace.build(IncrementalProjectBuilder.FULL_BUILD, null);
		return javaProject;
	}
	
	public static void createSrcFolders(IProject project,List<String> srcFolders,
											List<IClasspathEntry > classpathEntries)throws Exception{
		Collections.reverse(srcFolders);
		for (final String src : srcFolders) {
			final IFolder srcContainer = project.getFolder(src);
			if (!srcContainer.exists()) {
				srcContainer.create(false, true,null);
			}
			final IClasspathEntry srcClasspathEntry =
					JavaCore.newSourceEntry(srcContainer.getFullPath());
			classpathEntries.add(srcClasspathEntry);
		}
	}
	
	public static IFile createBehaviourFile(IJavaProject project,String fileName,String text)throws Exception{
		IFile file = project.getProject().getFile(fileName);
		writeToFile(file,text);
		return file;
	}
	
	public static IFile writeToFile(IFile file,String text)throws Exception{
		if(!file.exists()){
			
			InputStream stream = new ByteArrayInputStream(text.getBytes());
			file.create(stream,IResource.NONE,null);
		}	
		
		return file;
	}
	public static  IFile setupJavaProjectAndBehaviourFile(String fileName,String project,String text)throws Exception{
		IJavaProject rootProj = setupJavaProject(project);
		return createBehaviourFile(rootProj,fileName,text);
	}
}
