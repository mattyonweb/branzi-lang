package ASTnodes;

public class AssignVar extends ASTNode {
    public final ASTNode varId;
    public final ASTNode value;

    public AssignVar(ASTNode varId, ASTNode value) {
        this.varId = varId;
        this.value = value;

        // Ugly hack: se rval è un array esplicito tipo [1,2,3] o []
        // allora affidagli il tipo dichiarato. Serve perché in dichiarazioni
        // tipo  l : list int = [], la lista vuota sa di essere una `list` ma non sa di essere `int`
        if (this.value instanceof MyArray) {
            ((MyArray) this.value).setAssignedType(this.varId.typeof());
        }
    }

    @Override
    public String toString() {
        return "AssignVar{" +
                "varId='" + varId + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public Type typeof() {
        return Type.VOID;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        this.value.typecheck();

        TypeCheckerFail.verify(
                "Promised type and actual type differ in declaration of variable",
                this, this.varId.typeof(), value.typeof()
        );
    }

}
