package astnodes;

import ASTnodes.*;
import ASTnodes.ASTvisitors.VisitSimplifyMathExpr;
import ASTnodes.Number;
import FrontEnd.FrontEnd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class VisitSimplifyMathExprTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void visitBinOp() {
        BinOp bo = new BinOp(new Number(3), "+", new Number(6));
        ASTNode optimized = new VisitSimplifyMathExpr().visitBinOp(bo);

        assertTrue(optimized instanceof Number);
        assertEquals(9, ((Number) optimized).getN());
    }

    @Test
    void visitBinOp2() {
        BinOp bo = new BinOp(new Number(3), "+", new Number(4));
        BinOp bo2 = new BinOp(bo, "*", new Number(7));

        ASTNode optimized = new VisitSimplifyMathExpr().visitBinOp(bo2);

        System.out.println(optimized);

        assertTrue(optimized instanceof Number);
        assertEquals(49, ((Number) optimized).getN());
    }

    @Test
    void visitBinOp3() throws IOException {
        ASTNode root = new FrontEnd().ast_of_string(
                "{true and (4+7) == 10;}"
        );

        ASTNode optimized = root.astmodify(new VisitSimplifyMathExpr());
        System.out.println(optimized);

        ASTNode result = ((Program) optimized).getUnits().get(0);

        assertTrue(result instanceof Bool);
        assertFalse(((Bool) result).getB());
    }
}