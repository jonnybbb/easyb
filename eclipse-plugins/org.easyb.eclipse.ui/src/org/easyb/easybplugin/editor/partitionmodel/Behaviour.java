package org.easyb.easybplugin.editor.partitionmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyb.easybplugin.editor.PartitionScannerBuilder;
import org.easyb.easybplugin.utils.DocumentUtil;
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
	public static final String[] EASYB_STATEMENT_PARTITION_TYPES = new String[]{
		PartitionScannerBuilder.EASYB_STORY_GIVEN_START,
		PartitionScannerBuilder.EASYB_STORY_THEN_START,
		PartitionScannerBuilder.EASYB_STORY_WHEN_START
	};
	
	static{
		Arrays.sort(EASYB_STATEMENT_PARTITION_TYPES);
	}
	
	private List<IModelElement> specs 
	= new ArrayList<IModelElement>();
	
	/**
	 * Creates a partition model for the partitions in the document
	 * @param document
	 * @throws PartitionModelException
	 */
	public void createModel(IDocument document) throws PartitionModelException {
		try {
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
					specs.add(lastSpec);
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
		specs.clear();
	}
	
	private boolean isStatement(TypedPosition typePos){
		return Arrays.binarySearch(EASYB_STATEMENT_PARTITION_TYPES,typePos.getType())>=0;
	}
	
	private boolean isSpecification(TypedPosition typePos){
		if(PartitionScannerBuilder.EASYB_STORY_SCENARIO_START.equals(typePos.getType())){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean hasChildren(){
		return !specs.isEmpty();
	}
	
	
	@Override
	public IModelElement[] getElements() {
		return  specs.toArray(new IModelElement[specs.size()]);
	}

	@Override
	public IModelElement getParent() {
		return null;
	}
	
	public String getText(){
		return BEHAVIOUR_TEXT;
	}
	
}
