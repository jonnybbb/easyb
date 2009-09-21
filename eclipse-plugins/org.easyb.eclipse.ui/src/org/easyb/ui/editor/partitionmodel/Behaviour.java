package org.easyb.ui.editor.partitionmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easyb.ui.editor.PartitionScannerBuilder;
import org.easyb.ui.utils.DocumentUtil;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TypedPosition;

/**
 * Represents the root class of a partition model of a document.
 * Contains a map of Specifications (which represent Stories or specifications)
 * which are mapped to position offsets in the document
 * @author whiteda
 *
 */
public class Behaviour implements IModelElement{
	public static final String BEHAVIOUR_TEXT = "Behaviour";
	
	private List<IModelElement> roots 
	= new ArrayList<IModelElement>();
	
	/**
	 * Creates a partition model for the partitions in the document
	 * @param document
	 * @throws PartitionModelException
	 */
	public void createModel(IDocument document) throws PartitionModelException {
		try {
			if(document==null){
				return;
			}
			
			String[] categories = DocumentUtil.getManagingPositionCategories(
					PartitionScannerBuilder.PARTITIONER_ID, document);
			
			Position[] positions = document.getPositions(categories[0]);
			
			Statement lastSpec =null;
			for(Position pos : positions){
				if(!(pos instanceof TypedPosition)){
					continue;
				}
				
				TypedPosition typePos = (TypedPosition)pos;
				
				if(isSpecification(typePos)){
					lastSpec = new Statement();
					lastSpec.update(typePos, document,this);
					roots.add(lastSpec);
				}
				
				//IF spec isn`t set then continue as 
				//not valid syntax
				if(lastSpec==null){
					continue;
				}
				
				//Add this statement to the last specification
				if(isStatement(typePos)){
					lastSpec.addStatement(typePos, document);
				}
			}
			
		} catch (BadPositionCategoryException e) {
			throw new PartitionModelException("Unable to create partition model", e);
		}
	}
	
	public void clear(){
		roots.clear();
	}
	
	private boolean isStatement(TypedPosition typePos){
		return Arrays.binarySearch(PartitionScannerBuilder.EASYB_STATEMENT_PARTITION_TYPES,typePos.getType())>=0;
	}
	
	private boolean isSpecification(TypedPosition typePos){
		return Arrays.binarySearch(PartitionScannerBuilder.EASYB_ROOT_PARTITION_TYPES,typePos.getType())>=0;
	}

	@Override
	public boolean hasChildren(){
		return !roots.isEmpty();
	}
	
	
	@Override
	public IModelElement[] getElements() {
		return  roots.toArray(new IModelElement[roots.size()]);
	}

	@Override
	public IModelElement getParent() {
		return null;
	}
	
	public String getText(){
		return BEHAVIOUR_TEXT;
	}
	
	public KeywordPosition getFirstKeywordPosition(){
		return new KeywordPosition(0,0);
	}
}
