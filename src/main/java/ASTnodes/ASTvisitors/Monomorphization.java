package ASTnodes.ASTvisitors;

import ASTnodes.ASTNode;
import ASTnodes.Funcall;
import ASTnodes.Type;

public class Monomorphization extends ASTModifier {
    @Override
    public ASTNode visitFuncall(Funcall c) {
        Type functype = c.getFuncId().typeof();

        for (int i = 0; i < c.getArgs().size(); i++) {
            if (!Type.equality(functype.getParameter(i), Type.ANY))
                continue;

            functype.getParameters().set(i, c.getArgs().get(i).typeof());
        }

        return c;
    }

    @Override
    public String toString() {
        return "Monomorphization{}";
    }
}
