package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

import java.util.List;

public class Sequence extends ASTNode {
    private List<ASTNode> nodes;

    public Sequence(List<ASTNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        for (ASTNode x: this.nodes) {
            x.typecheck();
        }
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
        StringBuilder s = new StringBuilder("Sequence{\n");
        for (ASTNode x: this.nodes)
            s.append("\n\t").append(x);
        return s + "\n}";
    }

    public List<ASTNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ASTNode> nodes) {
        this.nodes = nodes;
    }

    public void shorten(int lastIdx) {
        this.nodes = this.nodes.subList(0, lastIdx);
    }
}
