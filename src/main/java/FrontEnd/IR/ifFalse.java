package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class ifFalse extends If {
    public ifFalse(LabelIR goTo) {
        super(goTo);
    }

    @Override
    public String toString() {
        return "IfFalse {goto: " + this.goTo + "}";
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
//        mv.visitInsn(ICONST_0); // load FALSE
        mv.visitJumpInsn(IFNE, goTo.getASMLabel()); // Jump iff pop() == FALSE
    }
}
