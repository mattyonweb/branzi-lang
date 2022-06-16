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

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.condition.typecheck();
        this.code.typecheck();

        TypeCheckerFail.verify(
                "Condition of IF is not of boolean type",
                this.condition,
                this.condition.typeof(),
                Type.BOOL
        );
    }
}
