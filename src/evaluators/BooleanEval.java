package evaluators;

import java.util.*;
import java.util.regex.*;

import expressions.*;
import resources.*;
/**
 * BooleanEval.java - This class takes a string and attempts to evaluate it as a
 *     boolean expression.  If it is not a valid boolean expression, it throws
 *     an InvalidExpressionException.
 * 
 * @author Justin
 */
public class BooleanEval implements Evaluator {
	// Set up static variables
	private static final String REGEX = "\\\"[^\\\"]*\\\"|" + 
								"true|false|\\||&|!=|\\<=|\\>=|\\<|\\>|!|=|" +
								"[0-9]*\\.?[0-9]+|\\+|-|\\*|/|%|\\^|\\(|\\)|~";
	private static final int LEQ_PRIORITY = 1;
	private static final int GEQ_PRIORITY = 1;
	private static final int LT_PRIORITY = 1;
	private static final int GT_PRIORITY = 1;
	private static final int EQU_PRIORITY = 1;
	private static final int NEQ_PRIORITY = 1;

	private static final int AND_PRIORITY = 2;
	private static final int OR_PRIORITY = 2;
	private static final int NOT_PRIORITY = 3;

	private static final int PLUS_PRIORITY = 4;
	private static final int MINUS_PRIORITY = 4;
	private static final int TIMES_PRIORITY = 5;
	private static final int DIV_PRIORITY = 5;
	private static final int MOD_PRIORITY = 5;
	private static final int POWER_PRIORITY = 6;
	private static final int NEG_PRIORITY = 7;

	private static final int PAREN_PRIORITY = 8;

	private Pattern p;

	/**
     * Creates a new BooleanEval instance, which compiles the regular expression
     * used to evaluate strings.
     */
	public BooleanEval() {
		p = Pattern.compile(REGEX);
	}// End BooleanEval constructor

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
	public Boolean evaluate(String expression)
										throws NoSuchVariableExistsException,
											   InvalidExpressionException,
											   VariableAssignmentException {
		expression = Tools.addVars(expression);
		Matcher m = p.matcher(expression);

		// Split the expression; if format was correct build an expression tree
		if(p.split(expression).length != 0) {
			return null;
		} else {
			try {
				Expression tree = build(m);
				return new Boolean(((Boolean)tree.accept(this)).booleanValue());
			} catch(Exception e) {
				throw new InvalidExpressionException(expression);
			}
		}
	}// End evaluate method

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

		return e1 + e2;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(MinusExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 - e2;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(TimesExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 * e2;
	}// End visit method

	public Double visit(DivExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 / e2;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(PowerExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return Math.pow(e1, e2);
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(ParenExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(LitStringExp exp) {
		return exp.value;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(ModExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 % e2;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(LitBooleanExp exp) {
		return exp.value;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Double visit(NegExp exp) {
		return 0 - ((Double)exp.right.accept(this)).doubleValue();
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(AndExp exp) {
		Boolean e1 = ((Boolean)exp.left.accept(this)).booleanValue();
    	Boolean e2 = ((Boolean)exp.right.accept(this)).booleanValue();

		return e1 && e2;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(OrExp exp) {
		Boolean e1 = ((Boolean)exp.left.accept(this)).booleanValue();
    	Boolean e2 = ((Boolean)exp.right.accept(this)).booleanValue();

		return e1 || e2;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(NotExp exp) {
		return !((Boolean)exp.right.accept(this)).booleanValue();
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(EqExp exp) {
		String e1 = (exp.left.accept(this)).toString();
    	String e2 = (exp.right.accept(this)).toString();

		return e1.equals(e2);
	}

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(NeqExp exp) {
		String e1 = (exp.left.accept(this)).toString();
    	String e2 = (exp.right.accept(this)).toString();

		return !e1.equals(e2);
	}

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(LeqExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 <= e2;
	}

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(GeqExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 >= e2;
	}

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(LtExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 < e2;
	}

	/**
	 * Implemented for visitor pattern 
	 */
	public Boolean visit(GtExp exp) {
		Double e1 = ((Double)exp.left.accept(this)).doubleValue();
    	Double e2 = ((Double)exp.right.accept(this)).doubleValue();

		return e1 > e2;
	}

    /**
     * Used for building an expression tree for booleans from a given string.
     * 
     * @param m a matcher which was created from a valid boolean expression (ie.
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
			double dValue = Double.NaN;
			boolean bValue = false;

			// ...determine if the token is an operator or operand... 
			try {// ...it's a double...
				dValue = Double.parseDouble(token);
				// Check for unary operators
				if(operators.size() > 1 && operators.peek().op.equals("~")) {
					operators.pop();
					expTree.push(new LitDoubleExp(0 - dValue));
				} else {
					expTree.push(new LitDoubleExp(dValue));
				}
			} catch(NumberFormatException nfe) {
				if(token.equals("true") ||
				   token.equals("false")) {// ...it's a boolean...
					bValue = Boolean.parseBoolean(token);

					if(operators.size() > 1 && operators.peek().op.equals("!")) {
						operators.pop();
						expTree.push(new LitBooleanExp(!bValue));
					} else {
						expTree.push(new LitBooleanExp(bValue));
					}
				} else if(token.charAt(0) == '"') {
					expTree.push(new LitStringExp(token));
				} else if(token.charAt(0) == ')') {// ...is it a right paren?
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

					if(token.equals("&")) {
						op = new AndExp(token, AND_PRIORITY);
					} else if(token.equals("|")) {
						op = new OrExp(token, OR_PRIORITY);
					} else if(token.equals("!")) {
						op = new NotExp(token, NOT_PRIORITY);
					} else if(token.equals("(")) {
						op = new ParenExp(token, PAREN_PRIORITY);
					} else if(token.equals("=")) {
						op = new EqExp(token, EQU_PRIORITY);
					} else if(token.equals("!=")) {
						op = new NeqExp(token, NEQ_PRIORITY);
					} else if(token.equals("<=")) {
						op = new LeqExp(token, LEQ_PRIORITY);
					} else if(token.equals(">=")) {
						op = new GeqExp(token, GEQ_PRIORITY);
					} else if(token.equals("<")) {
						op = new LtExp(token, LT_PRIORITY);
					} else if(token.equals(">")) {
						op = new GtExp(token, GT_PRIORITY);
					} else if(token.equals("+")) {
						op = new PlusExp(token, PLUS_PRIORITY);
					} else if(token.equals("-")) {
						op = new MinusExp(token, MINUS_PRIORITY);
					} else if(token.equals("*")) {
						op = new TimesExp(token, TIMES_PRIORITY);
					} else if(token.equals("/")) {
						op = new DivExp(token, DIV_PRIORITY);
					} else if(token.equals("%")) {
						op = new ModExp(token, MOD_PRIORITY);
					} else if(token.equals("^")) {
						op = new PowerExp(token, POWER_PRIORITY);
					} else if(token.equals("~")) {
						op = new NegExp(token, NEG_PRIORITY);
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
}// End BooleanEval class