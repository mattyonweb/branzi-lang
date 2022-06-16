package ASTnodes;

import FrontEnd.Environment;

public abstract class ASTNode {
    public abstract Type typeof();
    public abstract void typecheck() throws TypeCheckerFail;
}
