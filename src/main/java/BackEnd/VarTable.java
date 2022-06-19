package BackEnd;

import ASTnodes.Identifier;

import java.util.HashMap;
import java.util.List;

public class VarTable extends HashMap<Identifier, Integer> {
    public VarTable(List<Identifier> funcArgs) {
        funcArgs.forEach(this::bind);
    }

    public Integer bind(Identifier key) {
        return super.put(key, this.keySet().size());
    }
}
