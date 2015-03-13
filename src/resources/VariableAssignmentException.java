package resources;

/**
 * VariableAssignmentException.java - An exception that is thrown when an
 *     invalid variable assignment expression is used.
 * 
 * @author William
 */
@SuppressWarnings("serial")
public class VariableAssignmentException extends Exception {
	/**
	 * Creates a new VariableAssignmentException instance.
	 * 
	 * @param exp the invalid variable assignment
	 */
	public VariableAssignmentException(String exp){
		super("Invalid assignment statement: " + exp);
	}// End constructor
}// End VariableAssignmentException class