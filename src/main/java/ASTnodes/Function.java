package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

import java.util.List;

import static java.lang.Math.max;

public class Function extends ASTNode{
    private Identifier funcId;
    private Type funcType;
    private List<Identifier> funcArgs;
    private ASTNode body;
    private List<Return> returnNodes;

    public Function(Identifier funcId, Type funcType, List<Identifier> funcArgs, ASTNode body, List<Return> returnNodes) {
        this.funcId = funcId;
        this.funcType = funcType;
        this.funcArgs = funcArgs;
        this.body = body;
        this.returnNodes = returnNodes;
    }

    public Identifier getFuncId() {
        return funcId;
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

        for (Return ret: this.returnNodes) {
            ret.typecheck();

            TypeCheckerFail.verify(
                    "Return statement and function signature have different types",
                    ret,
                    this.typeof(),
                    ret.typeof()
            );
        }
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitFunction(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitFunction(this);
    }

    ///////////////////////////////7

    @Override
    public String toString() {
        return "Function{" +
                "\n\tfuncId=" + funcId +
                "\n\tfuncType=" + funcType +
                "\n\tfuncArgs=" + funcArgs +
                "\n\tbody=" + body +
                "\n\treturn=" + returnNodes +
                '}';
    }

    public Type getFuncType() {
        return funcType;
    }

    public List<Identifier> getFuncArgs() {
        return funcArgs;
    }

    public ASTNode getBody() {
        return body;
    }

    public List<Return> getReturnNodes() {
        return returnNodes;
    }

    public void setFuncId(Identifier funcId) {
        this.funcId = funcId;
    }

    public void setFuncType(Type funcType) {
        this.funcType = funcType;
    }

    public void setFuncArgs(List<Identifier> funcArgs) {
        this.funcArgs = funcArgs;
    }

    public void setBody(ASTNode body) {
        this.body = body;
    }

    public void setReturnNodes(List<Return> returnNodes) {
        this.returnNodes = returnNodes;
    }
}
