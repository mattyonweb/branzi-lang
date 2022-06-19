package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class UpdateVar extends ASTNode {
    private ASTNode varId;
    private ASTNode value;

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

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitUpdateVar(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitUpdateVar(this);
    }

    ////////////////////////7

    public ASTNode getVarId() {
        return varId;
    }

    public ASTNode getValue() {
        return value;
    }

    public void setVarId(ASTNode varId) {
        this.varId = varId;
    }

    public void setValue(ASTNode value) {
        this.value = value;
    }
}
