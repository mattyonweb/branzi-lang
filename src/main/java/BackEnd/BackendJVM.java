package BackEnd;

import ASTnodes.*;
import ASTnodes.ASTvisitors.ASTVisitor;
import ASTnodes.Number;
import FrontEnd.IR.IRGenerator;
import FrontEnd.IR.IRInstruction;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;


public class BackendJVM {
    final ClassWriter cw;
    MethodVisitor mv;
    final Path filename;


    public BackendJVM(String className, Path directory) {
        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(
                V1_8, ACC_PUBLIC + ACC_SUPER, className,
                null, "java/lang/Object", null
        );
        filename = Paths.get(String.valueOf(directory), className + ".class");
    }

    public void writeToFile(ASTNode root) throws IOException {
//        this.addStandardConstructor();
        this.addMainMethod(root);
        this.cw.visitEnd();
        FileOutputStream fos = new FileOutputStream(String.valueOf(this.filename));
        fos.write(cw.toByteArray());
    }

    private void addMainMethod(ASTNode root) {
        mv = cw.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                "([Ljava/lang/String;)V", null, null
        );
        mv.visitCode();

        IRGenerator irGenerator = new IRGenerator();
        root.astvisit(irGenerator);

        VarTable vt = new VarTable(List.of(new Identifier("param0")));

        this.compileInstructions(
                irGenerator.getInstructions(),
                mv, vt
        );

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();
    }

    ///////////////////

    private void compileInstructions(List<IRInstruction> instructions, MethodVisitor mv, VarTable vt) {


        for (IRInstruction ir: instructions) {
            ir.compile(vt, mv);
        }
    }

}
