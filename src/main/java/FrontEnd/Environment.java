package FrontEnd;

import ASTnodes.ASTNode;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Environment top;
    private final Map<String, ASTNode> dict = new HashMap<>();

    public Environment(Environment top) {
        this.top = top;
    }

    public void put(String name, ASTNode node) {
        dict.put(name, node);
    }

    public ASTNode get(String name) {
        if (this.dict.containsKey(name))
           return dict.get(name);
        if (top != null)
            return top.get(name);
        return null;
    }

    public Environment outer() {
        return this.top;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "dict=" + dict +
                '}';
    }
}
