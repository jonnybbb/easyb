package org.easyb.easybplugin.editor.partitionmodel;

import org.easyb.easybplugin.EasybActivator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class PartitionModelException extends CoreException {

	private static final long serialVersionUID = 3724487365010485400L;

	public PartitionModelException(IStatus status) {
		super(status);
	}
	
	public PartitionModelException(String message,Exception ex) {
		super(new Status(IStatus.ERROR,
				EasybActivator.PLUGIN_ID,message, ex));
	}

}
