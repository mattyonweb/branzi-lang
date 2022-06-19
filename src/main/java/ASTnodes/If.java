package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class If extends ASTNode {
    private ASTNode condition;
    private ASTNode code;

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

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitIf(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitIf(this);
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

    public void setCondition(ASTNode condition) {
        this.condition = condition;
    }

    public void setCode(ASTNode code) {
        this.code = code;
    }
}
