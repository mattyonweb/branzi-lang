package ASTnodes;

public class AssignVar extends ASTNode {
    private final ASTNode varId;
    private final ASTNode value;

    public AssignVar(ASTNode varId, ASTNode value) {
        this.varId = varId;
        this.value = value;
    }

    @Override
    public String toString() {
        return "AssignVar{" +
                "varId='" + varId + '\'' +
                ", value=" + value +
                '}';
    }
}
