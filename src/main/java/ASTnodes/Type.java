package ASTnodes;

import java.util.Arrays;
import java.util.List;

public class Type extends ASTNode {
    public static Type INT = new Type("int");
    public static Type BOOL = new Type("bool");
    public static Type VOID = new Type("void");
    public static Type ANY = new Type("any");
    public static Type TYPE = new Type("type");
    public static Type TBD = new Type("tbd");

    /////////////////////////////////

    private String type;
    private final List<Type> parameters;

    public Type(String type) {
        this.type = type;
        this.parameters = Arrays.asList();
    }

    public Type(String type, List<Type> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public Type(String type, Type ... parameters) {
        this.type = type;
        this.parameters = Arrays.asList(parameters);
    }

    //////////////////////////////////

    @Override
    public Type typeof() {
        return Type.TYPE;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // Nothing to do (?)
    }

    public boolean isCompund() {
        return this.parameters.size() > 0;
    }
    public boolean isFunction() { return this.type.equals("function"); }
    //////////////////////////////////

    public static boolean subtype(Type t1, Type t2) {
        // Is t1 <= t2?

        // t <= Any, for all t
        if (t2.type.equals("any")) { // NON cambiare con t2.equals(Type.ANY). Non va, e fallisce silenziosamente
            return true;
        }

        if (t1.equals(Type.TBD) || t2.equals(Type.TBD)) {
            return false;
        }

        // Follows: type equality
        if (!t1.type.equals(t2.type))
            return false;

        // Se i tipi base combaciano e sono entrambi tipi semplici
        if (t1.parameters == null && t2.parameters == null)
            return true;

        // Se uno è un tipo semplice ma l'altro è composto
        if ((t1.parameters == null) ^ (t2.parameters == null))
            return false;

        // Se hanno numero diverso di type parameters
        if (t1.parameters.size() != t2.parameters.size())
            return false;

        for (int i = 0; i < t1.parameters.size(); i++) {
            if (!Type.subtype(t1.parameters.get(i), t2.parameters.get(i)))
                return false;
        }

        return true;
    }

    ///////////////////////////////////

    public static Type Function(Type ... params) {
        // Helper method
        return new Type("function", params);
    }

    public static Type Function(List<Type> params) {
        // Helper method
        return new Type("function", params);
    }

    public static Type List(Type param) {
        return new Type("list", param);
    }

    /////////////////////////////7

    public List<Type> getParameters() {
        return parameters;
    }

    public Type getParameter(int i) {
        return this.getParameters().get(i);
    }

    public Type getLastParameter() {
        return this.getParameter(this.getParameters().size()-1);
    }

    public String getTypeName() {
        return this.type;
    }

    @Override
    public String toString() {
        String s = type;

        if (parameters == null || parameters.size() == 0)
            return s;

        return s + parameters;
    }
}
