import ASTnodes.ASTNode;
import ASTnodes.TypeCheckerFail;
import FrontEnd.FrontEnd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

class FrontEndTest {
    FrontEnd frontEnd;

    void typechecks(String s) throws IOException, TypeCheckerFail {
        ASTNode node = frontEnd.ast_of_string(s);
        System.out.println(node);
        node.typecheck();
    }

    void typecheck_fail(String s) {
        try {
            typechecks(s);
        } catch (TypeCheckerFail e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @BeforeEach
    void setUp() {
        frontEnd = new FrontEnd();
    }

    @Test
    void testFuncBase1() throws IOException, TypeCheckerFail {
        typechecks(
          "function foo : (int -> bool -> int) (a, b) {c : int := 2;}"
        );
    }

    @Test
    void testFuncBase2() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> bool -> list int) (a, b) {}"
        );
    }

    @Test
    void testFuncBase3() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : int () {}"
        );
    }


    @Test
    void testFuncListArguments() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (list any -> list (list bool) -> int) (a, b) {}"
        );
    }

    @Test
    void testFuncMismatch() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (int -> int -> int) (a) {}"
        );
    }

    @Test
    void testFuncMismatch1() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int (a) {}"
        );
    }

    @Test
    void testFuncMismatch2() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int (a, b) {}"
        );
    }

    @Test
    void testFuncMismatch3() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (list bool -> int) (a) {}"
        );
    }

    @Test
    void testBodyTypes() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> int) (x) { a : int := x + 1; }"
        );
    }

    @Test
    void testBodyTypesMismatch() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (int -> bool) (x) { a : bool := x; }"
        );
    }

    @Test
    void testBodyTypesNoMismatch() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> bool -> list int -> list list int) (n, b, li) { a : list list int := [li]; }"
        );
    }

    @Test
    void testFunctions() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (list (list int) -> list bool) (ll) " +
                        "{c: list (list (list int)) := [ll]; }"
        );
    }



}