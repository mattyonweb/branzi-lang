package FrontEnd.IR;

import ASTnodes.ASTNode;
import ASTnodes.Type;

public class Store extends IRInstruction {
    private final ASTNode varId;
    private final Type typeof;

    public Store(ASTNode varId, Type typeof) {
        this.varId = varId;
        this.typeof = typeof;
    }

    @Override
    public String toString() {
        return "Store {" +
                "varId=" + varId +
                ", typeof=" + typeof +
                '}';
    }
}
