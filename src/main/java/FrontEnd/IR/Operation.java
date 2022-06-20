package FrontEnd.IR;

import ASTnodes.Number;
import ASTnodes.Type;
import BackEnd.VarTable;
import Utils.NotYetImplemented;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
            case "==": case "!=": case "<": case "<=": case ">": case ">=":
                this.compileComparison(op, vt, mv);
                break;
            default:
                System.out.println("WARNING: " + op + " non è valido");
                break;
        }
    }

    private void compileComparison(String op, VarTable vt, MethodVisitor mv) {
        Consumer<If.Op> compare = oper -> {
            LabelIR lbl_else  = new LabelIR(new Label());
            LabelIR lbl_after = new LabelIR(new Label());

            List<IRInstruction> instrs = List.of(
                    new If2(If.negate(oper), lbl_else),
                    new Push(new Number(1), Type.INT, "c"),
                    new Jump(lbl_after),
                    lbl_else,
                    new Push(new Number(0), Type.INT, "c"),
                    lbl_after
            );

            instrs.forEach(i -> i.compile(vt, mv));
        };

        switch (op) {
            case "==":
                compare.accept(If.Op.EQ);
                break;
            case "!=":
                compare.accept(If.Op.NEQ);
                break;
            case "<":
                compare.accept(If.Op.LT);
                break;
            case "<=":
                compare.accept(If.Op.LET);
                break;
            case ">":
                compare.accept(If.Op.GT);
                break;
            case ">=":
                compare.accept(If.Op.GET);
                break;
            default:
                throw new NotYetImplemented("Operator " + op);
        }
    }
}
