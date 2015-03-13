package expressions;

import resources.*;
/**
 * LitStringExp.java - This class is used for representing a literal string
 *     operand (ie. "foo" or "bar").
 * 
 * @author Bryan
 */
public class LitStringExp extends Operand<String> {
	/**
	 * Creates a new LitStringExp instance.
	 * 
	 * @param value the value of this operand.
	 */
	public LitStringExp(String value) {
		this.value = value;
	}// End constructor

	/**
	 * Implemented for visitor pattern
	 */
	public Object accept(Evaluator v) {
		return v.visit(this);
	}// End accept method

	/**
	 * Returns the string representation of this operand.
	 * 
	 * @return the string representation of this operand
	 */
	public String toString() {
		return "\"" + value.toString() + "\"";
	}// End toString method
}// End LitStringExp class