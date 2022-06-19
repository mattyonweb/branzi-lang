package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class ArrayAccess extends ASTNode {
    private ASTNode id;
    private ASTNode offset;

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

    @Override
    public Type typeof() {
        return this.id.typeof().getParameters().get(0);
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.offset.typecheck();

        if (!this.id.typeof().getTypeName().equals("list"))
            throw new TypeCheckerFail(
                    "Tried access through [] on non-array",
                    this.id,
                    new Type("list", new Type("T")), // TODO probabilmente sbagliato
                    this.id.typeof()
            );

        if (!Type.subtype(this.offset.typeof(), Type.INT))
            throw new TypeCheckerFail(
                    "Array displacement in [] is not a number",
                    this, Type.INT, this.offset.typeof()
            );


    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitArrayAccess(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitArrayAccess(this);
    }

    public void setId(ASTNode id) {
        this.id = id;
    }

    public void setOffset(ASTNode offset) {
        this.offset = offset;
    }
}
