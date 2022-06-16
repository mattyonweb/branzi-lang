package ASTnodes;

import java.util.List;

import static java.lang.Math.max;

public class Function extends ASTNode{
    private final Identifier funcId;
    private final Type funcType;
    private final List<Identifier> funcArgs;
    private final ASTNode body;

    public Function(Identifier funcId, Type funcType, List<Identifier> funcArgs, ASTNode body) {
        this.funcId = funcId;
        this.funcType = funcType;
        this.funcArgs = funcArgs;
        this.body = body;
    }

    @Override
    public Type typeof() {
        return (funcType.isCompund() ? funcType.getLastParameter() : funcType);
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // controllare che t1 == t2 for t1,t2 in zip(funcType, funcArgs)

        if (max(funcType.getParameters().size()-1, 0) != funcArgs.size()) {
            throw new TypeCheckerFail(
                    "Number of arguments (" + funcArgs.size() + ") " +
                            "and type of function (" + (funcType.getParameters().size()-1) + ") don't coincide",
                    this,
                    funcType,
                    null // TODO?
            );
        }

        for (int i = 0; i < funcArgs.size(); i++) {
            funcArgs.get(i).setType(funcType.getParameter(i));
        }

        this.body.typecheck();
    }

    @Override
    public String toString() {
        return "Function{" +
                "\n\tfuncId=" + funcId +
                "\n\tfuncType=" + funcType +
                "\n\tfuncArgs=" + funcArgs +
                "\nbody=" + body +
                '}';
    }
}
