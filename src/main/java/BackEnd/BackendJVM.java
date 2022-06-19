//package BackEnd;
//
//import ASTnodes.*;
//import ASTnodes.ASTvisitors.ASTVisitor;
//import ASTnodes.Number;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.MethodVisitor;
//import org.objectweb.asm.Opcodes;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import static org.objectweb.asm.Opcodes.*;
//
//public class BackendJVM extends ASTVisitor {
//    final ClassWriter cw;
//    MethodVisitor mv;
//    final Path filename;
//
//    public BackendJVM(String className, Path directory) {
//        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//        cw.visit(
//                V1_8, ACC_PUBLIC + ACC_SUPER, className,
//                null, "java/lang/Object", null
//        );
//        filename = Paths.get(String.valueOf(directory), className + ".class");
//    }
//
//    public void writeToFile() throws IOException {
////        this.addStandardConstructor();
//        this.addMainMethod();
//        this.cw.visitEnd();
//        FileOutputStream fos = new FileOutputStream(String.valueOf(this.filename));
//        fos.write(cw.toByteArray());
//    }
//
//    private void addMainMethod() {
//        mv = cw.visitMethod(
//                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
//                "([Ljava/lang/String;)V", null, null
//        );
//        mv.visitCode();
//        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mv.visitLdcInsn("Hello world!");
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//        mv.visitInsn(Opcodes.RETURN);
//        mv.visitMaxs(0, 0);
//        mv.visitEnd();
//        cw.visitEnd();
//    }
//
//    ///////////////////
//
//
//    @Override
//    public void visitAssignVar(AssignVar av) {
//        Type varType = av.varId.typeof();
//        if (Type.equality(varType, Type.INT)) {
//            av.value
//        }
//
//        super.visitAssignVar(av);
//    }
//
//    @Override
//    public void visitBool(Bool b) {
//        super.visitBool(b);
//    }
//
//    @Override
//    public void visitFunction(Function f) {
//        super.visitFunction(f);
//    }
//
//    @Override
//    public void visitIdentifier(Identifier id) {
//        super.visitIdentifier(id);
//    }
//
//    @Override
//    public void visitNumber(Number n) {
//        super.visitNumber(n);
//    }
//
//    @Override
//    public void visitProgram(Program p) {
//        super.visitProgram(p);
//    }
//
//    @Override
//    public void visitSequence(Sequence s) {
//        super.visitSequence(s);
//    }
//
//    @Override
//    public void visitType(Type t) {
//        super.visitType(t);
//    }
//}
