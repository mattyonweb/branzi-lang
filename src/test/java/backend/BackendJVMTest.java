package backend;

import ASTnodes.ASTNode;
import BackEnd.BackendJVM;
import FrontEnd.FrontEnd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BackendJVMTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void serializeToBytes() throws IOException {
        ASTNode root = new FrontEnd().getOptimizedAST(
                "{x: int := 100; y: int := x-1; z: int := 2*y-x*(3+1) / 7; print(z); }"
        );

        BackendJVM prova = new BackendJVM(
                "Prova",
                Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output")
        );

        prova.writeToFile(root);
    }

    @Test
    void serializeToBytes2() throws IOException {
        ASTNode root = new FrontEnd().getOptimizedAST(
                "{x: int := 44; y: int := 66; if (y < x) {print(444);} print(666); }"
        );

        BackendJVM prova = new BackendJVM(
                "Prova",
                Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output")
        );

        prova.writeToFile(root);
    }

    @Test
    void serializeToBytes3() throws IOException {
        ASTNode root = new FrontEnd().getOptimizedAST(
                "{x: int := 0; while (x < 10) {x = x + 1; print(x);} print(x);}"
        );

        BackendJVM prova = new BackendJVM(
                "Prova",
                Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output")
        );

        prova.writeToFile(root);
    }

    @Test
    void serializeToBytes4() throws IOException {
        ASTNode root = new FrontEnd().getOptimizedAST(
                "{x: int := 1; while (x < 10 and x+5 <= 10) {x = x + 3; print(x);} print(x);}"
        );

        BackendJVM prova = new BackendJVM(
                "Prova",
                Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output")
        );

        prova.writeToFile(root);
    }

    @Test
    void serializeToBytes5() throws IOException {
        ASTNode root = new FrontEnd().getOptimizedAST(
                "function foo : (int -> bool -> int) (x, b) { return x; }" +
                        "function main : (list string -> void) (ss) { foo(10, true); return;}"
        );

        BackendJVM prova = new BackendJVM(
                "Prova",
                Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output")
        );

        prova.writeToFile(root);
    }

    @Test
    void serializeToBytes6() throws IOException {
        ASTNode root = new FrontEnd().getOptimizedAST(
                "function foo : (int -> bool -> int) (x, b) { if (b) {return x;} return 665+1;}" +
                        "function main : (list string -> void) (ss) { print(foo(10, true)); return;}"
        );

        BackendJVM prova = new BackendJVM(
                "Prova",
                Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output")
        );

        prova.writeToFile(root);
    }
}