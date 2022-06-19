package FrontEnd.IR;

import ASTnodes.ASTNode;
import ASTnodes.Identifier;
import ASTnodes.Type;
import BackEnd.VarTable;
import Utils.NotYetImplemented;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class Store extends IRInstruction {
    private final Identifier varId;
    private final Type typeof;

    public Store(ASTNode varId, Type typeof) {
        this.varId = (Identifier) varId;
        this.typeof = typeof;
    }

    @Override
    public String toString() {
        return "Store {" +
                "varId=" + varId +
                ", typeof=" + typeof +
                '}';
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        if (!vt.containsKey(varId)) {
            vt.bind(varId);
        }

        if (Type.equality(typeof, Type.INT) ||
            Type.equality(typeof, Type.BOOL)) {
            mv.visitVarInsn(ISTORE, vt.get(varId));
        } else {
            throw new NotYetImplemented("Store for type " + typeof);
        }
    }
}
