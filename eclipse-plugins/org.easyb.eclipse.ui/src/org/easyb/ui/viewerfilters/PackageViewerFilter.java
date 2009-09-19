package org.easyb.ui.viewerfilters;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class PackageViewerFilter  extends ViewerFilter{

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if(!(element instanceof IJavaElement)){
			return false;
		}
		
		switch(((IJavaElement)element).getElementType()){
			case IJavaElement.JAVA_PROJECT:
			{
				return true;
			}
			case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			{
				return false;
			}
			case IJavaElement.PACKAGE_FRAGMENT:
			{
				return true;
			}
			
			default:{
				return false;
			}
		}
	}

}
