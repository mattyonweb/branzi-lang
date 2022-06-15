package ASTnodes;

public class BinOp extends ASTNode {
    private final ASTNode arg1;
    private final String op;
    private final ASTNode arg2;

    public BinOp(ASTNode arg1, String op, ASTNode arg2) {
        this.arg1 = arg1;
        this.op = op;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return "BinOp{" +
                "arg1=" + arg1 +
                ", op='" + op + '\'' +
                ", arg2=" + arg2 +
                '}';
    }
}
