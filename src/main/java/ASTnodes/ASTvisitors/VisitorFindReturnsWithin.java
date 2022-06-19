package ASTnodes.ASTvisitors;

import ASTnodes.Return;

import java.util.ArrayList;
import java.util.List;

public class VisitorFindReturnsWithin extends ASTVisitor{
    private List<Return> found = new ArrayList<>();

    @Override
    public void visitReturn(Return r) {
        found.add(r);
        super.visitReturn(r);
    }

    public List<Return> getFound() {
        return found;
    }
}
