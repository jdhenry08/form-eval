package resources;

/**
 * Operator.java - Abstract base class for an operator.
 * 
 * @author Justin
 */
public abstract class Operator extends Expression {
	public abstract Object accept(Evaluator v);
	public abstract String toString();

	public int priority;
	public String op;
}