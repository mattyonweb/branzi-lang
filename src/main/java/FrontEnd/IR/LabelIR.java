package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class LabelIR extends IRInstruction {
    private Label ASMLabel;

    public LabelIR(Label ASMLabel) {
        this.ASMLabel = ASMLabel;
    }

    @Override
    public String toString() {
        return "Label {" + String.valueOf(this.hashCode()).substring(0, 4) + "}";
    }

    public Label getASMLabel() {
        return ASMLabel;
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        mv.visitLabel(this.ASMLabel);
    }
}
