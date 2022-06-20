package FrontEnd.IR;

import ASTnodes.*;
import ASTnodes.Number;
import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class Push extends IRInstruction {
    public static String CONSTANT = "c";
    public static String VAR = "v";

    private ASTNode x;
    private Type t;
    private String loadtype;

    public Push(ASTNode x, Type t, String loadtype) {
        this.x = x;
        this.t = t;
        this.loadtype = loadtype;
    }

    @Override
    public String toString() {
        return "Push  {" +
                "x=" + x +
                ", t=" + t +
                ", loadtype='" + loadtype + '\'' +
                '}';
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        if (this.loadtype.equals(CONSTANT)) {

            if (x instanceof Number) {
                Integer n = ((Number) x).getN();
                if (n==0) mv.visitInsn(ICONST_0);
                else if (n==1) mv.visitInsn(ICONST_1);
                else if (n==2) mv.visitInsn(ICONST_2);
                else if (n==3) mv.visitInsn(ICONST_3);
                else if (n==4) mv.visitInsn(ICONST_4);
                else if (n==5) mv.visitInsn(ICONST_5);
                else if (n==-1) mv.visitInsn(ICONST_M1);
                else if (Math.abs(n) <= 127) mv.visitIntInsn(BIPUSH, n);
                else if (Math.abs(n) < 65535) mv.visitIntInsn(SIPUSH, n);
                else mv.visitLdcInsn(((Number) x).getN());

            }

            else if (x instanceof Bool) {
                if (((Bool) x).getB())
                    mv.visitInsn(ICONST_1);
                else
                    mv.visitInsn(ICONST_0);
            }
        }

        // TODO: e se è una lista? Se è un acccesso ad array?
        else if (this.loadtype.equals(VAR)) {
            Identifier id = (Identifier) this.x;
            mv.visitVarInsn(ILOAD, vt.get(id));
        }
    }
}
