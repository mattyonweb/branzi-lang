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
}
