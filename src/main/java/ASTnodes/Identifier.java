package ASTnodes;

public class Identifier extends ASTNode {
    private final String id;
    private final Type type;

    public Identifier(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public Identifier(String id) {
        this.id = id;
        this.type = null;
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
