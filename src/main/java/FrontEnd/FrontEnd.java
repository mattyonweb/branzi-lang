package FrontEnd;

import ASTnodes.ASTNode;
import ASTnodes.ASTvisitors.ASTModifier;
import ASTnodes.ASTvisitors.Monomorphization;
import ASTnodes.ASTvisitors.OptimizerMathExpr;
import ASTnodes.ASTvisitors.OptimizerRemoveDeadCodeInFunction;
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
import java.util.List;

public class FrontEnd {
    public static void main(String[] ss) throws IOException, TypeCheckerFail {

        FrontEnd frontEnd = new FrontEnd();
        ASTNode visit = frontEnd.ast_of("static/test1.branzi");
//        ASTNode visit = frontEnd.ast_of(ss[1]);
        System.out.println(visit);

        visit.typecheck();
    }

    public ASTNode getOptimizedAST(String s) throws IOException {
        return getAST(
                s, List.of(
                        new OptimizerMathExpr(), new OptimizerRemoveDeadCodeInFunction(),
                        new Monomorphization()
                )
        );
    }

    public ASTNode getAST(String s, List<ASTModifier> optimizations) throws IOException {
        ASTNode ast = this.ast_of_string(s);

        ast.typecheck();

        for (ASTModifier visitor: optimizations) {
            ast = ast.astmodify(visitor);
        }

        return ast;
    }

    private ASTNode optimizeWith(ASTNode ast, ASTModifier visitor) {
        return ast.astmodify(visitor);
    }

    private void typecheck(ASTNode ast) {
        ast.typecheck();
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
