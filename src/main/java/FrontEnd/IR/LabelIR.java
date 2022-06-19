package FrontEnd.IR;

public class LabelIR extends IRInstruction {
    @Override
    public String toString() {
        return "Label {" + String.valueOf(this.hashCode()).substring(0, 4) + "}";
    }
}
