package ASTnodes;

public class Continue extends ASTNode {
    private While while_;

    public void setWhile(While w) {
        this.while_ = w;
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {

    }
}
