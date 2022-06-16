package FrontEnd;

import ASTnodes.ASTNode;
import ASTnodes.TypeCheckerFail;
import generated.BranziLexer;
import generated.BranziParser;
import generated.BranziVisitor;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FrontEnd {
    public static void main(String[] ss) throws IOException, TypeCheckerFail {
        FrontEnd frontEnd = new FrontEnd();
        ASTNode visit = frontEnd.ast_of("static/test1.branzi");
//        ASTNode visit = frontEnd.ast_of(ss[1]);
        System.out.println(visit);

        visit.typecheck();
    }

    public ASTNode ast_of(String fpath) throws IOException {
        BranziLexer lexer = new BranziLexer(new ANTLRFileStream(fpath));
        BranziParser parser = new BranziParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        BranziVisitor visitor = new BranziMyVisitor();

        return (ASTNode) visitor.visit(tree);
    }

    public ASTNode ast_of_string(String code) throws IOException {
        InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        BranziLexer lexer = new BranziLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));
        BranziParser parser = new BranziParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        BranziVisitor visitor = new BranziMyVisitor();

        return (ASTNode) visitor.visit(tree);
    }
}
