package evaluators;

import expressions.*;
import resources.*;
/**
 * IntegerEval.java - This class takes a string and attempts to evaluate it as a
 *     integer expression.  If it is not a valid integer expression, it returns
 *     null.
 * 
 * @author Justin
 */
public class IntegerEval implements Evaluator {
	// Set up static variables
	private static final String REGEX = "[0-9]*\\.?[0-9]+" + 
										"|\\+|-|\\*|/|%|\\^|\\(|\\)|~";
	/**
     * Creates a new IntegerEval instance, which compiles the regular expression
     * used to evaluate strings.
     */
	public IntegerEval() {
	}// End IntegerEval constructor

    /**
     * Evaluates the string expression and returns the resulting value, or
     * returns an InvalidExpressionException if the expression is invalid.
     * 
     * @param expression the expression to evaluate
     * @return the evaluated expression
     * @throws NoSuchVariableExistsException 
     * @throws InvalidExpressionException 
     * @throws VariableAssignmentException 
     */
	public Integer evaluate(String expression)
										throws NoSuchVariableExistsException,
											   InvalidExpressionException,
											   VariableAssignmentException {
		return (int)(new DoubleEval(REGEX).evaluate(expression).doubleValue());
	}// End evaluate method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(LitDoubleExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(PlusExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(MinusExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(TimesExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(DivExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(PowerExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(ParenExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(LitStringExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(ModExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(LitBooleanExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(NegExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(AndExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(OrExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(NotExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(EqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(NeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(LeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(GeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(LtExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Integer visit(GtExp exp) {
		return null;
	}// End visit method
}// End IntegerEval class