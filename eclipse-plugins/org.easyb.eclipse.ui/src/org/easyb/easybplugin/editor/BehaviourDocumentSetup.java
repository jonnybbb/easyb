package org.easyb.easybplugin.editor;

import org.easyb.easybplugin.utils.DocumentUtil;
import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;

public class BehaviourDocumentSetup implements IDocumentSetupParticipant {
	
	@Override
	public void setup(IDocument document) {
		setupPartitioner(document);
	}
	
	public static void setupPartitioner(IDocument document) {
		IDocumentPartitioner partitioner = 
			new FastPartitioner(PartitionScannerBuilder.createBehaviourPartitionScanner(),
					PartitionScannerBuilder.EASYB_BEHAVIOUR_PARTITION_TYPES);
		
		DocumentUtil.setDocumentPartitioner(
				document,PartitionScannerBuilder.PARTITIONER_ID, partitioner);
		
		int length = document.getLength();
		
		partitioner.connect(document);
	}

}
