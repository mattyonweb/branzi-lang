package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class NoOp extends ASTNode {

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {

    }

    @Override
    public void astvisit(ASTVisitor visitor) {
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return null;
    }
}
