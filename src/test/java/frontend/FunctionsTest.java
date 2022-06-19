package frontend;

import ASTnodes.ASTNode;
import ASTnodes.TypeCheckerFail;
import FrontEnd.FrontEnd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import java.io.IOException;
import static frontend.Utils.typechecks;
import static frontend.Utils.typecheck_fail;

import static org.junit.jupiter.api.Assertions.fail;

class FunctionsTest {
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

    @Test
    void testFuncNameScope() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : void () {return;}" +
                        "function bar : void () {return foo();}"
        );
    }

    @Test
    void testFuncNameScope2() throws IOException, TypeCheckerFail {
        typechecks(
                "function foo : int -> bool -> int (x,b) {return x;}" +
                        "function bar : int () {return foo(2, true);}"
        );
    }

    @Test
    void testFuncNameScope3() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int -> bool -> int (x,b) {return x;}" +
                        "function bar : int () {x: list int := foo(2, true); return 0;}"
        );
    }

    @Test
    void testFuncNameScope4Wrongs() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int -> bool -> int (x,b) {return x;}" +
                        "function bar : int () {return foo(2); }"
        );

        typecheck_fail(
                "function foo : int -> bool -> int (x,b) {return x;}" +
                        "function bar : int () {return foo(2, true, 3); }"
        );

        typecheck_fail(
                "function foo : int -> bool -> int (x,b) {return x;}" +
                        "function bar : int () {return foo(); }"
        );
    }

    @Test
    void testFuncNameScopeOverlap() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int -> bool (x) {return false;}" +
                        "function bar : int () {foo: int := 666; return foo(3);}"
        );
    }

    @Test
    void testFuncRecursion() throws IOException, TypeCheckerFail {
        typechecks(
                "function wrongFact : int -> int (n) {return wrongFact(n-1);}"
        );
    }

    @Test
    void testFuncRecursionWrong() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function reallyWrongFact : int -> int (n) {return reallyWrongFact(n, n-1);}"
        );
    }

    @Test
    void testFuncManyReturns() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int () { if (false) {return true;} while (1==2){return true;} return false; }"
        );
    }

    @Test
    void testFuncManyReturnsFail() throws IOException, TypeCheckerFail {
        typecheck_fail(
                "function foo : int () { if (false) {return true;} while (1==2){return 3;} return false; }"
        );
    }
}