package ASTnodes;

public class If extends ASTNode {
    private final ASTNode condition;
    private final ASTNode code;

    public If(ASTNode condition, ASTNode code) {
        this.condition = condition;
        this.code = code;
    }

    @Override
    public String toString() {
        return "If{" +
                "condition=" + condition +
                ", code=" + code +
                '}';
    }
}
