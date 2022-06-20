package ASTnodes.ASTvisitors;

import ASTnodes.*;

import java.util.Stack;

/**
 * Removes, in every _BLOCK_ inside a function, all the instructions
 * after the first `Return`.
 */
public class OptimizerRemoveDeadCodeInFunction extends ASTModifier {
    Stack<Boolean> foundFirstReturn = new Stack<>();

    public OptimizerRemoveDeadCodeInFunction() {
        foundFirstReturn.push(false);
    }

    @Override
    public ASTNode visitIf(If i) {
        foundFirstReturn.push(false);
        i.setCode(i.getCode().astmodify(this));
        foundFirstReturn.pop();
        return i;
    }

    @Override
    public ASTNode visitWhile(While w) {
        foundFirstReturn.push(false);
        w.setBlock(w.getBlock().astmodify(this));
        foundFirstReturn.pop();
        return w;
    }

    @Override
    public ASTNode visitReturn(Return r) {
        if (foundFirstReturn.peek())
            return new NoOp();

        foundFirstReturn.pop();
        foundFirstReturn.push(true);
        return r;
    }

    @Override
    public ASTNode visitSequence(Sequence s) {
        // Scan all the instructions in the sequence until you find the first return
        // then discard evereything that comes after
        for (int i = 0; i < s.getNodes().size(); i++) {
            ASTNode newElem = s.getNodes().get(i).astmodify(this);

            s.getNodes().set(i, newElem);

            if (foundFirstReturn.peek()) {
                s.shorten(i + 1);
                return s;
            }
        }
        return s;
    }

    @Override
    public String toString() {
        return "OptimizerRemoveDeadCodeInFunction{}";
    }
}
