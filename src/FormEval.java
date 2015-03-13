import java.util.*;

import resources.*;
/**
 * FormEval.java - A driver class for the form-eval project
 * 
 * Example code usage:
 * try {
 *     System.out.println(Expression.getEvaluator(exp).evaluate(exp));
 * } catch(NoSuchVariableExistsException e) {
 * } catch(VariableAssignmentExpcetion e) {
 * } catch(InvalidExpressionException e) {
 * }
 *
 * @author William, Justin
 */
public class FormEval {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		String exp = "";
		String answer;

		System.out.println("Input your expression:");

		while(true) {
			System.out.print("]> ");
			exp = stdin.nextLine();

			if(exp.equalsIgnoreCase("exit")) {
				System.exit(0);
			} else if(!exp.equals("")) {
				try {
					if(exp.contains(":")) {
						Tools.addVariable(exp);
						String[] tokens = exp.split(":");
						answer = tokens[0] + " was set to " + tokens[1];
					} else {
						Evaluator eval = Tools.getEvaluator(exp);
						answer = eval.evaluate(exp).toString();
					}
				} catch(NoSuchVariableExistsException nsvee) {
					answer = nsvee.getMessage();
				} catch(VariableAssignmentException vae) {
					answer = vae.getMessage();
				} catch(InvalidExpressionException iee) {
					answer = iee.getMessage();
				}

				System.out.println(answer);
			}
		}
	}
}// End FormEval class