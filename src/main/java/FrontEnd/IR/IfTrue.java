package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class IfTrue extends If {
    public IfTrue(LabelIR goTo) {
        super(goTo);
    }

    @Override
    public String toString() {
        return "IfTrue {goto: " + this.goTo + "}";
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        mv.visitJumpInsn(IFNE, goTo.getASMLabel());
    }
}
