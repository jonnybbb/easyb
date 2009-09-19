package org.easyb.launch.utils;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IDocumentPartitionerExtension2;

/**
 * Provides method to aid with converting to IDocumentExtension3
 * Document classes have been extended using the mixin interfaces 
 * IDocumentExtension2,3,4 and 5. Every where uses a a IDocument 
 * At the very least the IDocumentExtension3 should be used as the api 
 * changed with Eclipse 3.0 specifically with regards to partitioners
 * as partitoners can now be shared and need an id.
 * So IDocumentExtension3 methods should be used instead of IDocument methods
 * @author whiteda
 *
 */
public class DocumentUtil {
	
	public static void setDocumentPartitioner(IDocument document,
												String partionerId,
												IDocumentPartitioner partitioner){
		
		if(!(document instanceof IDocumentExtension3)){
			return;
		}
		
		((IDocumentExtension3)document).setDocumentPartitioner(
				partionerId,partitioner);
	}
	
	public static String[] getManagingPositionCategories(String partionerId,IDocument document){
		if(!(document instanceof IDocumentExtension3)){
			return null;
		}
		
		IDocumentPartitioner  partitioner = 
			((IDocumentExtension3)document).getDocumentPartitioner(partionerId);
		
		if(!(partitioner instanceof IDocumentPartitionerExtension2)){
			return null;
		}
		
		return ((IDocumentPartitionerExtension2)partitioner).getManagingPositionCategories();
	}
}
