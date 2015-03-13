package resources;

/**
 * NoSuchVariableExistsException.java - An exception that is thrown when an
 *     unset variable is used in an expression.
 * 
 * @author William
 */
@SuppressWarnings("serial")
public class NoSuchVariableExistsException extends Exception {
	/**
	 * Creates a new NoSuchVariableExistsException instance.
	 * 
	 * @param exp the nonexistant variable
	 */
	public NoSuchVariableExistsException(String exp){
		super("No such variable: " + exp);
	}// End constructor
}// End NoSuchVariableExistsException class