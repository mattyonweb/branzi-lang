package ASTnodes;

import java.util.List;

public class Program extends ASTNode {
    private List<ASTNode> units;

    public Program(List<ASTNode> units) {
        this.units = units;
    }

    @Override
    public Type typeof() {
        return null;
    }

    @Override
    public void typecheck() throws TypeCheckerFail {
        for (ASTNode x: units) {
            x.typecheck();
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Program{");
        for (ASTNode n: units) {
            s.append("\n").append(n).append("\n");
        }
        return s + "}";
    }
}
