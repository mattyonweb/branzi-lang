package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class If2 extends If {
    private Op operator;

    public If2(If.Op operator, LabelIR goTo) {
        super(goTo);
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "If2   {" + this.operator + ", goto=" + this.goTo + "}";
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        switch(operator) {
            case EQ: mv.visitJumpInsn(IF_ICMPEQ, goTo.getASMLabel()); break;
            case NEQ: mv.visitJumpInsn(IF_ICMPNE, goTo.getASMLabel()); break;
            case LT: mv.visitJumpInsn(IF_ICMPLT, goTo.getASMLabel()); break;
            case LET: mv.visitJumpInsn(IF_ICMPLE, goTo.getASMLabel()); break;
            case GT: mv.visitJumpInsn(IF_ICMPGT, goTo.getASMLabel()); break;
            case GET: mv.visitJumpInsn(IF_ICMPGE, goTo.getASMLabel()); break;
            default: break;
        }
    }
}
