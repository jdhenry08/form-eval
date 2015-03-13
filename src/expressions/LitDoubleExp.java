package expressions;

import resources.*;
/**
 * LitDoubleExp.java - This class is used for representing a literal double
 *     operand (ie. 2 or ~3.14).
 * 
 * @author Justin
 */
public class LitDoubleExp extends Operand<Double> {
	/**
	 * Creates a new LitDoubleExp instance.
	 * 
	 * @param value the value of this operand.
	 */
	public LitDoubleExp(Double value) {
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
}// End LitDoubleExp class