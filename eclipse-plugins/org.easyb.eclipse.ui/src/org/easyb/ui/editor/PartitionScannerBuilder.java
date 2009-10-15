package org.easyb.ui.editor;

import static org.easyb.ui.editor.KeywordEnum.A;
import static org.easyb.ui.editor.KeywordEnum.AFTER;
import static org.easyb.ui.editor.KeywordEnum.AFTER_EACH;
import static org.easyb.ui.editor.KeywordEnum.AND;
import static org.easyb.ui.editor.KeywordEnum.BEFORE;
import static org.easyb.ui.editor.KeywordEnum.BEFORE_EACH;
import static org.easyb.ui.editor.KeywordEnum.BEHAVES;
import static org.easyb.ui.editor.KeywordEnum.BEHAVIOUR;
import static org.easyb.ui.editor.KeywordEnum.DESCRIPTION;
import static org.easyb.ui.editor.KeywordEnum.ENSURE;
import static org.easyb.ui.editor.KeywordEnum.ENSURE_FAILS;
import static org.easyb.ui.editor.KeywordEnum.ENSURE_THROWS;
import static org.easyb.ui.editor.KeywordEnum.GIVEN;
import static org.easyb.ui.editor.KeywordEnum.I;
import static org.easyb.ui.editor.KeywordEnum.IT;
import static org.easyb.ui.editor.KeywordEnum.NARRATIVE;
import static org.easyb.ui.editor.KeywordEnum.SCENARIO;
import static org.easyb.ui.editor.KeywordEnum.SHARED;
import static org.easyb.ui.editor.KeywordEnum.SO;
import static org.easyb.ui.editor.KeywordEnum.THAT;
import static org.easyb.ui.editor.KeywordEnum.THEN;
import static org.easyb.ui.editor.KeywordEnum.WANT;
import static org.easyb.ui.editor.KeywordEnum.WHEN;

import java.util.Arrays;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
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
	public final static String EASYB_BEHAVIOUR_SHARED_START = "__easyb_behaviour_shared";
	public final static String EASYB_BEHAVIOUR_BEFORE_START = "__easyb_behaviour_before_start";
	public final static String EASYB_BEHAVIOUR_BEFORE_EACH_START = "__easyb_behaviour_before_each_start";
	public final static String EASYB_BEHAVIOUR_AFTER_START ="__easyb_behaviour_after_start";
	public final static String EASYB_BEHAVIOUR_AFTER_EACH_START ="__easyb_behaviour_after_each_start";
	public final static String EASYB_BEHAVIOUR_ENSURE_THROWS_START = "__easyb_behaviour_ensure_throws_start";
	public final static String EASYB_BEHAVIOUR_ENSURE_START = "__easyb_behaviour_ensure_start";
	public final static String EASYB_BEHAVIOUR_ENSURE_FAILS_START = "__easyb_behaviour_ensure_fails_start";
	public final static String EASYB_BEHAVIOUR_IT_BEHAVES_AS_START = "__easyb_behaviour_it_behaves_as";
	public final static String EASYB_BEHAVIOUR_AS_A_START = "__easyb_behaviour_as_a";
	public final static String EASYB_BEHAVIOUR_I_WANT_START = "__easyb_behaviour_i_want";
	public final static String EASYB_BEHAVIOUR_SO_THAT_START = "__easyb_behaviour_so_that";
	
	public static final String[] EASYB_STATEMENT_PARTITION_TYPES = new String[]{
		EASYB_BEHAVIOUR_GIVEN_START,
		EASYB_BEHAVIOUR_THEN_START,
		EASYB_BEHAVIOUR_WHEN_START,
		EASYB_BEHAVIOUR_AND_START,
		EASYB_BEHAVIOUR_ENSURE_THROWS_START,
		EASYB_BEHAVIOUR_ENSURE_START,
		EASYB_BEHAVIOUR_ENSURE_FAILS_START,
		EASYB_BEHAVIOUR_IT_BEHAVES_AS_START,
		EASYB_BEHAVIOUR_AS_A_START,
		EASYB_BEHAVIOUR_I_WANT_START,
		EASYB_BEHAVIOUR_SO_THAT_START
	};
	
	public static final String[] EASYB_ROOT_PARTITION_TYPES = new String[]{
		EASYB_BEHAVIOUR_SCENARIO_START,
		EASYB_BEHAVIOUR_BEFORE_START,
		EASYB_BEHAVIOUR_BEFORE_EACH_START,
		EASYB_BEHAVIOUR_AFTER_START,
		EASYB_BEHAVIOUR_AFTER_EACH_START,
		EASYB_BEHAVIOUR_IT_START,
		EASYB_BEHAVIOUR_NARRATIVE_START,
		EASYB_BEHAVIOUR_DESCRIPTION_START,
		EASYB_BEHAVIOUR_SHARED_START
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
	
	private PartitionScannerBuilder(){
	}
	
	public static RuleBasedPartitionScanner createBehaviourPartitionScanner(){
		RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
		scanner.setPredicateRules(createPredicateRules());
		return scanner;
	}
	
	private static IPredicateRule[] createPredicateRules(){
		IPredicateRule[] rules = new IPredicateRule[20];

		//Add rule for scenario start
		rules[0] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_SCENARIO_START),SCENARIO.toString());
		
		//Add rule for given
		rules[1] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_GIVEN_START),GIVEN.toString());
		
		//Add rule for when
		rules[2] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_WHEN_START),WHEN.toString());
		
		//Add rule for then
		rules[3] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_THEN_START),THEN.toString());
		
		//Add rule for and
		rules[4] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_AND_START),AND.toString());
		
		//Add rule for narrative
		rules[5] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_BEFORE_START),NARRATIVE.toString());
		
		//Add rule for before 
		rules[6] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_BEFORE_START),BEFORE.toString());
		
		//Add rule for before_each 
		rules[7] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_BEFORE_EACH_START),BEFORE_EACH.toString());
		
		//Add rule for after 
		rules[8] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_AFTER_START),AFTER.toString());
		
		//Add rule for after_each 
		rules[9] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_AFTER_EACH_START),AFTER_EACH.toString());
		
		//Add rule for ensure throws
		rules[10] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_ENSURE_START),ENSURE_THROWS.toString());
		
		//Add rule for ensure 
		rules[11] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_ENSURE_START),ENSURE.toString());
		
		//Add rule for ensure fails 
		rules[12] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_ENSURE_FAILS_START),ENSURE_FAILS.toString());
		
		//Add rule for description
		rules[13] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_DESCRIPTION_START),DESCRIPTION.toString());
		
		//Add rule for shared
		rules[14] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_SHARED_START),SHARED.toString(),BEHAVIOUR.toString());
	
		//Add rule for "it behaves as" escape whitespace 
		rules[15] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_IT_BEHAVES_AS_START),IT.toString(),BEHAVES.toString(),"as");
		
		//Add rule for it
		rules[16] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_IT_START),IT.toString());
		
		//Add rule for narrative "as a"
		rules[17] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_AS_A_START),"as",A.toString());
		
		//Add rule for narrative "i want"
		rules[18] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_I_WANT_START),I.toString(),WANT.toString());
		
		//Add rule for narrative "i want"
		rules[19] = new BehaviourWordRule(new Token(EASYB_BEHAVIOUR_I_WANT_START),SO.toString(),THAT.toString());
		
		return rules;
	}
}
