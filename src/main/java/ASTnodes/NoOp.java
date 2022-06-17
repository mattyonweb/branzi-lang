package ASTnodes;

public class NoOp extends ASTNode {

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {

    }
}
