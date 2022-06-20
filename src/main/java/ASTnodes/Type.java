package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

import java.util.Arrays;
import java.util.List;

public class Type extends ASTNode {
    public static Type INT = new Type("int");
    public static Type BOOL = new Type("bool");
    public static Type VOID = new Type("void");
    public static Type STRING = new Type("string"); // TODO
    public static Type ANY = new Type("any");
    public static Type TYPE = new Type("type");
    public static Type TBD = new Type("tbd");

    /////////////////////////////////

    private String typeName;
    private List<Type> parameters;

    public Type(String typeName) {
        this.typeName = typeName;
        this.parameters = Arrays.asList();
    }

    public Type(String typeName, List<Type> parameters) {
        this.typeName = typeName;
        this.parameters = parameters;
    }

    public Type(String typeName, Type ... parameters) {
        this.typeName = typeName;
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

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitType(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitType(this);
    }

    public boolean isCompund() {
        return this.parameters.size() > 0;
    }
    public boolean isFunction() { return this.typeName.equals("function"); }
    public boolean isList() {return this.typeName.equals("list"); }
    //////////////////////////////////

    public static Type mostGeneralType(List<Type> types) {
          if (types.size() == 0)
              return Type.TBD;

          Type candidate = types.get(0);
          for (Type t: types) {
              if (Type.subtype(t, candidate))       // es. TBD <= INT  -->  candidate = INT
                  continue;
              else if (Type.subtype(candidate, t))  // es. INT <= ANY  -->  new_candidate = ANY
                  candidate = t;
              else  // es. List INT e BOOL sono incomparabili => type error => MGT è ANY
                  candidate = Type.ANY;
          }

          return candidate;
    }

    public static boolean subtype(Type t1, Type t2) {
        // Is t1 <= t2?

        // t <= Any, for all t
        if (t2.typeName.equals("any")) { // NON cambiare con t2.equals(Type.ANY). Non va, e fallisce silenziosamente
            return true;
        }

        if (t1.equals(Type.TBD)) {
            return true;
        }

        if (t2.equals(Type.TBD)) {
            return false;
        }

        // Follows: type equality
        if (!t1.typeName.equals(t2.typeName))
            return false;

        // Se i tipi base combaciano e sono entrambi tipi semplici
        if (t1.parameters.size() == 0 && t2.parameters.size() == 0)
            return true;

        // Se uno è un tipo semplice ma l'altro è composto
        if ((t1.parameters.size() == 0) ^ (t2.parameters.size() == 0))
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

    public static boolean equality(Type t1, Type t2) {
        return subtype(t1, t2) && subtype(t2, t1);
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
        return this.typeName;
    }

    @Override
    public String toString() {
        String s = typeName;

        if (parameters == null || parameters.size() == 0)
            return s;

        return s + parameters;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setParameters(List<Type> parameters) {
        this.parameters = parameters;
    }
}
