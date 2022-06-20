package FrontEnd.IR;

import ASTnodes.Type;
import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class ReturnIR extends IRInstruction {
    private Type ty;

    public ReturnIR(Type ty) {
        this.ty = ty;
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        if (Type.equality(ty, Type.VOID))
            mv.visitInsn(RETURN);
        else if (Type.equality(ty, Type.INT) || Type.equality(ty, Type.BOOL))
            mv.visitInsn(IRETURN);
        else
            mv.visitInsn(ARETURN);
    }
}
