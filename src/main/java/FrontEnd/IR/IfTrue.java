package FrontEnd.IR;

public class IfTrue extends If {
    public IfTrue(LabelIR ifNotGoto) {
        super(ifNotGoto);
    }

    @Override
    public String toString() {
        return "IfTrue {" + this.elseGoto + "}";
    }
}
