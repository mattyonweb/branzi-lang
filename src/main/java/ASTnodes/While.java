package ASTnodes;

public class While extends ASTNode {
    private ASTNode condition;
    private ASTNode block;

    public While(ASTNode condition, ASTNode block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        TypeCheckerFail.verify(
                "Condition of WHILE is not of boolean type",
                this.condition,
                this.condition.typeof(),
                Type.BOOL
        );
        this.condition.typecheck();
        this.block.typecheck();
    }

    ///////////////////7

    @Override
    public String toString() {
        return "While{" +
                "condition=" + condition +
                "\n\tblock=" + block +
                '}';
    }
}
