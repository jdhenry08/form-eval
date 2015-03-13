package resources;

import java.util.*;
import java.util.regex.*;

import evaluators.*;
/**
 * Tools.java - This class contains the methods for finding variables in an
 * expression and for determining what type of evaluator is needed for a given 
 * expression.  These methods are HARD CODED and do not support any evaluators
 * or variable cases that were not in existence for the indicated version of the
 * software.  For these functions to work with new evaluators they will need to
 * be completely rewritten.
 *   
 * @author William, Justin
 */
public final class Tools {
	private static HashMap<String, String> variableTable = 
												new HashMap<String, String>();
	/**
	 * Checks to see if a variable is set in the map.
	 * 
	 * @param var the variable to check for
	 * @return true if the variable exists, false otherwise
	 */
	public static boolean variableExists(String var) {
		return variableTable.containsKey(var);
	}// End variableExists method

	/**
	 * Adds or changes a value in the variable map.
	 * 
	 * @param var the variable to add or change
	 * @param exp the value of the variable
	 */
	private static void setVariable(String var, String exp) {
		// The variable already exists, so remove the old value
		if(variableExists(var)) {
			variableTable.remove(var);
		}
		// Now add the new value
		variableTable.put(var, exp);
	}// End setVariable method

	/**
	 * Used to declare variables.  Takes an expression of the form "x:y" and
	 * and sets the variable x to the value y.
	 * 
	 * @param expression the expression to evaluate
	 * @return null, in order to satisfy the Evaluator interface
	 * @throws VariableAssignmentException
	 */
	public static Object addVariable(String exp)
											throws VariableAssignmentException {
		String[] toMap = exp.split(":");
		if(toMap.length != 2 || toMap[0].length() < 1) {
			throw new VariableAssignmentException(exp);
		} else {
			setVariable(toMap[0], toMap[1]);
			return null;
		}
	}// End addVariable method

	/**
	 * Returns the expression attached to a variable.  If the variable is not
	 * bound, throws a NoSuchVariableExistsException.
	 * 
	 * @param var the variable to retrieve
	 * @return the evaluated variable
	 * @throws NoSuchVariableExistsException 
	 * @throws VariableAssignmentException 
	 * @throws InvalidExpressionException 
	 */
	public static String getExpression(String var)
										throws NoSuchVariableExistsException,
											   VariableAssignmentException,
											   InvalidExpressionException {
		if(variableExists(var)) {
			Evaluator eval = Tools.getEvaluator(variableTable.get(var));
			return eval.evaluate(variableTable.get(var)).toString();
		} else {
			throw new NoSuchVariableExistsException(var);
		}
	}// End getExpression method

	/**
	 * Returns the original expression used when the requested variable was set,
	 * or throws a NoSuchVariableExistsException if the variable does not exist.
	 * 
	 * @param var the variable to look up
	 * @return the original expression stored in this variable
	 * @throws NoSuchVariableExistsException 
	 */
	public static String getFormula(String var)
										throws NoSuchVariableExistsException {
		//Make sure that variable exists
		if(variableExists(var)) {
			return variableTable.get(var);
		} else {
			throw new NoSuchVariableExistsException(var);
		}
	}// End getFormula method

	/**
	 * This method takes a string and determines the type of expression it is,
	 * then returns the proper evaluator object to handle it.
	 * 
	 * Note: THIS IS A HARD CODED METHOD AND WILL NOT SUPPORT ANY EVALUATOR NOT
	 * IMPLEMENTED WITH THE ORIGINAL PACKAGE.
	 *
	 * @param expression the expression to evaluate
	 * @return the correct Evaluator for the given expression
	 * @throws VariableAssignmentException
	 * @throws NoSuchVariableExistsException
	 * @throws InvalidExpressionException 
	 */
	public static Evaluator getEvaluator(String expression)
										throws VariableAssignmentException,
											   NoSuchVariableExistsException,
											   InvalidExpressionException {
		// If we can't determine the type of expression, return null
		Evaluator theExpression = null;

		// Used for sorting
		ArrayList<String> matchToSort = new ArrayList<String>();

		// Add variables to the expression
		expression = addVars(expression);
		// Pull out strings and boolean operators
		Pattern isStringOrBoolExp = Pattern.compile("\\\"[^\\\"]*\\\"|" +
								"true|false|\\||&|!=|\\<=|\\>=|\\<|\\>|!|=");

		// Pull out doubles, integers, and mathematical operators
		Pattern isDoubleOrIntegerExp = Pattern.compile("[0-9]*\\.?[0-9]+" + 
												"|\\+|-|\\*|/|%|\\^|\\(|\\)|~");

		// Pull out doubles and floats
		Pattern isDoubleOrFloat = Pattern.compile("[0-9]*\\.[0-9]+");

		Matcher m = isStringOrBoolExp.matcher(expression);

		// Only will run if matcher found a string or boolean operator
		if(m.find()) {
			matchToSort.add(m.group());

			// Put items into the ArrayList
			while(m.find()) {
				matchToSort.add(m.group());
			}

			// Sort items in the ArrayList - puts strings at top
			Collections.sort(matchToSort);

			if(matchToSort.get(0).charAt(0) == '"') {
				theExpression = new StringEval();
			} else {
				theExpression = new BooleanEval();
			}
		}// End if string/boolean

		m = isDoubleOrIntegerExp.matcher(expression);

		// Only if expression wasn't a string or boolean expression
		if(theExpression == null) {
			// Put items into the ArrayList
			while(m.find()) {
				matchToSort.add(m.group());
			}

			// Sort items in the ArrayList - puts '%' at top
			Collections.sort(matchToSort);

			m = isDoubleOrFloat.matcher(expression);

			// If it's a double expression
			if(matchToSort.get(0).charAt(0) != '%' && m.find()) {
				theExpression = new DoubleEval();
			} else {
				theExpression = new IntegerEval();
			}
		}// End if double/integer

		// Return the correct expression
		return theExpression;
	}// End getEvaluator method

