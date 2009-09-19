package org.easyb.launch.utils;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

/**
 * Useful methods for setting UI Controls
 * @author whiteda
 *
 */
public class WidgetUtil {
	
	/**
	 * Returns a width hint for a button control.
	 * Based on org.eclipse.jdt.internal.junit.util
	 * @param button	the button for which to set the dimension hint
	 * @return the width hint
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		FontMetrics fontMetrics = getFontMetrics(button.getFont());
		
		if(fontMetrics==null){
			return 0;
		}
		
		int widthHint = 
			Dialog.convertHorizontalDLUsToPixels(fontMetrics,IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}
	
	public static FontMetrics getFontMetrics(Font font){
		GC gc = new GC(font.getDevice());
		gc.setFont(font);
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
		
		return fontMetrics;
	}

	/**
	 * Sets width and height hint for the button control.
	 * <b>Note:</b> This is a NOP if the button's layout data is not
	 * an instance of <code>GridData</code>.
	 * Based on org.eclipse.jdt.internal.junit.util
	 * @param button	the button for which to set the dimension hint
	 */
	public static void setButtonDimensionHint(Button button){
		Assert.isNotNull(button);
		Object gd= button.getLayoutData();
		if (gd instanceof GridData) {
			((GridData)gd).widthHint= getButtonWidthHint(button);
			((GridData)gd).horizontalAlignment = GridData.FILL;
		}
	}
}
