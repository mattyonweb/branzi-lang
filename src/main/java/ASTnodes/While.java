package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

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

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitWhile(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitWhile(this);
    }

    ///////////////////7

    @Override
    public String toString() {
        return "While{" +
                "condition=" + condition +
                "\n\tblock=" + block +
                '}';
    }

    public ASTNode getCondition() {
        return condition;
    }

    public ASTNode getBlock() {
        return block;
    }

    public void setCondition(ASTNode condition) {
        this.condition = condition;
    }

    public void setBlock(ASTNode block) {
        this.block = block;
    }
}
