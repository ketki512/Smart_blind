package edu.rit.csci759.fuzzylogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleExpression;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethod;

public class MyTipperClass {
	FunctionBlock fb;
	FIS fis;
	RuleBlock rb;
	RuleConnectionMethod and;
	RuleConnectionMethod or;

//	public static void main(String[] args) throws Exception {
//		String filename = "FuzzyLogic/fuzzy.fcl";
//		FIS fis = FIS.load(filename, true);
//
//		if (fis == null) {
//			System.err.println("Can't load file: '" + filename + "'");
//			System.exit(1);
//		}
//
//		// Get default function block
//		FunctionBlock fb = fis.getFunctionBlock(null);
//
//		// Set inputs
//		fb.setVariable("temp", 8.5);
//		fb.setVariable("ambience", 7.5);
//
//		// Evaluate
//		fb.evaluate();
//
//		// Show output variable's chart
//		fb.getVariable("blinds").defuzzify();
//
//		// Print ruleSet
//		System.out.println(fb);
//		System.out.println("Blinds: " + fb.getVariable("blinds").getValue());
//
//	}

	public MyTipperClass() {
		String filename = "FuzzyLogic/tipper.fcl";
		fis = FIS.load(filename, true);
		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		fb = fis.getFunctionBlock(null);
		and = fb.getFuzzyRuleBlock(null).getRules().get(0).getAntecedents().getRuleConnectionMethod();
		or = fb.getFuzzyRuleBlock(null).getRules().get(1).getAntecedents().getRuleConnectionMethod();
		rb =fb.getFuzzyRuleBlock(null);
	}

	public void setTemp(double tempValue) {
		fb.setVariable("temp", tempValue);
	}

	public void setAmbience(double ambValue) {
		fb.setVariable("ambience", ambValue);
	}
	
	public void resetRulesBlock() {
		List<Rule> existingRules = rb.getRules();
		System.out.println("Ruleblock size before removing rules : "
				+ existingRules.size());
		for (int i = 0; i < existingRules.size(); i++) {
			rb.remove(existingRules.get(i));
		}
		System.out.println("Ruleblock size after removing rules : "
				+ rb.getRules().size());
	}
	
	public List<Rule> getRules() {
		return rb.getRules();
	}
	
	public void setRule(String[] rulesArray) {
		int blocksCount = (rulesArray.length / 3);
		Rule rule = new Rule("Rule", rb);
		List<RuleTerm> rulesList = new ArrayList<RuleTerm>();
		for (int i = 0; i < blocksCount; i++) {
			RuleTerm ruleTerm = new RuleTerm(fis.getVariable(rulesArray[i*3]), rulesArray[1 + (i*3)], false);
			rulesList.add(ruleTerm);
		}
		blocksCount--;
		RuleExpression exp1 = null;
		for (int i = 0; i < blocksCount; i++) {

			if (i == 0) {
				exp1 = new RuleExpression(
						rulesList.get(i),
						rulesList.get(i + 1),
						(rulesArray[2 + (i * 3)].compareToIgnoreCase("AND") == 0) ? and
								: or);
			} else if (i < blocksCount - 1) {
				exp1 = new RuleExpression(
						exp1,
						rulesList.get(i + 1),
						(rulesArray[2 + (i * 3)].compareToIgnoreCase("AND") == 0) ? and
								: or);
			}
		}
		rule.setAntecedents(exp1);
		rule.addConsequent(fis.getVariable("blinds"), rulesArray[rulesArray.length-1], false);
		rb.add(rule);
	}

	public double deFuzzify() {
		fb.evaluate();
		// Show output variable's chart
		System.out.println("Defuzzify returns : " + fb.getVariable("blinds").defuzzify());

		// Print ruleSet
		System.out.println(fb);
		System.out.println("Blinds: " + fb.getVariable("blinds"));
		return fb.getVariable("blinds").defuzzify();
	}
}
