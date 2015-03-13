package resources;

/**
 * Expression.java - Abstract base class for expressions - this includes both 
 *     operators and operands.
 *
 * @author Justin, Bryan, William
 */
public abstract class Expression {
	public abstract Object accept(Evaluator v);
	public abstract String toString();

	public boolean leaf;
	public Expression left, right;
}// End Expression class