package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyArray extends ASTNode {
    private List<ASTNode> list;
    private Type assignedType;
    private Type typeFromElements;

    public MyArray(ArrayList<ASTNode> list) {
        this.list = list;
        this.typeFromElements = deduceType();
    }

    private Type deduceType() {
        // Deduce type by scanning elements of the list
        List<Type> argsTypes = this.list
                .stream()
                .map(ASTNode::typeof)
                .collect(Collectors.toList());

        return Type.List(Type.mostGeneralType(argsTypes));

//        if (this.list.size() == 0)
//            return Type.List(Type.ANY);
//
//        Type candidateType = this.list.get(0).typeof();
//
//        for (ASTNode x: this.list) {
//            if (! Type.subtype(x.typeof(), candidateType))
//                return Type.List(Type.ANY);
//        }
//
//        return Type.List(candidateType);
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
                " candidateType=" + typeFromElements +
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

            // La lista vuota non ha bisogno di ulteriore typecheck
            if (this.list.size() == 0)
                return;

            // ES.  l: list list int := [[1,2], 666]
            // Il typeFromElements è list any, ma il typeAssigned è list list int
            // Se typeFromElements <= typeAssigned ok, ma viceversa no
            if (! Type.subtype(this.typeFromElements, this.assignedType)) {
                throw new TypeCheckerFail(
                        "Calculated type is not a subtype of declared array type",
                        this, this.assignedType, this.typeFromElements
                );
            }

            this.list.forEach(ASTNode::typecheck);
        }
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitMyArray(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitMyArray(this);
    }

    ////////////////////////////////


    public void setList(List<ASTNode> list) {
        this.list = list;
    }

    public void setTypeFromElements(Type typeFromElements) {
        this.typeFromElements = typeFromElements;
    }

    public List<ASTNode> getList() {
        return list;
    }
}
