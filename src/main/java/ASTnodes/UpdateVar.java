package ASTnodes;

public class UpdateVar extends ASTNode {
    private final ASTNode varId;
    private final ASTNode value;

    public UpdateVar(ASTNode varId, ASTNode value) {
        this.varId = varId;
        this.value = value;
    }

    @Override
    public String toString() {
        return "UpdateVar{" +
                "varId=" + varId +
                ", value=" + value +
                '}';
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.value.typecheck();

        TypeCheckerFail.verify(
                "New value for assignee is of wrong type",
                this,
                this.varId.typeof(),
                this.value.typeof()
        );
    }
}
