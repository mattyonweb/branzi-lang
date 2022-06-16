package ASTnodes;

import java.util.ArrayList;

public class MyArray extends ASTNode {
    private ArrayList<ASTNode> list;
    private Type assignedType = null; // POV.  l : list bool := []; come fa [] a sapere che è di tipo bool?

    public MyArray(ArrayList<ASTNode> list) {
        this.list = list;
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
        // Quando l'array è stato dichiarato durante un assegnamento
        // di variabile di tipo list, il tipo lo sai già
        if (this.list.size() == 0 && assignedType != null) {
            return assignedType;
        }

        // Di seguito il codice per calcolare il tipo nel caso di
        // array "nudi", cioè al di fuori di un assegnamento
        // (es. cose tipo x : int := [2,4,88][1])
        if (this.list.size() == 0) {
            Type t = new Type("list", Type.ANY);
            this.setAssignedType(t);
            return t;
        }

        // Se c'è solo un elemento nella lista, ritorna "list t"
        Type t = this.list.get(0).typeof();

        // Controlla se ogni elemento ha tipo uguale al primo elemento
        for (ASTNode x: this.list.subList(1, this.list.size())) {
            if (! Type.subtype(x.typeof(), t)) { // se due elementi di list sono di tipi diversi, ritorna list ANY
                Type out = new Type("list", Type.ANY);
                this.setAssignedType(out);
                return out;
            }
        }

        Type out = new Type("list", t);
        this.setAssignedType(out);
        return out;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        if (this.assignedType != null) {

            // Se mi è stato assegnato (dall'utente) un tipo non-lista,
            // ovviamente il typechecking fallisce subito
            if (!this.assignedType.getTypeName().equals("list")) {
                throw new TypeCheckerFail(
                        "Here we have an array, but declared type is not list",
                        this, new Type("list", Type.ANY), this.assignedType
                );
            }

            // Controlla che, se il tipo dichiarato è list T, tutti i suoi
            // elementi abbiano tipo T
            for (ASTNode x : this.list) {
                x.typecheck();

                TypeCheckerFail.verify(
                        "An element of the array has the wrong type",
                        this, this.assignedType.getParameter(0), x.typeof()
                );
            }
        }
    }
}
