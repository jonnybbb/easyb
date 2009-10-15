package org.easyb.ui.editor;

import org.codehaus.groovy.eclipse.editor.GroovyPartitionScanner;
import org.easyb.ui.utils.DocumentUtil;
import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;

public class BehaviourDocumentSetup implements IDocumentSetupParticipant {
	
	@Override
	public void setup(IDocument document) {
		setupPartitioner(document);
	}
	
	public static void setupPartitioner(IDocument document) {
		//Add the easyb partitoner  
		IDocumentPartitioner easybPartitioner = 
			new FastPartitioner(PartitionScannerBuilder.createBehaviourPartitionScanner(),
					PartitionScannerBuilder.EASYB_ALL_PARTITION_TYPES);
		
		DocumentUtil.setDocumentPartitioner(
				document,PartitionScannerBuilder.PARTITIONER_ID,easybPartitioner);
		
		easybPartitioner.connect(document);
		
		//Add the Groovy partitioner
		IDocumentPartitioner groovyPartitioner = 
			new FastPartitioner(new GroovyPartitionScanner(), GroovyPartitionScanner.LEGAL_CONTENT_TYPES);
		 
		DocumentUtil.setDocumentPartitioner(
				document,IJavaPartitions.JAVA_PARTITIONING,groovyPartitioner);
		
		
		groovyPartitioner.connect(document);
	}

}
