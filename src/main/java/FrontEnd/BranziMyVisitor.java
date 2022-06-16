package FrontEnd;

import ASTnodes.*;
import ASTnodes.Number;
import generated.BranziBaseVisitor;
import generated.BranziParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BranziMyVisitor extends BranziBaseVisitor<ASTNode> {
    Environment env = new Environment(null);

    @Override
    public ASTNode visitProgram(BranziParser.ProgramContext ctx) {
        return new Program(
                ctx.unit().stream().map(this::visit).collect(Collectors.toList())
        );
    }

    @Override
    public ASTNode visitFuncDeclEmptyArgs(BranziParser.FuncDeclEmptyArgsContext ctx) {
        String funcname = ctx.Name.getText();
        Identifier funcId = new Identifier(funcname);

        Type funcType = (Type) this.visit(ctx.type());
        System.out.println(funcType);
        funcId.setType(funcType);

        List<Identifier> funcArgs = new ArrayList<>();

        // Aggiungerò all'ENV il nome di questa stessa funzione, per permettere
        // chiamate ricosrive
        List<Identifier> addToEnv = new ArrayList<>();
        addToEnv.add(funcId);

        return new Function(
                funcId,
                funcType,
                funcArgs,
                this.visitBlockAddingToEnvironment(ctx.block(), addToEnv)
        );
    }

    @Override
    public ASTNode visitFuncDeclNonEmptyArgs(BranziParser.FuncDeclNonEmptyArgsContext ctx) {
        String funcname = ctx.Name.getText();
        Identifier funcId = new Identifier(funcname);

        Type funcType = (Type) this.visit(ctx.type());
        funcId.setType(funcType);

        List<Identifier> funcArgs = new ArrayList<>();
        funcArgs.add(new Identifier(ctx.arg1.getText()));
        funcArgs.addAll(ctx.otherArgs
                .stream()
                .map(a -> new Identifier(a.getText()))
                .collect(Collectors.toList())
        );

        List<Identifier> addToEnv = new ArrayList<>(funcArgs);
        // Aggiungerò all'ENV il nome di questa stessa funzione, per permettere
        // chiamate ricosrive
        addToEnv.add(funcId);

        return new Function(
                funcId,
                funcType,
                funcArgs,
                this.visitBlockAddingToEnvironment(ctx.block(), addToEnv)
        );
    }



    @Override
    public ASTNode visitFunc_type(BranziParser.Func_typeContext ctx) {
        List<Type> types = new ArrayList<>();
        types.add((Type) this.visit(ctx.arg1));
        types.addAll(ctx.otherArgs.stream()
                .map(t -> (Type) (this.visit(t)))
                .collect(Collectors.toList()));

        return Type.Function(types);
    }


    @Override
    public ASTNode visitBlock(BranziParser.BlockContext ctx) {
        return this.visitBlockAddingToEnvironment(
                ctx,
                new ArrayList<>()
        );
    }

    public ASTNode visitBlockAddingToEnvironment(BranziParser.BlockContext ctx,
                                                 List<Identifier> idsToAdd) {
        if (ctx.statement().size() == 0) {
            return Type.VOID; // TODO: non è proprio giustissimo ecco
        }

        env = new Environment(env);
        for (Identifier id: idsToAdd) {
            env.put(id.getId(), id);
        }

        System.out.println(env);

        if (ctx.statement().size() == 1) {
            ASTNode result = this.visit(ctx.statement(0));
            env = env.outer();
            return result;
        }

        ASTNode fstNode = this.visit(ctx.statement(0));
        ASTNode sndNode = this.visit(ctx.statement(1));
        ASTNode sequence = new Sequence(fstNode, sndNode);
        for (BranziParser.StatementContext sc : ctx.statement().subList(2, ctx.statement().size())) {
            ASTNode newNode = this.visit(sc);
            sequence = new Sequence(sequence, newNode);
        }

        env = env.outer();
        return sequence;
    }



    @Override
    public ASTNode visitIf_statement(BranziParser.If_statementContext ctx) {
        return new If(this.visit(ctx.expr()),
                      this.visit(ctx.block()));
    }

    @Override
    public ASTNode visitWhile_statement(BranziParser.While_statementContext ctx) {
        return super.visitWhile_statement(ctx); // TODO
    }

    @Override
    public ASTNode visitMulDiv(BranziParser.MulDivContext ctx) {
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

    @Override
    public ASTNode visitAddSub(BranziParser.AddSubContext ctx) {
        System.out.println(this.visit(ctx.Left));
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

    @Override
    public ASTNode visitExpr_el(BranziParser.Expr_elContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public ASTNode visitParens(BranziParser.ParensContext ctx) {
        return this.visit(ctx.Exp);
    }

    @Override
    public ASTNode visitExprVar(BranziParser.ExprVarContext ctx) {
        return env.get(ctx.IDENTIFIER().getText());
    }


    @Override
    public ASTNode visitExprNum(BranziParser.ExprNumContext ctx) {
        return new Number(Integer.parseInt(ctx.NUMBER().getText()));
    }

    @Override
    public ASTNode visitDeclare_statement(BranziParser.Declare_statementContext ctx) {
        Identifier varId = new Identifier(
                ctx.IDENTIFIER().getText()
        );
        varId.setType((Type) this.visit(ctx.type()));

        env.put(varId.getId(), varId);

        return new AssignVar(
                varId, this.visit(ctx.expr_el())
        );
    }


    @Override
    public ASTNode visitUpdate_statement(BranziParser.Update_statementContext ctx) {
        return new UpdateVar(
                this.visit(ctx.lvalue()), // Si occupa lui di aggiornare il env
                this.visit(ctx.expr_el())
        );
    }



//    @Override
//    public ASTNode visitExprArrayAccess(BranziParser.ExprArrayAccessContext ctx) {
//        return this.visit(ctx.array_access());
//    }
//
//    @Override
//    public ASTNode visitExprArrayExplicit(BranziParser.ExprArrayExplicitContext ctx) {
//        return this.visit(ctx.array_explicit());
//    }
//
//    @Override
//    public ASTNode visitExprBool(BranziParser.ExprBoolContext ctx) {
//        return this.visit(ctx.boolean_expr());
//    }
//
//    @Override
//    public ASTNode visitLvalueArrayAccess(BranziParser.LvalueArrayAccessContext ctx) {
//        return this.visit(ctx.array_access());
//    }

    @Override
    public ASTNode visitLvalueSimpleVar(BranziParser.LvalueSimpleVarContext ctx) {
        return env.get(ctx.IDENTIFIER().getText());
    }

    @Override
    public ASTNode visitArray_access(BranziParser.Array_accessContext ctx) {
        ASTNode arrayIdentifier = env.get(ctx.IDENTIFIER().getText());

        return new ArrayAccess(
                arrayIdentifier,
                this.visit(ctx.expr())
        );

    }

    @Override
    public ASTNode visitEmptyArray(BranziParser.EmptyArrayContext ctx) {
        return new MyArray(new ArrayList<>());
    }

    @Override
    public ASTNode visitNonEmptyArray(BranziParser.NonEmptyArrayContext ctx) {
        ArrayList<ASTNode> list = new ArrayList<>();

        list.add(this.visit(ctx.fst));
        list.addAll(
                ctx.elements.stream().map(this::visit).collect(Collectors.toList())
        );

        return new MyArray(list);
    }

    @Override
    public ASTNode visitBoolTrue(BranziParser.BoolTrueContext ctx) {
        return new Bool(true);
    }

    @Override
    public ASTNode visitBoolFalse(BranziParser.BoolFalseContext ctx) {
        return new Bool(false);
    }

    @Override
    public ASTNode visitBoolNot(BranziParser.BoolNotContext ctx) {
        // TODO (crea classe UnaryOp)
        return null;
    }

    @Override
    public ASTNode visitBoolBinop(BranziParser.BoolBinopContext ctx) {
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

    // TODO
    @Override
    public ASTNode visitBoolParens(BranziParser.BoolParensContext ctx) {
        return this.visit(ctx.boolean_expr());
    }

    @Override
    public ASTNode visitParensType(BranziParser.ParensTypeContext ctx) {
        return this.visit(ctx.simple_type());
    }

    @Override
    public ASTNode visitBaseType(BranziParser.BaseTypeContext ctx) {
        switch(ctx.basetype.getText()) {
            case "int": return Type.INT;
            case "any": return Type.ANY;
            case "bool": return Type.BOOL;
            default: return null;
        }
    }

    @Override
    public ASTNode visitArrayType(BranziParser.ArrayTypeContext ctx) {
        return new Type(
                ctx.T_LIST().getText(),
                List.of((Type) this.visit(ctx.simple_type()))
        );
    }


}
