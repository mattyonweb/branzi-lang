package FrontEnd.IR;

import ASTnodes.*;
import ASTnodes.ASTvisitors.ASTVisitor;
import ASTnodes.If;
import ASTnodes.Number;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class IRGenerator extends ASTVisitor {
    final String className;
    List<IRInstruction> instructions = new ArrayList<>();
    Map<Identifier, Function> functions = new HashMap<>();

    public IRGenerator() {
        System.out.println("WARNING: no className given; only use in test!");
        className = "";
    }

    public IRGenerator(String classname) {
        this.className = classname;
    }

    public IRGenerator(String classname, List<Function> functions) {
        this.className = classname;
        for (Function f: functions) {
            this.functions.put(f.getFuncId(), f);
        }
    }

    @Override
    public void visitBool(Bool b) {
        instructions.add(
                new Push(b, Type.BOOL, Push.CONSTANT)
        );
    }

    @Override
    public void visitNumber(Number n) {
        instructions.add(
                new Push(n, Type.INT, Push.CONSTANT)
        );
    }

    @Override
    public void visitAssignVar(AssignVar av) {
        av.value.astvisit(this);
        instructions.add(
                new Store(av.varId, av.varId.typeof())
        );
    }

    @Override
    public void visitUpdateVar(UpdateVar uv) {
        uv.getValue().astvisit(this);
        instructions.add(
                new Store(uv.getVarId(), uv.getValue().typeof()) // TODO: typeof giusto?
        );
    }

    @Override
    public void visitBinOp(BinOp bo) {
        super.visitBinOp(bo);
        instructions.add(
                new Operation(bo.getOp(), BinOp.opTypes.get(bo.getOp()))
        );
    }

    @Override
    public void visitIdentifier(Identifier id) {
        instructions.add(
                new Push(id, id.typeof(), Push.VAR)
        );
    }


    @Override
    public void visitIf(If i) {
        LabelIR label = new LabelIR(new Label());

        i.getCondition().astvisit(this);
        instructions.add(new ifFalse(label));
        i.getCode().astvisit(this);
        instructions.add(label);
    }

    @Override
    public void visitWhile(While w) {
        LabelIR labelStart    = new LabelIR(new Label());
        LabelIR labelAfterEnd = new LabelIR(new Label());

        this.instructions.add(labelStart);
        w.getCondition().astvisit(this);
        instructions.add(new ifFalse(labelAfterEnd));
        w.getBlock().astvisit(this);
        instructions.add(new Jump(labelStart));
        instructions.add(labelAfterEnd);
    }

    @Override
    public void visitFuncall(Funcall c) {
        if (c.getFuncId().getId().equals("print")) {
            this.instructions.add(new JVMInstruction(
                    JVMInstruction.typeJvmInstr.GETSTATIC,
                    "java/lang/System", "out", "Ljava/io/PrintStream;")
            );
        }

        c.getArgs().forEach(x -> x.astvisit(this));

        Consumer<MethodVisitor> funcIdCompiler;
        if (c.getFuncId().getId().equals("print")) {
            funcIdCompiler = mv -> {
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        "java/io/PrintStream",
                        "println",
                        "(" + JVMInstruction.convertType(c.getFuncId().typeof().getParameter(0)) + ")V",
                        false);
            };
        } else {
            funcIdCompiler = mv -> {
                mv.visitMethodInsn(
                        INVOKESTATIC,
                        this.className,
                        c.getFuncId().getId(),
                        JVMInstruction.convertType(functions.get(c.getFuncId()).getFuncType()),
                        false
                );
            };
        }

        instructions.add(
                new Call(c.getFuncId(), funcIdCompiler)
        );
    }

    @Override
    public void visitReturn(Return r) {
        super.visitReturn(r);
        instructions.add(new ReturnIR(r.typeof()));
    }

    //////////////7


    public List<IRInstruction> getInstructions() {
        return instructions;
    }
    public void printInstructions() {
        this.instructions.forEach(System.out::println);
    }
}
