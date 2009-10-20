package org.easyb.ui.scanner;

import static org.easyb.ui.editor.KeywordEnum.SCENARIO;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easyb.ui.editor.BehaviourWordRule;
import org.easyb.ui.editor.PartitionScannerBuilder;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.action.VoidAction; 

public class BehaviourWordRuleTest extends TestCase{

	
	public void testMatch(){
		ScannerStub scanner = 
			new ScannerStub("scenario \"Test scenario\",{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertFalse(token.isUndefined());
		assertEquals(24,scanner.getOffSet());
	}
	
	//Shouldn`t iterate passed the first character
	public void testNoneMatchFirstCharacter(){
		ScannerStub scanner = 
			new ScannerStub("test \"Test scenario\",{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertTrue(token.isUndefined());
		assertEquals(0,scanner.getOffSet());
	}
	
	public void testNoneMatch(){
		ScannerStub scanner = 
			new ScannerStub("scen \"Test scenario\",{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertTrue(token.isUndefined());
		assertEquals(0,scanner.getOffSet());
	}
	
	
	public void testTripleQuotes(){
		ScannerStub scanner = 
			new ScannerStub("scenario \"\"\"Test scenario\"\"\",{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertFalse(token.isUndefined());
		assertEquals(28,scanner.getOffSet());
	}
	
	//TODO fix
	public void testTripleQuotesMissingQuote(){
		ScannerStub scanner = 
			new ScannerStub("scenario \"\"Test scenario\"\"\",{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertTrue(token.isUndefined());
		assertEquals(0,scanner.getOffSet());
	}
	
	public void testSingleQuotesMatch(){
		ScannerStub scanner = 
			new ScannerStub("scenario 'Test scenario',{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertFalse(token.isUndefined());
		assertEquals(24,scanner.getOffSet());
	}
	
	public void testSlashMatch(){
		ScannerStub scanner = 
			new ScannerStub("scenario /Test scenario/,{ ");
		
		BehaviourWordRule rule = 
			new BehaviourWordRule(new Token(PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		IToken token = null;
		do{
			token = rule.evaluate(scanner);
		}while(scanner.isEnd());
		
		assertFalse(token.isUndefined());
		assertEquals(24,scanner.getOffSet());
	}
	
}
