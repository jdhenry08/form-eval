package resources;

import expressions.*;
/**
 * Evaluator.java - Implements the visitor pattern interface.
 * 
 * @author William, Justin
 */
public interface Evaluator {
	public Object visit(LitDoubleExp exp);
	public Object visit(LitStringExp exp);
	public Object visit(LitBooleanExp exp);

	public Object visit(PlusExp exp);
	public Object visit(MinusExp exp);
	public Object visit(TimesExp exp);
	public Object visit(DivExp exp);
	public Object visit(ModExp exp);
	public Object visit(PowerExp exp);
	public Object visit(NegExp exp);

	public Object visit(AndExp exp);
	public Object visit(OrExp exp);
	public Object visit(NotExp exp);

	public Object visit(EqExp exp);
	public Object visit(NeqExp exp);
	public Object visit(LeqExp exp);
	public Object visit(GeqExp exp);
	public Object visit(LtExp exp);
	public Object visit(GtExp exp);

	public Object visit(ParenExp exp);

	public Object evaluate(String expression)
						throws NoSuchVariableExistsException,
							   InvalidExpressionException,
							   VariableAssignmentException;
}// End Evaluator class