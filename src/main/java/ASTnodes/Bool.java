package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Bool extends ASTNode {
    private boolean b;

    public Bool(boolean b) {
        this.b = b;
    }

    @Override
    public Type typeof() {
        return Type.BOOL;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // Nothing to do
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitBool(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitBool(this);
    }

    @Override
    public String toString() {
        return "Bool{" + b + "}";
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean getB() {
        return b;
    }
}
