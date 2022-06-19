package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class If2 extends If {
    private Op operator;

    public If2(If.Op operator, LabelIR elseGoto) {
        super(elseGoto);
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "If2   {" + this.operator + ", elseGoto=" + this.elseGoto + "}";
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        switch(operator) {
            case EQ: mv.visitJumpInsn(IFNE, elseGoto.getASMLabel()); break;
            case NEQ: mv.visitJumpInsn(IFEQ, elseGoto.getASMLabel()); break;
            case LT: mv.visitJumpInsn(IFGE, elseGoto.getASMLabel()); break;
            case LET: mv.visitJumpInsn(IFGT, elseGoto.getASMLabel()); break;
            case GT: mv.visitJumpInsn(IFLE, elseGoto.getASMLabel()); break;
            case GET: mv.visitJumpInsn(IFLT, elseGoto.getASMLabel()); break;
            default: break;
        }
    }
}
