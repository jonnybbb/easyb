package org.easyb.ui.scanner;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.action.VoidAction; 

public class BehaviourWordRuleTest extends TestCase{
	private Mockery context = new Mockery();
	
	private ICharacterScanner scanner;
	private byte[] bytes; 
	private int offset;             
	@Override
	public void setUp(){
		context.mock(ICharacterScanner.class);
	}
	
	private void configureScanner(String text){
		 bytes = new byte[text.length()+1];
		 bytes[text.length()] = ICharacterScanner.EOF;
		
		 byte[] oldBytes = text.getBytes();
		 for(int i = 0;i<oldBytes.length;++i){
			 bytes[i] = oldBytes[i];
		 }
		
		 context.checking(new Expectations() {{
				atLeast(1).of (scanner).read();
	            will(returnValue(read()));
			}});
		 
		 context.checking(new Expectations() {{
				atLeast(1).of (scanner).unread();
				will(returnValue(unread()));//Might break here as can`t return a value
			}});
	}
	
	private byte read(){
		if(offset<0){
			throw new RuntimeException("offset < 0 for read, "+offset);
		}
		
		if(offset<bytes.length){
			return bytes[offset++];
		}

		return ICharacterScanner.EOF;
	}
	
	private int unread(){
		if(offset-1<0){
			throw new RuntimeException("unread will mean offset is < 0, "+offset);
		}
		
		--offset;
		return offset;
	}
}
