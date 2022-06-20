package FrontEnd.IR;

import ASTnodes.ASTNode;
import ASTnodes.Identifier;
import BackEnd.VarTable;
import Utils.NotYetImplemented;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

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

        if (funcId.getId().equals("print")) {
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "java/io/PrintStream",
                    "println",
                    "(" + JVMInstruction.convertType(funcId.typeof().getParameter(0)) + ")V",
                    false);
        }

        else {
            throw new NotYetImplemented("Funcall not implemented");
        }
    }
}
