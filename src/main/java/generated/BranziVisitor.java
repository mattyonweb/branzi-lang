// Generated from /home/groucho/IdeaProjects/SimpleLanguage/src/main/grammar/Branzi.g4 by ANTLR 4.9.2
package generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BranziParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BranziVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BranziParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BranziParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link BranziParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(BranziParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link BranziParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(BranziParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BranziParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(BranziParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BranziParser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(BranziParser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprVar}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprVar(BranziParser.ExprVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprNum}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNum(BranziParser.ExprNumContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(BranziParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(BranziParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(BranziParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprAssign}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAssign(BranziParser.ExprAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declareNewVar}
	 * labeled alternative in {@link BranziParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclareNewVar(BranziParser.DeclareNewVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link BranziParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(BranziParser.TypeContext ctx);
}