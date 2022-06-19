package FrontEnd.IR;

public class If2 extends If {
    private Op operator;

    public If2(If.Op operator, LabelIR ifNotGoto) {
        super(ifNotGoto);
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "If2   {" + this.operator + ", elseGoto=" + this.elseGoto + "}";
    }
}
