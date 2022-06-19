package ASTnodes;

import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.ASTVisitor;

public class Identifier extends ASTNode {
    private String id;
    private Type type = Type.TBD;

    public Identifier(String id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public Type typeof() {
        return this.type;
    }

    @Override
    public String toString() {
        Integer hash = this.hashCode();
        String hashS = hash.toString();
        String lastHash = hashS.substring(hashS.length()-5);

        return "Identifier{" +
                "id='" + id + lastHash + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        // niente da fare qui
    }

    @Override
    public void astvisit(ASTVisitor visitor) {
        visitor.visitIdentifier(this);
    }

    @Override
    public ASTNode astmodify(ASTModifier visitor) {
        return visitor.visitIdentifier(this);
    }
}
