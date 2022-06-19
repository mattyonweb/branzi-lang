package FrontEnd.IR;

import ASTnodes.*;
import ASTnodes.ASTvisitors.ASTVisitor;
import ASTnodes.If;
import ASTnodes.Number;

import java.util.ArrayList;
import java.util.List;

public class IRGenerator extends ASTVisitor {
    List<IRInstruction> instructions = new ArrayList<>();

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
    public void visitBinOp(BinOp bo) {
        super.visitBinOp(bo);
        instructions.add(
                new Operation(Operation.convert(bo.getOp()))
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
        LabelIR label = new LabelIR();

        i.getCondition().astvisit(this);
        instructions.add(new IfTrue(label));
        i.getCode().astvisit(this);
        instructions.add(label);
    }

    @Override
    public void visitFuncall(Funcall c) {
        c.getArgs().forEach(x -> x.astvisit(this));

        instructions.add(new Call(c.getFuncId()));
    }

    //////////////7


    public List<IRInstruction> getInstructions() {
        return instructions;
    }
    public void printInstructions() {
        this.instructions.forEach(System.out::println);
    }
}
