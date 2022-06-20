package backend;

import ASTnodes.ASTNode;
import BackEnd.BackendJVM;
import FrontEnd.FrontEnd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackendJVMTest {
    ExecuteBytecode bytecodeExecuter;
    final String classpath = "/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output";

    @BeforeEach
    void setUp() {
        bytecodeExecuter = new ExecuteBytecode(classpath);
    }

    private static String getMethodName() {
        // Returns the name of the method that is currently executing (!)
        return (Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    public void compile(String program, String className) throws IOException {
        // Obtain AST
        ASTNode root       = new FrontEnd().getOptimizedAST(program);

        // Compile to Bytecode and write to file
        BackendJVM backend = new BackendJVM(className, Path.of(this.classpath));
        backend.writeToFile(root);
    }

    void test(String classname, List<String> expected) throws IOException, InterruptedException {
        // Matches the output of the executed .class file with the expected
        List<String> actual = bytecodeExecuter.run(classname);
        assertLinesMatch(expected, actual);
    }



    ////////////////////////////////////////

    @Test
    void TestSimpleConditionals() throws IOException, InterruptedException {
        compile(
                "function main : list string -> void (ss) {" +
                        "{x: int := 44; y: int := 66; if (y < x) {print(444);} print(666); }",
                getMethodName()
        );

        test(getMethodName(), List.of("666"));
    }


    @Test
    void TestWhileAndPrint() throws IOException, InterruptedException {
        compile("function main : list string -> void (ss) {" +
                "{x: int := 0; while (x < 10) {x = x + 1; print(x);} print(x);}",
                getMethodName()
        );

        List<String> expected = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            expected.add(String.valueOf(i));
        } expected.add("10");

        test(getMethodName(), expected);
    }


    @Test
    void TestComplexConditionWhile() throws IOException, InterruptedException {
        compile("function main : list string -> void (ss) {" +
                "x: int := 1; while (x < 10 and x+6 <= 10) {x = x + 3; print(x);} print(x);}",
                getMethodName()
        );

        test(getMethodName(), List.of("4", "7", "7"));
    }


    @Test
    void TestMultipleFunctions2() throws IOException, InterruptedException {
        compile("function foo : (int -> bool -> int) (x, b) { if (b) {return x;} return 665+1;}" +
                                "function main : (list string -> void) (ss) { print(foo(10, false)); return;}",
                        getMethodName());

        test(getMethodName(), List.of("666"));
    }


    @Test
    void TestFactorial() throws IOException, InterruptedException {
        compile("function fact : (int -> int) (n) { if ( n <= 1) {return 1;} return n * fact(n-1);}" +
                        "function main : (list string -> void) (ss) { print(fact(10)); return;}",
                getMethodName());

        test(getMethodName(), List.of("3628800"));
    }
}