package ASTnodes;

import java.util.ArrayList;

public class MyArray extends ASTNode {
    private ArrayList<ASTNode> list;
    private Type assignedType;
    private Type typeFromElements;

    public MyArray(ArrayList<ASTNode> list) {
        this.list = list;
        this.typeFromElements = deduceType();
    }

    private Type deduceType() {
        // Deduce type by scanning elements of the list
        if (this.list.size() == 0)
            return Type.List(Type.ANY);

        Type candidateType = this.list.get(0).typeof();

        for (ASTNode x: this.list) {
            if (! Type.subtype(x.typeof(), candidateType))
                return Type.List(Type.ANY);
        }

        return Type.List(candidateType);
    }

    public void setAssignedType(Type assignedType) {
        // Chiamato solo quando c'è un assegnamento tipo
        // l : list int := [1,3,5]
        // per comunicare all'array il proprio tipo (utile nel caso di liste vuote,
        // che altrimenti rimarrebbero delle `list ?`
        this.assignedType = assignedType;
    }

    public boolean isEmptyList() {
        return this.list.size() == 0;
    }

    @Override
    public String toString() {
        return "MyArray{" +
                "list=" + list +
                " assignedType=" + assignedType +
                '}';
    }

    @Override
    public Type typeof() {
        // Di seguito il codice per calcolare il tipo nel caso di
        // array "nudi", cioè al di fuori di un assegnamento
        // (es. cose tipo x : int := [2,4,88][1])
        if (assignedType == null)
            return typeFromElements;

        return assignedType;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // In pratica: se è un array nudo, l'utente non ha mai specificato
        // il tipo. Quindi ritorno il tipo calcolato sulla base degli elementi
        if (this.typeFromElements != null && this.assignedType == null) {
            this.list.forEach(ASTNode::typecheck);
        }

        if (this.typeFromElements != null && this.assignedType != null) {
            TypeCheckerFail.verify(
                    "User-given type and calculated type don't coincide",
                    this,
                    this.typeFromElements,
                    this.assignedType
            );

            this.list.forEach(ASTNode::typecheck);
        }
    }
}
