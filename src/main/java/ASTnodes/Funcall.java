package ASTnodes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class Funcall extends ASTNode {
    private final Identifier funcId;
    private final List<ASTNode> args = new ArrayList<>();

    public Funcall(Identifier func, List<ASTNode> args) {
        this.funcId = func;
        this.args.addAll(args);
    }

    public Funcall(Identifier func) {
        this.funcId = func;
    }

    //////////////////////////

    @Override
    public Type typeof() {
        return funcId.typeof().isCompund() ? funcId.typeof().getLastParameter() : funcId.typeof();
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        Type functionType = funcId.typeof();

        if (max(functionType.getParameters().size()-1, 0) != args.size()) {
            throw new TypeCheckerFail(
                    "Number of arguments given and type of function don't coincide",
                    this,
                    functionType,
                    // TODO Non è proprio giusto, è fuorviante per l'utente
                    Type.Function(this.args.stream().map(ASTNode::typeof).collect(Collectors.toList()))
            );
        }

        for (int i = 0; i < args.size(); i++) {
            args.get(i).typecheck();

            TypeCheckerFail.verify(
                    "Argument " + this.args.get(i) + " of funcall has wrong type",
                    this,
                    functionType.getParameter(i),
                    args.get(i).typeof()
            );
        }
    }

    //////////////////////////////

    @Override
    public String toString() {
        return "Funcall{" +
                "funcId=" + funcId +
                ", args=" + args +
                '}';
    }

    public Identifier getFuncId() {
        return funcId;
    }

    public List<ASTNode> getArgs() {
        return args;
    }
}
