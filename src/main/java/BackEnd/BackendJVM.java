package BackEnd;

import ASTnodes.*;
import ASTnodes.ASTvisitors.VisitorFindAllFuncDef;
import FrontEnd.IR.IRGenerator;
import FrontEnd.IR.IRInstruction;
import FrontEnd.IR.JVMInstruction;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
    final String className;

    public BackendJVM(String className, Path directory) {
        this.className = className;

        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(
                V1_8, ACC_PUBLIC + ACC_SUPER, className,
                null, "java/lang/Object", null
        );
        filename = Paths.get(String.valueOf(directory), className + ".class");
    }

    public void writeToFile(ASTNode root) throws IOException {
//        this.addStandardConstructor();

        VisitorFindAllFuncDef visitorFunc = new VisitorFindAllFuncDef();
        root.astvisit(visitorFunc);
        List<Function> funcdefs = visitorFunc.getFuncDefs();

        for (Function func: funcdefs) {
            if (func.getFuncId().getId().equals("main"))
                this.addMainMethod(func, funcdefs);
            else
                this.addMethod(func, funcdefs);
        }

//        this.addMainMethod(root, funcdefs);
        this.cw.visitEnd();

        FileOutputStream fos = new FileOutputStream(String.valueOf(this.filename));
        fos.write(cw.toByteArray());
    }

    private void addMethod(Function func, List<Function> funcdefs) {
        mv = cw.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                func.getFuncId().getId(),
                JVMInstruction.convertType(func.getFuncType()),
                null, null
        );

        compileFuncAST(func.getBody(), func.getFuncArgs(), funcdefs);

    }

    private void addMainMethod(Function func, List<Function> funcdefs) {
        mv = cw.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                "([Ljava/lang/String;)V", null, null
        );

        compileFuncAST(
                func.getBody(),
                List.of(new Identifier("cli_args", Type.List(Type.STRING))),
                funcdefs
        );
    }

    private void compileFuncAST(
            ASTNode root, List<Identifier> args, List<Function> topmostFuncs
    ) {
        mv.visitCode();

        IRGenerator irGenerator = new IRGenerator(this.className, topmostFuncs);
        root.astvisit(irGenerator);

        VarTable vt = new VarTable(args);

        this.compileInstructions(
                irGenerator.getInstructions(),
                mv, vt
        );

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    ///////////////////

    private void compileInstructions(List<IRInstruction> instructions, MethodVisitor mv, VarTable vt) {
        for (IRInstruction ir: instructions) {
            ir.compile(vt, mv);
        }
    }

}
