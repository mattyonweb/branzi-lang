package FrontEnd.IR;

public abstract class If extends IRInstruction {
    public final LabelIR goTo;

    public static enum Op {EQ, NEQ, LT, LET, GT, GET};

    public If(LabelIR goTo) {
        this.goTo = goTo;
    }
}