	/**
	 * Searches an expression string for variables and replaces them with the
	 * correct expression they correspond to.
	 * 
	 * Note: THIS IS A HARD CODED METHOD AND WILL NOT SUPPORT ANY EVALUATOR CASE
	 * NOT IMPLEMENTED WITH THE ORIGINAL PACKAGE.
	 * 
	 * @param expression the expression to search through
	 * @return the expression, with all variables replaced
	 * @throws NoSuchVariableExistsException
	 * @throws InvalidExpressionException 
	 * @throws VariableAssignmentException 
	 */
	public static String addVars(String expression)
										throws NoSuchVariableExistsException,
											   VariableAssignmentException,
											   InvalidExpressionException {
		
		//These are used for conformity (eg. no spaces in expressions)
		String returnValue = "";
		expression = compress(expression);
		
		String regex = "true|false|[a-zA-Z]+[a-zA-Z0-9]*|\\\"[^\\\"]*\\\"";
		
		//Holds everything in the expression that is not a variable
		//each index is indication of where a variable must be inserted
		String[] origionalExp = expression.split(regex);

		// Used for checking for variables
		Pattern p = Pattern.compile(regex);
		//This will only contain variable
		Matcher vars = p.matcher(expression);

		String current;
		//index is used to keep track on the expression while it is being rebuilt
		//using the origionalExp array
		int index = 0;
		//While we have variables to insert into the expression...
		while(vars.find()) {
			//Get a variable to insert
			current = vars.group();
			// If it was a boolean, put it back in the expression
			if((current.equals("true") || current.equals("false")) &&
					origionalExp.length > index) {
				returnValue = returnValue + origionalExp[index] + current;
			} else if(current.equals("true") || current.equals("false")) {
				// Expression was a single boolean
				returnValue = returnValue + current;
			} else if(current.charAt(0) == '"' && origionalExp.length > index) {
				// If it was a string, put it back in the expression
				returnValue = returnValue + origionalExp[index] + current;	
			} else if(current.charAt(0) == '"') {
				// Expression was a single string
				returnValue = returnValue + current;
			} else {
				// There is a variable to insert
				if(variableExists(current) &&
						origionalExp.length > index) {
					returnValue = returnValue + origionalExp[index] +
						getExpression(current);
				} else if(variableExists(current)) {
					// Expression was a single variable
					returnValue = returnValue +
						getExpression(current);
				} else {
					//Couldn't find the variable in the hash map
					throw new NoSuchVariableExistsException(current);
				}
			}

			index++;
		}// End while

		// If there is anything left in the origionalExp array add it
		if(origionalExp.length == index + 1) {
			returnValue = returnValue + origionalExp[index];
		}
	
		return returnValue;
	}// End addVars method

    /**
     * A method used to remove the "whitespace" from a string.
     * 
     * @param s the string to be compressed
     * @return the compressed string
     */
    public static String compress(String s) {
    	int numQuotes = 0;
    	char[] chars = s.toCharArray();
        String answer = "";

        for(int i = 0; i < chars.length; i++) {
        	if(chars[i] == '"') {
        		numQuotes++;
        		answer += chars[i];
        	} else if(numQuotes % 2 != 0) {
        		answer += chars[i];
        	} else if(chars[i] != ' ') {
        		answer += chars[i];
        	}
        }

        return answer;
    }// End compress method
}// End Tools class