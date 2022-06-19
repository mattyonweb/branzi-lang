package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public abstract class ASTNode {
    public abstract Type typeof();
    public abstract void typecheck() throws TypeCheckerFail;
    public abstract void astvisit(ASTVisitor visitor);
    public abstract ASTNode astmodify(ASTModifier visitor);
}
