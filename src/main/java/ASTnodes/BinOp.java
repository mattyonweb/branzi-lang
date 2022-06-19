package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

import java.util.HashMap;

public class BinOp extends ASTNode {
    private ASTNode arg1;
    private String op;
    private ASTNode arg2;

    private static final HashMap<String, Type> opTypes = new HashMap<>();
    static {
        opTypes.put("+", Type.Function(Type.INT, Type.INT, Type.INT));
        opTypes.put("*", Type.Function(Type.INT, Type.INT, Type.INT));
        opTypes.put("-", Type.Function(Type.INT, Type.INT, Type.INT));
        opTypes.put("/", Type.Function(Type.INT, Type.INT, Type.INT));

        opTypes.put("and", Type.Function(Type.BOOL, Type.BOOL, Type.BOOL));
        opTypes.put("or", Type.Function(Type.BOOL, Type.BOOL, Type.BOOL));
        opTypes.put("xor", Type.Function(Type.BOOL, Type.BOOL, Type.BOOL));

        opTypes.put("==", Type.Function(Type.ANY, Type.ANY, Type.BOOL));
        opTypes.put("!=", Type.Function(Type.ANY, Type.ANY, Type.BOOL));

        opTypes.put("<", Type.Function(Type.INT, Type.INT, Type.BOOL));
        opTypes.put(">", Type.Function(Type.INT, Type.INT, Type.BOOL));
        opTypes.put("<=", Type.Function(Type.INT, Type.INT, Type.BOOL));
        opTypes.put(">=", Type.Function(Type.INT, Type.INT, Type.BOOL));
    }

    public BinOp(ASTNode arg1, String op, ASTNode arg2) {
        this.arg1 = arg1;
        this.op = op;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return "BinOp{" +
                "arg1=" + arg1 +
                ", op='" + op + '\'' +
                ", arg2=" + arg2 +
                '}';
    }

    @Override
    public Type typeof() {
        // Ritorna ultimo parametro della dichiarazione di tipo.
        // foo :: A -> B -> C  ===> (foo 5 10) :: C
        return opTypes.get(this.op).getLastParameter();
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.arg1.typecheck();
        this.arg2.typecheck();

        TypeCheckerFail.verify(
                "First argument of binary operator has invalid type",
                this,
                opTypes.get(this.op).getParameter(0),
                this.arg1.typeof()
        );

        TypeCheckerFail.verify(
                "Second argument of binary operator has invalid type",
                this,
                opTypes.get(this.op).getParameter(1),
                this.arg2.typeof()
        );
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitBinOp(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitBinOp(this);
    }

    //////////////////////

    public ASTNode getArg1() {
        return arg1;
    }

    public String getOp() {
        return op;
    }

    public ASTNode getArg2() {
        return arg2;
    }

    public void setArg1(ASTNode arg1) {
        this.arg1 = arg1;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setArg2(ASTNode arg2) {
        this.arg2 = arg2;
    }
}
