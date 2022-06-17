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
        fail();
    }

    @BeforeEach
    void setUp() {
        frontEnd = new FrontEnd();
    }

    @Test
    void testFuncBase1() throws IOException, TypeCheckerFail {
        typechecks(
          "function foo : (int -> bool -> void) (a, b) { c: int := 2; return;}"
        );
    }

    @Test
    void testFuncBase2() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> list int -> void) (a, b) { return; }"
        );
    }

    @Test
    void testFuncBase3() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : void () {return; }"
        );
    }


    @Test
    void testFuncListArguments() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (list any -> list (list bool) -> void) (a, b) {return;}"
        );
    }

    @Test
    void testFuncMismatch() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (int -> int -> int) (a) { return 1;}"
        );
    }

    @Test
    void testFuncMismatch1() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int (a) { return 1;}"
        );
    }

    @Test
    void testFuncMismatch2() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int (a, b) {return 1;}"
        );
    }

    @Test
    void testFuncMismatch3() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (list bool -> int) (a) {return a;}"
        );
    }

    @Test
    void testBodyTypes() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> void) (x) { a : int := x + 1; return; }"
        );
    }

    @Test
    void testBodyTypesMismatch() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (int -> void) (x) { a : bool := x; return; }"
        );
    }

    @Test
    void testBodyTypesNoMismatch() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> bool -> list int -> void) (n, b, li) { a : list list int := [li]; return;}"
        );
    }

    @Test
    void testFunctions() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (list (list int) -> void) (ll) " +
                        "{c: list (list (list int)) := [ll]; return;}"
        );
    }


    @Test
    void testReturn1() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : int () { return 1; }"
        );
    }
    @Test
    void testReturn1Expression() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : int () { return (3*4-1); }"
        );
    }
    @Test
    void testReturn2() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : int () { a: int := 1; return a; }"
        );
    }
    @Test
    void testReturnArgument() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> int) (x) { return x; }"
        );
    }
    @Test
    void testReturnArgument2() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : (int -> bool) (x) { return x; }"
        );
    }

    @Test
    void testReturnArgument3() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> int -> list int) (x, y) { return [x,y]; }"
        );
    }

    @Test
    void testReturnArgument4() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> int -> list any) (x, y) { return [x,y]; }"
        );
    }

    @Test
    void testReturnArgument5() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : (int -> void -> list any) (x, y) { l: list int := [1,x,2]; return l; }"
        );
    }
}