package ASTnodes;

public class Bool extends ASTNode {
    private boolean b;

    public Bool(boolean b) {
        this.b = b;
    }

    @Override
    public Type typeof() {
        return Type.BOOL;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // Nothing to do
    }

    @Override
    public String toString() {
        return "Bool{" + b + "}";
    }
}
