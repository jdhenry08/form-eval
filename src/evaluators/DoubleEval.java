package evaluators;

import java.util.*;
import java.util.regex.*;

import expressions.*;
import resources.*;
/**
 * DoubleEval.java - This class takes a string and attempts to evaluate it as a
 *     double expression.  If it is not a valid double expression, it returns
 *     an InvalidExpressionException.
 * 
 * @author Justin, William, Bryan
 */
public class DoubleEval implements Evaluator {
	// Set up static variables
	private static final String REGEX = "[0-9]*\\.?[0-9]+" + 
										"|\\+|-|\\*|/|\\^|\\(|\\)|~";
	private static final int PLUS_PRIORITY = 1;
	private static final int MINUS_PRIORITY = 1;
	private static final int TIMES_PRIORITY = 2;
	private static final int DIV_PRIORITY = 2;
	private static final int MOD_PRIORITY = 2;
	private static final int POWER_PRIORITY = 3;
	private static final int NEG_PRIORITY = 4;
	private static final int PAREN_PRIORITY = 5;

	private Pattern p;

	/**
     * Creates a new DoubleEval instance, which compiles the regular expression
     * used to evaluate strings.
     */
	public DoubleEval() {
		p = Pattern.compile(REGEX);
	}// End DoubleEval constructor

	/**
     * Creates a new DoubleEval instance, which compiles the regular expression
     * used to evaluate strings. (For use by IntegerEval)
     * 
     * @param regex the regular expression to use
     */
	public DoubleEval(String regex) {
		p = Pattern.compile(regex);
	}
    /**
     * Evaluates the string expression and returns the resulting value, or
     * returns an InvalidExpressionException if the expression is invalid.
     * 
     * @param expression the expression to evaluate
     * @return the evaluated expression
     * @throws NoSuchVariableExistsException 
     * @throws InvalidExpressionException 
     * @throws VariableAssignmentException 
     */
	public Double evaluate(String expression) 
										throws NoSuchVariableExistsException,
											   InvalidExpressionException,
											   VariableAssignmentException {
		//Add variables to the expression if there are any
		expression = Tools.addVars(expression);
		Matcher m = p.matcher(expression);
		// Split the expression; if format was correct build an expression tree
		if(p.split(expression).length != 0) {
			return null;
		} else {
			try {
				Expression tree = build(m);
				return new Double(((Double)tree.accept(this)).doubleValue());
			} catch(Exception e) {
				throw new InvalidExpressionException(expression);
			}
		}
	}// End evaluate Method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(LitDoubleExp exp) {
		return exp.value;
	}// End visit method
	
	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(PlusExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return new Double(e1 + e2);
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(MinusExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return new Double(e1 - e2);
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(TimesExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return new Double(e1 * e2);
	}// End visit method

	public Double visit(DivExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return new Double(e1 / e2);
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(PowerExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return new Double(Math.pow(e1, e2));
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(ParenExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(LitStringExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(ModExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return new Double(e1 % e2);
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(NegExp exp) {
		return 0 - ((Double)exp.right.accept(this)).doubleValue();
	}

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(LitBooleanExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(AndExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(OrExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(NotExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(EqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(NeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(LeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(GeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(LtExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(GtExp exp) {
		return null;
	}// End visit method

    /**
     * Used for building an expression tree for doubles from a given string.
     * 
     * @param m a matcher which was created from a valid double expression (ie.
     * every token of the string matched our given regular expression).
     * @return the root of the expression tree, ready to be traversed
     */
	private static Expression build(Matcher m) {
    	// Set up Operator and Expression stacks
		Stack<Operator> operators = new Stack<Operator>();
		Stack<Expression> expTree = new Stack<Expression>();
        // Bottom of stack
		operators.push(new PlusExp("\0", -10));

		// While there is a token in our matcher...
		while(m.find()) {
			String token = m.group();
			double value = Double.NaN;

			// ...determine if the token is an operator or operand... 
			try {// ...it's an operand
				value = Double.parseDouble(token);
				// Check for unary operators
				if(operators.size() > 1 && operators.peek().op.equals("~")) {
					operators.pop();
					expTree.push(new LitDoubleExp(0 - value));
				} else {
					expTree.push(new LitDoubleExp(value));
				}
			} catch(NumberFormatException nfe) {
				// ...it's an operator; is it a right paren?
				if(token.charAt(0) == ')') {
					// Yes; pop operators until we find the matching left paren,
					// building trees and pushing them onto the expression stack
					Expression op = operators.pop();

					while(!op.toString().equals("(")) {
						// Create a new tree
						op.right = expTree.pop();
						op.left = expTree.pop();
						// Push the tree onto the stack
						expTree.push(op);
						op = operators.pop();
					}
				} else {
					// No; determine what operator it is and create a new
					// instance of that type of operator
					Operator op = null;

					switch(token.charAt(0)) {
					case '+':	op = new PlusExp(token, PLUS_PRIORITY);
								break;
					case '-':	op = new MinusExp(token, MINUS_PRIORITY);
								break;
					case '*':	op = new TimesExp(token, TIMES_PRIORITY);
								break;
					case '/':	op = new DivExp(token, DIV_PRIORITY);
								break;
					case '^':	op = new PowerExp(token, POWER_PRIORITY);
								break;
					case '(':	op = new ParenExp(token, PAREN_PRIORITY);
								break;
					case '%':	op = new ModExp(token, MOD_PRIORITY);
								break;
					case '~':	op = new NegExp(token, NEG_PRIORITY);
								break;
					}
					// Check the priority of the operator on top of the stack
					Operator top = operators.peek();
					// Pop operators until the current operator has a higher  
					// priority, building trees and pushing them onto the 
					// expression stack
					while(top.priority >= op.priority &&
							!top.toString().equals("(")) {
						top = operators.pop();
						// Create a new tree
						top.right = expTree.pop();
						top.left = expTree.pop();
						// Push the tree onto the stack
						expTree.push(top);

						top = operators.peek();
					}
					//Push the new operator onto the stack
					operators.push(op);
				}
			}
		}// End while
		// Once all the tokens have been placed onto the correct stack, build
        // the final Expression tree
		while(operators.size() > 1) {
			Expression operator = operators.pop();
			// Create a new tree
			operator.right = expTree.pop();
			operator.left = expTree.pop();
			// Push the tree onto the stack
			expTree.push(operator);
		}
		// Return the final Expression tree, which will be the only object left
		// on the Expression stack
		return expTree.pop();
	}// End build method
}// End DoubleEval class