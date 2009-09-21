package org.easyb.ui.editor.partitionmodel;

public class KeywordPosition {
	private int offset;
	private int length;
	
	public KeywordPosition(int offset,int length){
		this.offset = offset;
		this.length = length;
	}
	
	public int getOffset(){
		return offset;
	}
	
	public int getLength(){
		return length;
	}
}
