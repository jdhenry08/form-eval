package resources;

/**
 * Operand.java - Abstract base class for an operand.
 * 
 * @author Justin
 */
public abstract class Operand<E> extends Expression {
	public abstract Object accept(Evaluator v);
	public abstract String toString();

	public E value;
}