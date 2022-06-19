package FrontEnd.IR;

import ASTnodes.Identifier;
import BackEnd.VarTable;
import Utils.NotYetImplemented;
import org.objectweb.asm.MethodVisitor;

public class Call extends IRInstruction {
    private Identifier funcId;

    public Call(Identifier funcId) {
        this.funcId = funcId;
    }

    @Override
    public String toString() {
        return "Call  {" + funcId + '}';
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        throw new NotYetImplemented("Funcall not implemented");
    }
}
