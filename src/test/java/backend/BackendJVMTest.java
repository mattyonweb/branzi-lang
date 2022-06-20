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
}