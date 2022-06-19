package FrontEnd.IR;

public class If extends IRInstruction {
    public final LabelIR elseGoto;

    public static enum Op {EQ, NEQ, LT, LET, GT, GET};

    public If(LabelIR elseGoto) {
        this.elseGoto = elseGoto;
    }
}
