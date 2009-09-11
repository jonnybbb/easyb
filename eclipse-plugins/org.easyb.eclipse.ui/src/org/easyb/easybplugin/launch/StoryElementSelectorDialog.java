package org.easyb.easybplugin.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.TwoPaneElementSelector;

/**
 * Displays story files in top pane with associated package/folders
 * in bottom pane.
 * @author whiteda
 *
 */
public class StoryElementSelectorDialog extends TwoPaneElementSelector {
	
	
	//TODO implement a easyb label provider which will display a 
	//easyb story icon
	private static class StoryContainerRenderer extends LabelProvider {

		private JavaElementLabelProvider fBaseLabelProvider;

		public StoryContainerRenderer() {
			fBaseLabelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_PARAMETERS | JavaElementLabelProvider.SHOW_POST_QUALIFIED | JavaElementLabelProvider.SHOW_ROOT);
		}

		public Image getImage(Object element) {
			return fBaseLabelProvider.getImage(((IFile)element).getParent());
		}

		public String getText(Object element) {
			return fBaseLabelProvider.getText(((IFile)element).getParent());
		}

		public void dispose() {
			fBaseLabelProvider.dispose();
		}

	}

	public StoryElementSelectorDialog(Shell parent,IFile[] files) {
		super(parent,new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT),
				new StoryContainerRenderer());
		setElements(files);
	}
	
}
