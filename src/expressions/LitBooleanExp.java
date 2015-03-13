package expressions;

import resources.*;
/**
 * LitBooleanExp.java - This class is used for representing a literal boolean
 *     operand (ie. true or false).
 * 
 * @author Justin
 */
public class LitBooleanExp extends Operand<Boolean> {
	/**
	 * Creates a new LitBooleanExp instance.
	 * 
	 * @param value the value of this operand.
	 */
	public LitBooleanExp(Boolean value) {
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
		return value.toString();
	}// End toString method
}// End LitBooleanExp class