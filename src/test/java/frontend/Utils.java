package frontend;

import ASTnodes.ASTNode;
import ASTnodes.TypeCheckerFail;
import FrontEnd.FrontEnd;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class Utils {
    static FrontEnd frontEnd = new FrontEnd();

    public static ASTNode typechecks(String s) throws IOException, TypeCheckerFail {
        ASTNode node = frontEnd.ast_of_string(s);
        System.out.println(node);
        node.typecheck();
        return node;
    }

    static void typecheck_fail(String s) {
        try {
            typechecks(s);
        } catch (TypeCheckerFail e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        fail();
    }
}
