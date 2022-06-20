package ASTnodes.ASTvisitors;

import ASTnodes.BinOp;
import ASTnodes.Function;
import ASTnodes.Return;
import ASTnodes.TypeCheckerFail;

public class VisitorFindIllegalReturn extends ASTVisitor {
    private boolean isInFunction = false;

    @Override
    public void visitFunction(Function f) {
        isInFunction = true;
        super.visitFunction(f);
    }

    @Override
    public void visitReturn(Return r) {
        if (!isInFunction) {
            throw new TypeCheckerFail("Found a return outside a function definition! " + r);
        }
        super.visitReturn(r);
    }

    @Override
    public String toString() {
        return "VisitorFindIllegalReturn{}";
    }
}
