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
        if (Type.equality(t, Type.BOOL))
            return "Z"; // TODO: non so... alla fine jvm usa solo interi, va bene scriver così?
        if (Type.equality(t, Type.VOID))
            return "V";
        if (t.isList())
            return "[" + convertType(t.getParameter(0));
        if (Type.equality(t, Type.ANY))
            return "Ljava/lang/Object;";
        if (Type.equality(t, Type.STRING))
            return "Ljava/lang/String;";

        if (t.isFunction()) {
            if (t.getParameters().size() == 1)
                return "()" + convertType(t.getLastParameter());

            StringBuilder sb = new StringBuilder("(");
            for (Type ty: t.getParameters().subList(0, t.getParameters().size()-1)) {
                sb.append(convertType(ty));
            }
            sb.append(")");
            sb.append(convertType(t.getLastParameter()));
            return sb.toString();
        }

        throw new NotYetImplemented("");
    }
}
