package FrontEnd.IR;

import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

public abstract class IRInstruction {
    public abstract void compile(VarTable vt, MethodVisitor mv);
}
