package frontend;

import ASTnodes.TypeCheckerFail;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static frontend.Utils.typechecks;
import static frontend.Utils.typecheck_fail;

public class ArrayTest {
    @Test
    void testArrayBase() throws IOException, TypeCheckerFail {
        typechecks("{l: list int := [1,2,3];}");
        typechecks("{l: list int := [1];}");
        typechecks("{l: list int := [];}");
    }

    @Test
    void testNestedList() throws IOException, TypeCheckerFail {
        typechecks("{l: list list int := [[1,2],[3]];}");
        typechecks("{l: list list list int := [ [[1,2],[3]], []];}");
        typechecks("{l: list list list int := [];}");
        typechecks("{l: list list list int := [[],[],[]];}");
        typechecks("{l: list list list int := [[[]],[[]],[[]]];}");
        typechecks("{l: list any := [1,true,l];}");
    }

    @Test
    void testNestedListWrong() throws IOException, TypeCheckerFail {
        typecheck_fail("{l: list list int := [[1,2],666];}");
        typecheck_fail("{l: list list list int := [[[1,2],[3]], 666];}");
        typecheck_fail("{l: list list list int := [[],3,[]];}");
        typecheck_fail("{l: list list list int := [[],[[[]]],[]];}");
    }

    @Test
    void testListAccess() throws IOException, TypeCheckerFail {
        typechecks("{l: list list bool := [[true]];  x: list bool := l[1];}");
        typecheck_fail("{l: list list bool := [[true]];  x: bool := l[1];}");
    }

    @Test
    void testListAccessWrong() throws IOException, TypeCheckerFail {
        typecheck_fail("{l: list bool := [true,false];  x: int  := l[0];}");
        typecheck_fail("{l: list bool := [true,false];  b: bool := l[false];}");
    }

    @Test
    void testListUpdate() throws IOException, TypeCheckerFail {
        typechecks("{l: list bool := [true,false];  l[0] = false;}");
        typechecks("{l: list bool := [true,false];  l[3*(2+5)] = false;}");
        typechecks("{l: list bool := [true,false];  idx: int := 1-idx; l[idx] = false;}");
        typechecks("{l: list bool := [true,false];  idx: int := 1-idx; l[idx] = true and false;}");
        typechecks("{l: list bool := [true,false];  idx: int := 1-idx; newval: bool := true and false; l[idx] = newval;}");
    }

    @Test
    void testListUpdate2Func() throws IOException, TypeCheckerFail {
        typechecks(
                "function subprime : (int -> list int -> list int) (x,l) { l[0] = x; return l; }" +
                        "{ l: list int := [1,3,5,7]; l = <¹subprime(666, l); }"
        );
    }

    @Test
    void testListUpdateFail() throws IOException, TypeCheckerFail {
        typecheck_fail("{l: list list bool := [[true,false]];  l[0] = false;}");
        typecheck_fail("{l: list bool := [true,false];  l[0] = [false];}");
    }
}
