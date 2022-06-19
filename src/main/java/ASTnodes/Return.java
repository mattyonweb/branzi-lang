package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Return extends ASTNode {
    private ASTNode expr;

    public Return(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public Type typeof() {
        return expr.typeof();
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.expr.typecheck();
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitReturn(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitReturn(this);
    }

    @Override
    public String toString() {
        return "Return{" +
                "expr=" + expr +
                '}';
    }

    public ASTNode getExpr() {
        return expr;
    }
}
