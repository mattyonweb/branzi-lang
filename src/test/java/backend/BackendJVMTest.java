//package backend;
//
//import BackEnd.BackendJVM;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Path;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BackendJVMTest {
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void serializeToBytes() throws IOException {
//        BackendJVM prova = new BackendJVM("Prova", Path.of("/home/groucho/IdeaProjects/SimpleLanguage/src/test/java/backend/output"));
//
//        prova.writeToFile();
//    }
//}