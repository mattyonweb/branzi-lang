package ASTnodes.ASTvisitors;

import ASTnodes.Function;
import ASTnodes.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitorFindAllFuncDef extends ASTVisitor {
    List<Function> funcDefs = new ArrayList<>();

    @Override
    public void visitFunction(Function f) {
        funcDefs.add(f);
    }

    public List<Function> getFuncDefs() {
        return funcDefs;
    }

    @Override
    public String toString() {
        return "VisitorFindAllFuncDef{}";
    }
}
