package frontend;

import ASTnodes.ASTNode;
import FrontEnd.FrontEnd;
import FrontEnd.IR.IRGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class IRGeneratorTest {

    @Test
    public void example() throws IOException {
        ASTNode n = new FrontEnd().getOptimizedAST("{x: int := 100; y: int := x-1; z: int := 2*y-x*(3+1) / 7; }");
        System.out.println(n);

        IRGenerator ir = new IRGenerator();
        n.astvisit(ir);

        ir.printInstructions();
    }

    @Test
    public void exampleIf() throws IOException {
        ASTNode n = new FrontEnd().getOptimizedAST("{x: bool := false; if (x or true) {y1: int := 1; y2: int := 2;} y3:int := 77; }");
        System.out.println(n);
        System.out.println("\n\n");

        IRGenerator ir = new IRGenerator();
        n.astvisit(ir);

        ir.printInstructions();
    }
}