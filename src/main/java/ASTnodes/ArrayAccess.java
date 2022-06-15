package ASTnodes;

public class ArrayAccess extends ASTNode {
    private final ASTNode id;
    private final ASTNode offset;

    public ArrayAccess(ASTNode id, ASTNode offset) {
        this.id = id;
        this.offset = offset;
    }


    @Override
    public String toString() {
        return "ArrayAccess{" +
                "id=" + id +
                ", offset=" + offset +
                '}';
    }

    public ASTNode getVariableName() {
        return this.id;
    }

    public ASTNode getOffset() {
        return offset;
    }
}
