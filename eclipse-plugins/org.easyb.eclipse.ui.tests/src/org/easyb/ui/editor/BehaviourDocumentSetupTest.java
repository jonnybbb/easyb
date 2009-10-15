package org.easyb.ui.editor;

import junit.framework.TestCase;

import org.easyb.eclipse.test.tools.EditorTool;
import org.easyb.eclipse.test.tools.ProjectTool;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.ui.IEditorPart;

public class BehaviourDocumentSetupTest extends TestCase{

	public static String getStoryText(){
		return "scenario \"a test\",{given \"a given\",{}}";
	}


	public void testDocumentSetUp()throws Exception{

		IFile file =
			ProjectTool.setupJavaProjectAndBehaviourFile("test.story","test",getStoryText());

		IEditorPart editor = EditorTool.openEditor(file);
		IDocument  doc = EditorTool.getDocument(editor);
		
		assertTrue((doc instanceof IDocumentExtension3));

		IDocumentExtension3 docExt3 = (IDocumentExtension3)doc;
		
		//The document should of had a partitioner setup for 
		//both easyb and groovy
		IDocumentPartitioner  partitioner = 
			docExt3.getDocumentPartitioner(PartitionScannerBuilder.PARTITIONER_ID);

		assertNotNull(partitioner);

		partitioner =
			docExt3.getDocumentPartitioner(IJavaPartitions.JAVA_PARTITIONING);

		//TODO test getting all partitions
	}
}
