package org.easyb.launch.editor;

import java.util.Arrays;

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
	
	public final static String EASYB_BEHAVIOUR_SCENARIO_START= "__easyb_behaviour_scenario_start";
	public final static String EASYB_BEHAVIOUR_GIVEN_START = "__easyb_behaviour_given_start";
	public final static String EASYB_BEHAVIOUR_WHEN_START = "__easyb_behaviour_when_start";
	public final static String EASYB_BEHAVIOUR_THEN_START = "__easyb_behaviour_then_start";
	public final static String EASYB_BEHAVIOUR_AND_START = "__easyb_behaviour_and_start";
	public final static String EASYB_BEHAVIOUR_NARRATIVE_START = "__easyb_behaviour_narrative_start";
	public final static String EASYB_BEHAVIOUR_IT_START = "__easyb_behaviour_it_start";
	public final static String EASYB_BEHAVIOUR_DESCRIPTION_START = "__easyb_behaviour_description";
	public final static String EASYB_BEHAVIOUR_BEFORE_START = "__easyb_behaviour_before_start";
	public final static String EASYB_BEHAVIOUR_BEFORE_EACH_START = "__easyb_behaviour_before_each_start";
	public final static String EASYB_BEHAVIOUR_AFTER_START ="__easyb_behaviour_after_start";
	public final static String EASYB_BEHAVIOUR_AFTER_EACH_START ="__easyb_behaviour_after_each_start";
	public final static String EASYB_BEHAVIOUR_ENSURE_THROWS_START = "__easyb_behaviour_ensure_throws";
	
	public static final String[] EASYB_STATEMENT_PARTITION_TYPES = new String[]{
		PartitionScannerBuilder.EASYB_BEHAVIOUR_GIVEN_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_THEN_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_WHEN_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_AND_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_ENSURE_THROWS_START
	};
	
	public static final String[] EASYB_ROOT_PARTITION_TYPES = new String[]{
		PartitionScannerBuilder.EASYB_BEHAVIOUR_SCENARIO_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_BEFORE_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_BEFORE_EACH_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_AFTER_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_AFTER_EACH_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_IT_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_NARRATIVE_START,
		PartitionScannerBuilder.EASYB_BEHAVIOUR_DESCRIPTION_START
	};
	
	public static final String[] EASYB_ALL_PARTITION_TYPES = 
		new String[EASYB_STATEMENT_PARTITION_TYPES.length+EASYB_ROOT_PARTITION_TYPES.length];
	static{
		Arrays.sort(EASYB_ROOT_PARTITION_TYPES);
		Arrays.sort(EASYB_STATEMENT_PARTITION_TYPES);
		
		int lastVal =0;
		for(;lastVal<EASYB_ROOT_PARTITION_TYPES.length;++lastVal){
			EASYB_ALL_PARTITION_TYPES[lastVal] =EASYB_ROOT_PARTITION_TYPES[lastVal]; 
		}
		
		for(int i =0;lastVal<EASYB_ALL_PARTITION_TYPES.length;++lastVal,++i){
			EASYB_ALL_PARTITION_TYPES[lastVal] = EASYB_STATEMENT_PARTITION_TYPES[i];
		}
		
	}
	
	/*public static final String[] EASYB_BEHAVIOUR_PARTITION_TYPES = new String[]{
		EASYB_BEHAVIOUR_SCENARIO_START,
		EASYB_BEHAVIOUR_GIVEN_START,
		EASYB_BEHAVIOUR_WHEN_START,
		EASYB_BEHAVIOUR_THEN_START,
		EASYB_BEHAVIOUR_AND_START,
		EASYB_BEHAVIOUR_NARRATIVE_START,
		EASYB_BEHAVIOUR_IT_START,
		EASYB_BEHAVIOUR_DESCRIPTION_START,
		EASYB_BEHAVIOUR_BEFORE_START,
		EASYB_BEHAVIOUR_BEFORE_EACH_START,
		EASYB_BEHAVIOUR_AFTER_START,
		EASYB_BEHAVIOUR_AFTER_EACH_START,
		EASYB_BEHAVIOUR_ENSURE_THROWS_START
	};*/
	
	private PartitionScannerBuilder(){
	}
	
	public static RuleBasedPartitionScanner createBehaviourPartitionScanner(){
		RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
		scanner.setPredicateRules(createPredicateRules());
		return scanner;
	}
	
	private static IPredicateRule[] createPredicateRules(){
		IPredicateRule[] rules = new IPredicateRule[15];
		
		// Add rule for single line comments.
		rules[0] = new EndOfLineRule("//", Token.UNDEFINED);

		// Add rule for strings and character constants.
		rules[1] = new SingleLineRule("\"", "\"", Token.UNDEFINED, '\\'); 
		rules[2] = new SingleLineRule("'", "'", Token.UNDEFINED, '\\'); 
		
		//Add rule for scenario start
		rules[3] = new MultiLineRule("scenario","{",new Token(EASYB_BEHAVIOUR_SCENARIO_START));
		
		//Add rule for given
		rules[4] = new MultiLineRule("given","{",new Token(EASYB_BEHAVIOUR_GIVEN_START));
		
		//Add rule for when
		rules[5] = new MultiLineRule("when","{",new Token(EASYB_BEHAVIOUR_WHEN_START));
		
		//Add rule for then
		rules[6] = new 	MultiLineRule("then","{",new Token(EASYB_BEHAVIOUR_THEN_START));
		
		//Add rule for and
		rules[7] = new 	MultiLineRule("and","{",new Token(EASYB_BEHAVIOUR_AND_START));
		
		//Add rule for narrative
		rules[8] = new MultiLineRule("narrative","{",new Token(EASYB_BEHAVIOUR_NARRATIVE_START));
		
		//Add rule for it
		rules[9] = new MultiLineRule("it","{",new Token(EASYB_BEHAVIOUR_IT_START));
		
		//Add rule for before 
		rules[10] = new MultiLineRule("before","{",new Token(EASYB_BEHAVIOUR_BEFORE_START));
		
		//Add rule for before_each 
		rules[11] = new MultiLineRule("before_each","{",new Token(EASYB_BEHAVIOUR_BEFORE_EACH_START));
		
		//Add rule for after 
		rules[12] = new MultiLineRule("after","{",new Token(EASYB_BEHAVIOUR_AFTER_START));
		
		//Add rule for after_each 
		rules[13] = new MultiLineRule("after_each","{",new Token(EASYB_BEHAVIOUR_AFTER_EACH_START));
		
		//Add rule for endsure throws
		rules[14] = new MultiLineRule("ensureThrows","{",new Token(EASYB_BEHAVIOUR_ENSURE_THROWS_START));
		
		return rules;
	}
}
