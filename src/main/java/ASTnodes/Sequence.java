package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Sequence extends ASTNode {
    private ASTNode n1;
    private ASTNode n2;

    public Sequence(ASTNode n1, ASTNode n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.n1.typecheck();
        this.n2.typecheck();
    }


    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitSequence(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitSequence(this);
    }

    ////////////////////////7

    @Override
    public String toString() {
        return "Sequence{\n" +
                "n1=" + n1 + ",\n" +
                "n2=" + n2 +
                '}';
    }

    public ASTNode getN1() {
        return n1;
    }

    public ASTNode getN2() {
        return n2;
    }

    public void setN1(ASTNode n1) {
        this.n1 = n1;
    }

    public void setN2(ASTNode n2) {
        this.n2 = n2;
    }
}
