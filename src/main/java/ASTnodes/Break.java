package ASTnodes;

public class Break extends ASTNode {
    private While while_;

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    public void setWhile(While w) {
        this.while_ = w;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
    }
}
