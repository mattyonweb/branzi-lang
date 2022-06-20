package FrontEnd.IR;

import ASTnodes.Type;
import BackEnd.VarTable;
import Utils.NotYetImplemented;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class JVMInstruction extends IRInstruction {
    private final typeJvmInstr instrType;
    private final String[] args;

    public enum typeJvmInstr {GETSTATIC};

    public JVMInstruction(typeJvmInstr instrType, String ... args) {
        this.instrType = instrType;
        this.args = args;
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        if (instrType.equals(typeJvmInstr.GETSTATIC)) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, args[0], args[1], args[2]);
        }
    }

    public static String convertType(ASTnodes.Type t) {
        if (Type.equality(t, Type.INT))
            return "I";
        throw new NotYetImplemented("");
    }
}
