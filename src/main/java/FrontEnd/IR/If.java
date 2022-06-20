package FrontEnd.IR;

import Utils.NotYetImplemented;

public abstract class If extends IRInstruction {
    public final LabelIR goTo;

    public static enum Op {EQ, NEQ, LT, LET, GT, GET};
    public static Op negate(Op op) {
        switch (op) {
            case EQ: return Op.NEQ;
            case NEQ: return Op.EQ;
            case LT: return Op.GET;
            case LET: return Op.GT;
            case GT: return Op.LET;
            case GET: return Op.LT;
        }
        throw new NotYetImplemented("BIG ERROR HERE! OP " + op + " INEXISTENT");
    }

    public If(LabelIR goTo) {
        this.goTo = goTo;
    }
}
