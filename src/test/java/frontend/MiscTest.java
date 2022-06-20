package frontend;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static frontend.Utils.typechecks;
import static frontend.Utils.typecheck_fail;

public class MiscTest {
    @Test
    public void whileTest() throws IOException {
        typechecks(
                "function foo : (bool -> int) (b) {" +
                        "while ((true and b) != (false or (false and b))) { } " +
                        "return 1;}"
        );
        typechecks(
                "function foo : (bool -> int) (b) {" +
                        "while ((true and b) != (false or (false and b))) { b=true; } " +
                        "return 1;}"
        );
    }

    @Test
    public void complexBool() throws IOException {
        typechecks(
                "{ b: bool := true and ((4+7) == 10); }"
        );
    }

    @Test
    public void printTest() throws IOException {
        typechecks("{ print(10); }");
        typechecks("{ print(3*(45-15)+1); }");
        typechecks("{ print(true or (false and true)); }");
        typechecks("{ x: int := 0; print(1+x); }");
        typechecks("{ l: list bool := [true, false]; print(l); }");
    }
}