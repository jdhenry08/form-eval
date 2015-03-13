package expressions;

import resources.*;
/**
 * EqExp.java - Used for representing the boolean 'equals' operator.
 * 
 * @author Justin
 */
public class EqExp extends Operator {
	/**
	 * Creates a new EqExp instance.
	 * 
	 * @param op the operator associated with this operator
	 * @param priority the priority of this operator
	 */
	public EqExp(String op, int priority) {
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
}// End EqExp class