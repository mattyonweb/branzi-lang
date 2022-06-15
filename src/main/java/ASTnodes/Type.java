package ASTnodes;

import java.util.ArrayList;
import java.util.List;

public class Type extends ASTNode {
    private String type;
    private final List<Type> parameters;

    public Type(String type) {
        this.type = type;
        this.parameters = null;
    }

    public Type(String type, List<Type> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String s = "Type{" +
                "type='" + type + '\'';

        if (parameters == null)
            return s + "}";

        return s + ", parameters=" + parameters + '}';
    }
}
