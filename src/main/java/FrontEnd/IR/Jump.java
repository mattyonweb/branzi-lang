package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.GOTO;

public class Jump extends IRInstruction {
    private LabelIR label;

    public Jump(LabelIR label) {
        this.label = label;
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        mv.visitJumpInsn(GOTO, label.getASMLabel());
    }
}
