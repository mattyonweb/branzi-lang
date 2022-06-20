package FrontEnd.IR;

import ASTnodes.ASTNode;
import ASTnodes.Identifier;
import BackEnd.VarTable;
import Utils.NotYetImplemented;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.function.Consumer;

import static org.objectweb.asm.Opcodes.*;

public class Call extends IRInstruction {
    private Identifier funcId;
    private Consumer<MethodVisitor> funcIdCompiler;

    public Call(Identifier funcId, Consumer<MethodVisitor> funcIdCompiler) {
        this.funcId = funcId;
        this.funcIdCompiler = funcIdCompiler;
    }

    @Override
    public String toString() {
        return "Call  {" + funcId + '}';
    }

    @Override
    public void compile(VarTable vt, MethodVisitor mv) {
        this.funcIdCompiler.accept(mv);
    }
}
