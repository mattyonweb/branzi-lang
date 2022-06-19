package ASTnodes.ASTvisitors;

import ASTnodes.*;
import ASTnodes.Number;

import java.util.List;

public class OptimizerMathExpr extends ASTModifier {
    List<String> canSimplify = List.of("+", "-", "*", "/");
    List<String> canSimplifyBool = List.of("and", "or", "xor");
    List<String> canSimplifyComparison = List.of("==", "!=");

    @Override
    public ASTNode visitBinOp(BinOp oldBo) {
        BinOp bo = (BinOp) super.visitBinOp(oldBo);

        ASTNode optimizedMath = simplifyArithmetic(bo);
        if (optimizedMath!= null)
            return optimizedMath;

        ASTNode optimizedBool = simplifyBoolean(bo);
        if (optimizedBool != null)
            return optimizedBool;

        ASTNode optimizedCmp = simplifyComparison(bo);
        if (optimizedCmp != null)
            return optimizedCmp;

        return bo;
    }

    private Bool simplifyComparison(BinOp bo) {
        if (bo.getArg1() instanceof Number && bo.getArg2() instanceof Number && canSimplifyComparison.contains(bo.getOp()))  {
            boolean result;
            Integer x1 = ((Number) bo.getArg1()).getN();
            Integer x2 = ((Number) bo.getArg2()).getN();

            switch(bo.getOp()) {
                case "==": result = x1.equals(x2); break;
                case "!=": result = !(x1.equals(x2)); break;
                default: return null;
            }
            return new Bool(result);
        }
        return null;
    }

    private Bool simplifyBoolean(BinOp bo) {
        if (bo.getArg1() instanceof Bool && bo.getArg2() instanceof Bool && canSimplifyBool.contains(bo.getOp()))  {
            boolean result;
            boolean x1 = ((Bool) bo.getArg1()).getB();
            boolean x2 = ((Bool) bo.getArg2()).getB();

            switch(bo.getOp()) {
                case "and": result = x1 && x2; break;
                case "or": result = x1 || x2; break;
                case "xor": result = x1 ^ x2; break;
                default: return null;
            }
            return new Bool(result);
        }
        return null;
    }

    private Number simplifyArithmetic(BinOp bo) {
        if (bo.getArg1() instanceof Number && bo.getArg2() instanceof Number && canSimplify.contains(bo.getOp()))  {
            Integer result;
            Integer x1 = ((Number) bo.getArg1()).getN();
            Integer x2 = ((Number) bo.getArg2()).getN();

            switch(bo.getOp()) {
                case "+": result = x1 + x2; break;
                case "-": result = x1 - x2; break;
                case "*": result = x1 * x2; break;
                case "/": result = x1 / x2; break;
                default: return null;
            }
            return new Number(result);
        }
        return null;
    }
}
