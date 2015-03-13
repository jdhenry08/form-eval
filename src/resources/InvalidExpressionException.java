package resources;

/**
 * InvalidExpressionException.java - An exception that is thrown when an
 *     evaluator recieves an invalid expression.
 * 
 * @author Justin
 */
@SuppressWarnings("serial")
public class InvalidExpressionException extends Exception {
	/**
	 * Creates a new InvalidExpressionException instance.
	 * 
	 * @param exp the invalid expression
	 */
	public InvalidExpressionException(String exp) {
		super("Invalid expression: " + exp);
	}// End constructor
}// End InvalidExpressionException class