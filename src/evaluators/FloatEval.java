package evaluators;

import expressions.*;
import resources.*;
/**
 * FloatEval.java - This class takes a string and attempts to evaluate it as a
 *     float expression.  If it is not a valid float expression, it returns
 *     an InvalidExpressionException.
 * 
 * @author William
 */
public class FloatEval implements Evaluator {
	/**
     * Creates a new FloatEval instance, which compiles the regular expression
     * used to evaluate strings.
     */
	public FloatEval() {
	}// End FloatEval constructor

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
	public Float evaluate(String expression)
										throws NoSuchVariableExistsException,
											   InvalidExpressionException,
											   VariableAssignmentException {
		return new Float(new DoubleEval().evaluate(expression));
	}// End evaluate method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(LitDoubleExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(PlusExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(MinusExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(TimesExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(DivExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(ModExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(PowerExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(ParenExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(LitStringExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(LitBooleanExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(NegExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(AndExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(OrExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(NotExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(EqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(NeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(LeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(GeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(LtExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Float visit(GtExp exp) {
		return null;
	}// End visit method
}// End FloatEval class