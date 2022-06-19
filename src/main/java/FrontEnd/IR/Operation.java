package FrontEnd.IR;

import ASTnodes.Type;
import BackEnd.VarTable;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class Operation extends IRInstruction {
    private final String op;
    private Type type;

    public Operation(String op, Type type) {
        this.op = op;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Operation{" + op + '}';
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        // TODO: il tipo può tornare utile in futuro
        switch (op) {
            case "+": mv.visitInsn(IADD); break;
            case "-": mv.visitInsn(ISUB); break;
            case "*": mv.visitInsn(IMUL); break;
            case "/": mv.visitInsn(IDIV); break;
            case "and": mv.visitInsn(IAND); break;
            case "or": mv.visitInsn(IXOR); break;
            default:
                System.out.println("WARNING: " + op + " non è valido");
                break;
        }
    }
}
