package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFNE;

public class IfTrue extends If {
    public IfTrue(LabelIR ifNotGoto) {
        super(ifNotGoto);
    }

    @Override
    public String toString() {
        return "IfTrue {" + this.elseGoto + "}";
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        mv.visitInsn(ICONST_1); // load TRUE
        mv.visitJumpInsn(IFNE, elseGoto.getASMLabel()); // Jump iff pop() != TRUE
    }
}
