package FrontEnd.IR;

public class Operation extends IRInstruction {
    private final String op;

    public Operation(String op) {
        this.op = op;
    }

    public static String convert(String op) {
        return op;
    }

    @Override
    public String toString() {
        return "Operation{" + op + '}';
    }
}
