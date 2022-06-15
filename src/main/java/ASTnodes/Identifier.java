package ASTnodes;

public class Identifier extends ASTNode {
    private final String id;
    private Type type = null;

    public Identifier(String id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
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
}
