package FrontEnd.IR;

import ASTnodes.ASTNode;
import ASTnodes.Type;

public class Push extends IRInstruction {
    public static String CONSTANT = "c";
    public static String VAR = "v";

    private ASTNode x;
    private Type t;
    private String loadtype;

    public Push(ASTNode x, Type t, String loadtype) {
        this.x = x;
        this.t = t;
        this.loadtype = loadtype;
    }

    @Override
    public String toString() {
        return "Push  {" +
                "x=" + x +
                ", t=" + t +
                ", loadtype='" + loadtype + '\'' +
                '}';
    }
}
