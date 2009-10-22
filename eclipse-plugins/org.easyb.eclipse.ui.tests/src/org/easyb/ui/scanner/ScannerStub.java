package org.easyb.ui.scanner;

import org.eclipse.jface.text.rules.ICharacterScanner;


public class ScannerStub implements ICharacterScanner{

	private byte[] bytes; 
	private int offset;   
	
	public ScannerStub(String text){
		bytes = new byte[text.length()+1];
		bytes[text.length()] = ICharacterScanner.EOF;
		
		 byte[] oldBytes = text.getBytes();
		 for(int i = 0;i<oldBytes.length;++i){
			 bytes[i] = oldBytes[i];
		 }
	}
	
	@Override
	public int getColumn() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public char[][] getLegalLineDelimiters() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	@Override
	public int read(){
		if(offset<0){
			throw new RuntimeException("offset < 0 for read, "+offset);
		}
		
		if(offset<bytes.length){
			return bytes[offset++];
		}
		
		return ICharacterScanner.EOF;
	}
	
	@Override
	public void unread(){
		if(offset-1<0){
			throw new RuntimeException("unread will mean offset is < 0, "+offset);
		}
		
		--offset;
	}
	
	public boolean isEnd(){
		return offset==bytes.length || bytes[offset] == ICharacterScanner.EOF;
	}
	
	public int getOffSet(){
		return offset;
	}

}
