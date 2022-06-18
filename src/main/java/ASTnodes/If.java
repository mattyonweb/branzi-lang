package ASTnodes;

public class If extends ASTNode {
    private final ASTNode condition;
    private final ASTNode code;

    public If(ASTNode condition, ASTNode code) {
        this.condition = condition;
        this.code = code;
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        TypeCheckerFail.verify(
                "Condition of IF is not of boolean type",
                this.condition,
                this.condition.typeof(),
                Type.BOOL
        );
        this.condition.typecheck();
        this.code.typecheck();
    }

    /////////////////////

    @Override
    public String toString() {
        return "If{" +
                "condition=" + condition +
                ", code=" + code +
                '}';
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getCode() {
        return code;
    }
}
