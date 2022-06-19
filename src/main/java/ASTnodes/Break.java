package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Break extends ASTNode {
    private While while_;

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    public void setWhile(While w) {
        this.while_ = w;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitBreak(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitBreak(this);
    }
}
