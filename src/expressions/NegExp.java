package expressions;

import resources.*;
/**
 * NegExp.java - Used for representing the double 'negative' operator.
 * 
 * @author Justin
 */
public class NegExp extends Operator {
	/**
	 * Creates a new NegExp instance.
	 * 
	 * @param op the operator associated with this operator
	 * @param priority the priority of this operator
	 */
	public NegExp(String op, int priority) {
		this.priority = priority;
		this.op = op;
	}// End constructor

	/**
	 * Implemented for visitor pattern
	 */
	public Object accept(Evaluator v) {
		return v.visit(this);
	}// End accept method

	/**
	 * Returns a string representation of this operator
	 * 
	 * @return a string representation of this operator
	 */
	public String toString() {
		return op;
	}// End toString method
}// End NegExp class