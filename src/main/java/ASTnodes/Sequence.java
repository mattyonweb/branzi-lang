package ASTnodes;

public class Sequence extends ASTNode {
    private final ASTNode n1;
    private final ASTNode n2;

    public Sequence(ASTNode n1, ASTNode n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    @Override
    public String toString() {
        return "Sequence{\n" +
                "n1=" + n1 + ",\n" +
                "n2=" + n2 +
                '}';
    }
}
