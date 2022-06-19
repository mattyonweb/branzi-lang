package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Continue extends ASTNode {
    private While while_;

    public void setWhile(While w) {
        this.while_ = w;
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {

    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitContinue(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitContinue(this);
    }
}
