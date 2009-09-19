package org.easyb.launch.viewerfilters;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class SourceViewerFilter  extends ViewerFilter{

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if(element instanceof PackageFragmentRoot){ 
			PackageFragmentRoot source = 
				(PackageFragmentRoot)element;
			
			if(!source.isArchive()){
				return true;
			}
			
		}else if(element instanceof IJavaProject){
			return true;
		}

		return false;
	}

}
