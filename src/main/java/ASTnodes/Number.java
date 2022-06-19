package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Number extends ASTNode {
    private Integer n;

    public Number(Integer n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Number{" +
                "n=" + n +
                '}';
    }

    @Override
    public Type typeof() {
        return Type.INT;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // Nothing
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitNumber(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitNumber(this);
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getN() {
        return n;
    }
}
