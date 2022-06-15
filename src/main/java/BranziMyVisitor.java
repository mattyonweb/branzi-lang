import ASTnodes.*;
import ASTnodes.Number;
import generated.BranziBaseVisitor;
import generated.BranziParser;

public class BranziMyVisitor extends BranziBaseVisitor<ASTNode> {
    Environment env = null;

    @Override
    public ASTNode visitProgram(BranziParser.ProgramContext ctx) {
        return this.visit(ctx.block());
    }

    @Override
    public ASTNode visitBlock(BranziParser.BlockContext ctx) {
        if (ctx.statement().size() == 0) {
            return null; // TODO: return VOID!
        }

        env = new Environment(env);

        if (ctx.statement().size() == 1) {
            ASTNode result = this.visit(ctx.statement(0));
            env = env.outer();
            return result;
        }

        ASTNode fstNode = this.visit(ctx.statement(0));
        ASTNode sndNode = this.visit(ctx.statement(1));
        ASTNode sequence = new Sequence(fstNode, sndNode);
        for (BranziParser.StatementContext sc : ctx.statement().subList(2, ctx.statement().size())) {
            ASTNode newNode = this.visit(sc);
            sequence = new Sequence(sequence, newNode);
        }

        env = env.outer();
        return sequence;
    }

    @Override
    public ASTNode visitStatement(BranziParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public ASTNode visitIf_statement(BranziParser.If_statementContext ctx) {
        return new If(this.visit(ctx.expr()),
                      this.visit(ctx.block()));
    }

    @Override
    public ASTNode visitWhile_statement(BranziParser.While_statementContext ctx) {
        return super.visitWhile_statement(ctx); // TODO
    }

    @Override
    public ASTNode visitMulDiv(BranziParser.MulDivContext ctx) {
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

    @Override
    public ASTNode visitAddSub(BranziParser.AddSubContext ctx) {
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

    @Override
    public ASTNode visitParens(BranziParser.ParensContext ctx) {
        return this.visit(ctx.Exp);
    }

    @Override
    public ASTNode visitExprVar(BranziParser.ExprVarContext ctx) {
        return env.get(ctx.IDENTIFIER().getText());
    }

    @Override
    public ASTNode visitExprNum(BranziParser.ExprNumContext ctx) {
        return new Number(Integer.parseInt(ctx.NUMBER().getText()));
    }

    @Override
    public ASTNode visitExprAssign(BranziParser.ExprAssignContext ctx) {
        return new UpdateVar(
                env.get(ctx.IDENTIFIER().getText()),
                this.visit(ctx.expr())
        );
    }

    @Override
    public ASTNode visitDeclareNewVar(BranziParser.DeclareNewVarContext ctx) {
        Identifier varId = new Identifier(
                ctx.IDENTIFIER().getText(),
                this.visitType(ctx.type())
        );

        env.put(varId.getId(), varId);

        return new AssignVar(
                varId, this.visit(ctx.expr())
        );
    }

    @Override
    public Type visitType(BranziParser.TypeContext ctx) {
        return new Type(ctx.getText()); // ?
    }

    //    @Override
//    public ASTNode visitExprAssign(BranziParser.ExprAssignContext ctx) {
//        String varname = ctx.IDENTIFIER().getText();
//        ASTNode identifier = new Identifier(varname);
//        ASTNode node = this.visit(ctx.expr());
//
//        env.put(varname, identifier);
//        return new AssignVar(varname, node);
//    }


}
