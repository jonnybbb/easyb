package org.easyb.easybplugin.editor;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

/**
 * Constructs a partition scanner to be used to 
 * partition easyb behaviours.
 * @author whiteda
 *
 */
public class PartitionScannerBuilder {
	public static final String PARTITIONER_ID = "org.easyb.behaviour.partitioner";
	
	public final static String EASYB_STORY_SCENARIO_START= "__easyb_story_scenario_start";
	public final static String EASYB_STORY_GIVEN_START = "__easyb_story_given_start";
	public final static String EASYB_STORY_WHEN_START = "__easyb_story_when_start";
	public final static String EASYB_STORY_THEN_START = "__easyb_story_then_start";
	public final static String EASYB_END_TAG = "__easyb_end_tag";
	
	public static final String[] EASYB_BEHAVIOUR_PARTITION_TYPES = new String[]{
		//IDocument.DEFAULT_CONTENT_TYPE,
		EASYB_STORY_SCENARIO_START,
		EASYB_STORY_GIVEN_START,
		EASYB_STORY_WHEN_START,
		EASYB_STORY_THEN_START,
		EASYB_END_TAG
	};
	
	private PartitionScannerBuilder(){
	}
	
	public static RuleBasedPartitionScanner createBehaviourPartitionScanner(){
		RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
		scanner.setPredicateRules(createPredicateRules());
		return scanner;
	}
	
	private static IPredicateRule[] createPredicateRules(){
		IPredicateRule[] rules = new IPredicateRule[8];
		
		// Add rule for single line comments.
		rules[0] = new EndOfLineRule("//", Token.UNDEFINED);

		// Add rule for strings and character constants.
		rules[1] = new SingleLineRule("\"", "\"", Token.UNDEFINED, '\\'); 
		rules[2] = new SingleLineRule("'", "'", Token.UNDEFINED, '\\'); 
		
		//Add rule for end of scenario,given,wehn,then,etc
		rules[3] = new SingleLineRule("{","{",new Token(EASYB_END_TAG ));
		
		//Add rule for scenario start
		rules[4] = new MultiLineRule("scenario","{",new Token(EASYB_STORY_SCENARIO_START));
		
		//Add rule for given
		rules[5] = new MultiLineRule("given","{",new Token(EASYB_STORY_GIVEN_START));
		
		//Add rule for when
		rules[6] = new MultiLineRule("when","{",new Token(EASYB_STORY_WHEN_START));
		
		//Add rule for then
		rules[7] = new 	MultiLineRule("then","{",new Token(EASYB_STORY_THEN_START));
		
		//TODO Add other story/specification rules 
		return rules;
	}
}
