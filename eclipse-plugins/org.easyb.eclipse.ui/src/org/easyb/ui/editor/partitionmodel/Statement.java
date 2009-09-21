package org.easyb.ui.editor.partitionmodel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TypedPosition;

/**
 * Represents a Specification (Story/specification) 
 * or part of a specification in the docuement 
 * @author whiteda
 *
 */
public class Statement implements IModelElement{
	//Removes everything after the last , i.e ,{
	private static final String CLEAN_TEXT_REGEX = "[,$]|[\\{$]";
	private int offset;
	private String type;
	private String text;
	private IModelElement parent;
	private int keywordOffset;
	private int keywordLength;
	
	private List<IModelElement> statements = 
		new ArrayList<IModelElement>();
	
	public Statement(){
	}
	
	public void update(TypedPosition typePos,IDocument document,IModelElement parent)throws PartitionModelException{
		try {
			offset = typePos.offset;
			type = typePos.getType();
			setText(document.get(offset,typePos.length));
			this.parent = parent;
		} catch (BadLocationException e) {
			throw new PartitionModelException("Unable to update partiton model for "+type,e);
		}
	}
	
	//Removes ,{ from the line of text
	private void setText(String dirtyText){
		text = dirtyText.replaceAll(CLEAN_TEXT_REGEX, "");
	}
	
	public int getOffSet(){
		return offset;
	}
	
	public String getType(){
		return type;
	}
	
	public String getText(){
		return text == null?"":text;
	}
	
	public int getHighlightOffset(){
		return offset;
	}
	
	public int getHighlightLength(){
		return getText().length();
	}
	public void addStatement(TypedPosition typePos,IDocument document)throws PartitionModelException{
		Statement state = new Statement();
		state.update(typePos, document,this);
		statements.add(state);
	}
	
	@Override
	public boolean hasChildren(){
		return !statements.isEmpty();
	}

	@Override
	public IModelElement[] getElements() {
		return  statements.toArray(new IModelElement[statements.size()]);
	}

	@Override
	public IModelElement getParent() {
		return parent;
	}
	
	@Override
	public KeywordPosition getFirstKeywordPosition(){
		keywordLength = 0;
		keywordOffset = offset;
		boolean bFoundChar = false;
		for(char ch : getText().toCharArray()){
		
			if(Character.isWhitespace(ch)){
				if(bFoundChar)
				{
					break;
				}else{
					++keywordOffset;
					continue;
				}
			}else{
				bFoundChar=true;
				++keywordLength;
			}
		}
		
		return new KeywordPosition(keywordOffset,keywordLength);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("type=").append(type)
		.append("offset=").append(offset)
		.append("text=").append(text);
		
		return builder.toString();
	}
}
