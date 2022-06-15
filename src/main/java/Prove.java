import ASTnodes.ASTNode;
import generated.BranziBaseVisitor;
import generated.BranziLexer;
import generated.BranziParser;
import generated.BranziVisitor;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class Prove {
    public static void main(String[] ss) throws IOException {
        BranziLexer lexer = new BranziLexer(new ANTLRFileStream("static/test1.branzi"));
        BranziParser parser = new BranziParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        BranziVisitor visitor = new BranziMyVisitor();

        ASTNode visit = (ASTNode) visitor.visit(tree);

        System.out.println(visit);
    }
}
