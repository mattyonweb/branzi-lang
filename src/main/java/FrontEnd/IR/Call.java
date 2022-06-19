package FrontEnd.IR;

import ASTnodes.Identifier;

public class Call extends IRInstruction {
    private Identifier funcId;

    public Call(Identifier funcId) {
        this.funcId = funcId;
    }

    @Override
    public String toString() {
        return "Call  {" + funcId + '}';
    }
}
