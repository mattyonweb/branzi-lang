package ASTnodes;

public class Return extends ASTNode {
    private ASTNode expr;

    public Return(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public Type typeof() {
        return expr.typeof();
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.expr.typecheck();
    }

    @Override
    public String toString() {
        return "Return{" +
                "expr=" + expr +
                '}';
    }
}
