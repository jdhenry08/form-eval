package evaluators;

import expressions.*;
import resources.*;

import java.util.*;
import java.util.regex.*;
/**
 * StringEval.java - This class takes a string and attempts to evaluate it as a
 *     string expression.  If it is not a valid string expression, it returns
 *     an InvalidExpressionException.
 * 
 * @authors Bryan, William, Justin
 */
public class StringEval implements Evaluator {
    private static final String REGEX = "\\\"[^\\\"]*\\\"|\\+";
    private static final int PLUS_PRIORITY = 1;

    private Pattern p;

    /**
     * Creates a new StringEval instance, which compiles the regular expression
     * used to evaluate strings.
     */
    public StringEval() {
        p = Pattern.compile(REGEX);
    }// End StringEval constructor

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
    public String evaluate(String expression)
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
                return new String(((String)tree.accept(this)).toString());
        	} catch(Exception e) {
				throw new InvalidExpressionException(expression);
			}
        }
    }// End evaluate method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(LitStringExp exp) {
        return exp.toString();
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(PlusExp exp) {
    	String e1 = exp.left.accept(this).toString();
    	String e2 = exp.right.accept(this).toString();

    	return new String(e1.substring(0, e1.length() - 1) + e2.substring(1));
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(MinusExp exp) {
    	return null;
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(TimesExp exp) {
        return null;
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(DivExp exp) {
        return null;
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(PowerExp exp) {
        return null;
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
    public String visit(ParenExp exp) {
        return null;
    }// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(LitDoubleExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(ModExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(LitBooleanExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(NegExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(AndExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(OrExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(NotExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(EqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(NeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(LeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(GeqExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(LtExp exp) {
		return null;
	}// End visit method

	/**
	 * Implemented for visitor pattern 
	 */
	public String visit(GtExp exp) {
		return null;
	}// End visit method

    /**
     * Used for building an expression tree for strings from a given string.
     * 
     * @param m a matcher which was created from a valid string expression (ie.
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
            Operator op = null;

            // ...determine if the token is an operator or operand...
            switch(token.charAt(0)) {
            case '"':	token = token.substring(1, token.length() - 1);
            			expTree.push(new LitStringExp(token));
            			break;
            case '+':	op = new PlusExp(token, PLUS_PRIORITY);
            			Operator top = operators.peek();

            			while(top.priority >= op.priority) {
            				top = operators.pop();
            				// Create a new tree
            				top.right = expTree.pop();
            				top.left = expTree.pop();
            				// Push the tree onto the stack
            				expTree.push(top);

            				top = operators.peek();
            			}
            			// Push the new operator onto the stack
            			operators.push(op);
            			break;
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
}// End StringEval class