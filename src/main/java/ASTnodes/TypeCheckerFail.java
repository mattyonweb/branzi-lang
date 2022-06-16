package ASTnodes;

public class TypeCheckerFail extends Exception {
    public TypeCheckerFail() {
    }

    public TypeCheckerFail(String message) {
        super(message);
    }

    public TypeCheckerFail(String comment, ASTNode culprit, Type expected, Type obtained) {
        super(comment + "\n\tin: " + culprit + "\nExpected: " + expected + "\nObtained: " + obtained);
    }

    public static void verify(String comment, ASTNode culprit, Type expected, Type obtained) throws TypeCheckerFail {
        if (!Type.subtype(obtained, expected)) {
            throw new TypeCheckerFail(comment, culprit, expected, obtained);
        }
    }
}
