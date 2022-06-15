package ASTnodes;

public class Number extends ASTNode {
    private final Integer n;

    public Number(Integer n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Number{" +
                "n=" + n +
                '}';
    }
}
