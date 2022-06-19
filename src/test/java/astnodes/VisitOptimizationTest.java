package astnodes;

import ASTnodes.*;
import ASTnodes.ASTvisitors.OptimizerMathExpr;
import ASTnodes.Number;
import FrontEnd.FrontEnd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static frontend.Utils.typechecks;

class VisitOptimizationTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void visitBinOp() {
        BinOp bo = new BinOp(new Number(3), "+", new Number(6));
        ASTNode optimized = new OptimizerMathExpr().visitBinOp(bo);

        assertTrue(optimized instanceof Number);
        assertEquals(9, ((Number) optimized).getN());
    }

    @Test
    void visitBinOp2() {
        BinOp bo = new BinOp(new Number(3), "+", new Number(4));
        BinOp bo2 = new BinOp(bo, "*", new Number(7));

        ASTNode optimized = new OptimizerMathExpr().visitBinOp(bo2);

        System.out.println(optimized);

        assertTrue(optimized instanceof Number);
        assertEquals(49, ((Number) optimized).getN());
    }

    @Test
    void visitBinOp3() throws IOException {
        ASTNode root = new FrontEnd().ast_of_string(
                "{true and (4+7) == 10;}"
        );

        ASTNode optimized = root.astmodify(new OptimizerMathExpr());
        System.out.println(optimized);

        ASTNode result = ((Program) optimized).getUnits().get(0);

        assertTrue(result instanceof Bool);
        assertFalse(((Bool) result).getB());
    }

    @Test
    void visitRemoveUselessReturns() throws IOException {
        ASTNode root = typechecks(
                "function foo : int () {" +
                        "return 444; i: int := 1; true; return 666; }"
        );

        List<ASTNode> bodyNew =
                ((Sequence) ((Function) ((Program) root).getUnits().get(0)).getBody()).getNodes();

        assertEquals(1, bodyNew.size());
        assertTrue(bodyNew.get(0) instanceof Return);
        assertTrue(((Return) bodyNew.get(0)).getExpr() instanceof Number);
        assertEquals(444, ((Number) ((Return) bodyNew.get(0)).getExpr()).getN());
    }

    @Test
    void visitRemoveUselessReturns2() throws IOException {
        ASTNode root = typechecks(
                "function foo : int () {" +
                        "return 444; i: int := 1; true; return bool; }"
        );

        List<ASTNode> bodyNew =
                ((Sequence) ((Function) ((Program) root).getUnits().get(0)).getBody()).getNodes();

        assertEquals(1, bodyNew.size());
        assertTrue(bodyNew.get(0) instanceof Return);
        assertTrue(((Return) bodyNew.get(0)).getExpr() instanceof Number);
        assertEquals(444, ((Number) ((Return) bodyNew.get(0)).getExpr()).getN());
    }

    @Test
    void visitRemoveUselessReturns3() throws IOException {
        ASTNode root = typechecks(
                "function foo : int () {" +
                        "while (true) {return 1; return 2;} return 3; return 4; }"
        );

        System.out.println(root);

        List<ASTNode> bodyNew =
                ((Sequence) ((Function) ((Program) root).getUnits().get(0)).getBody()).getNodes();

        assertEquals(2, bodyNew.size());

        assertTrue(bodyNew.get(0) instanceof While);
        assertTrue(((While) bodyNew.get(0)).getBlock() instanceof Sequence);
        assertEquals(1, ((Sequence) ((While) bodyNew.get(0)).getBlock()).getNodes().size());

        assertTrue(bodyNew.get(1) instanceof Return);
    }
}