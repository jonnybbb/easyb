package org.easyb.easybplugin.viewerfilters;

import org.easyb.easybplugin.EasybActivator;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filter to restrict what conatiners can be displayed in a SelectionDialog 
 * @author whiteda
 *
 */
//TODO Check contents of conatiners to ensure they conatin stories 
public class ContainerViewerFilter extends ViewerFilter{

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if(element instanceof IJavaProject){
			return true;
		}
		
		//TODO possible support for none source 
		//folders if needed 
		/*if(element instanceof IFolder)
		{
			IFolder folder = (IFolder)element;
			
			if(!folder.isAccessible() || folder.isHidden() || folder.isDerived() ){
				return false;
			}
			
			return true;
		}*/
		
		try{
			
			if(element instanceof IPackageFragmentRoot){
				IPackageFragmentRoot root = (IPackageFragmentRoot)element;
				
				if(root.isArchive() || !root.hasChildren())
				{
					return false;
				}
				
				return true;
			}
			
			if(element instanceof IPackageFragment && ((IPackageFragment)element).hasChildren()){
				return true;
			}
		}catch (JavaModelException e) {
			EasybActivator.Log("Unable to filter package for story", e);
			return false;
		}
		
		return false;
	}

}
