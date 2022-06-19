package FrontEnd;

import ASTnodes.*;
import ASTnodes.ASTvisitors.OptimizerRemoveDeadCodeInFunction;
import ASTnodes.ASTvisitors.VisitorFindReturnsWithin;
import ASTnodes.Number;
import generated.BranziBaseVisitor;
import generated.BranziParser;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class BranziMyVisitor extends BranziBaseVisitor<ASTNode> {
    Environment env = new Environment(null);
    Map<Identifier, Function> functions = new HashMap<>();

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

        Type funcType = (Type) this.visit(ctx.func_type());
        System.out.println(funcType);
        funcId.setType(funcType);

        List<Identifier> funcArgs = new ArrayList<>();

        // Aggiungerò all'ENV il nome di questa stessa funzione, per permettere
        // chiamate ricosrive
        env = new Environment(env);
        env.putAtOutermost(funcname, funcId);

        // Ottieni AST del body della funzione
        ASTNode funcBody         = this.visit(ctx.block()).astmodify(new OptimizerRemoveDeadCodeInFunction());
        List<Return> funcReturns = this.allReturnsInside(funcBody);

        env = env.outer();

        Function foo = new Function(
                funcId,
                funcType,
                funcArgs,
                funcBody,
                funcReturns
        );

        // Functions IDS are saved in env
        // Functions are saved in the map ID -> FUNCTION
        functions.put(funcId, foo);
        return foo;
    }

    @Override
    public ASTNode visitFuncDeclNonEmptyArgs(BranziParser.FuncDeclNonEmptyArgsContext ctx) {
        String funcname = ctx.Name.getText();
        Identifier funcId = new Identifier(funcname);

        Type funcType = (Type) this.visit(ctx.func_type());
        funcId.setType(funcType);

        // Raccolgo i nomi (:: string) degli argomenti
        List<String> funcArgsNames = new ArrayList<>();
        funcArgsNames.add(ctx.arg1.getText());
        funcArgsNames.addAll(ctx.otherArgs
                .stream()
                .map(Token::getText)
                .collect(Collectors.toList())
        );

        // Se
        //     foo : int -> bool -> int (x, b)
        // ritorna [x:int, b:bool]
        List<Identifier> funcArgs = createTypedVariables(
                funcArgsNames, funcType
        );

        // Creo la scope per l'interno della funzione
        env = new Environment(env);
        for (Identifier id: funcArgs) {
            env.put(id.getId(), id);
        }

        // Nell'env metto i riferimenti alla funzione che sono in procinto di definire.
        // Aggiungo anche un `null` alle functions, in modo da intercettare casi del tipo:
        //     { foo: int := 666; foo(12); }
        env.putAtOutermost(funcname, funcId);
        functions.put(funcId, null);

        // Ottieni AST del body della funzione
        ASTNode funcBody         = this.visit(ctx.block()).astmodify(new OptimizerRemoveDeadCodeInFunction());
        List<Return> funcReturns = this.allReturnsInside(funcBody);

        env = env.outer();

        Function foo = new Function(
                funcId,
                funcType,
                funcArgs,
                funcBody,
                funcReturns
        );

        functions.put(funcId, foo);
        return foo;
    }


    private List<Return> allReturnsInside(ASTNode root) {
        VisitorFindReturnsWithin visitorReturn = new VisitorFindReturnsWithin();
        root.astvisit(visitorReturn);
        return visitorReturn.getFound();
    }


    private List<Identifier> createTypedVariables(List<String> varNames, Type funcType) {
        if (!funcType.isFunction()) {
            throw new TypeCheckerFail("Serious error: function has not-function type: " + funcType);
        }

        int funcArity = max(0, funcType.getParameters().size()-1);
        if (funcArity != varNames.size()) {
            throw new TypeCheckerFail(
                    "Mismatch between expected num. of arguments (" + funcArity + ") and" +
                            "given arguments (" + varNames.size() + ")"
            );
        }

        List<Identifier> variables = new ArrayList<>();
        for (int i = 0; i < varNames.size(); i++) {
            Identifier varId = new Identifier(varNames.get(i));
            varId.setType(funcType.getParameter(i));
            variables.add(varId);
        }

        return variables;
    }

    @Override
    public ASTNode visitFuncallNoArgs(BranziParser.FuncallNoArgsContext ctx) {
        Identifier funcId  = (Identifier) env.get(ctx.IDENTIFIER().getText());


        if (!functions.containsKey(funcId))
            throw new TypeCheckerFail("Tried to call " + funcId.getId() + " but in this scope it is not a function" +
                    "\nIn: " + ctx.IDENTIFIER().getParent().getText());

        return new Funcall(funcId);
    }

    @Override
    public ASTNode visitFuncallWithArgs(BranziParser.FuncallWithArgsContext ctx) {
        Identifier funcId  = (Identifier) env.get(ctx.IDENTIFIER().getText());

        if (!functions.containsKey(funcId))
            throw new TypeCheckerFail("Tried to call " + funcId.getId() + " but in this scope it is not a function" +
                    "\nIn: " + ctx.IDENTIFIER().getParent().getText());

        List<ASTNode> args = new ArrayList<>();
        args.add(this.visit(ctx.arg1));
        args.addAll(ctx.otherArgs.stream().map(this::visit).collect(Collectors.toList()));

        return new Funcall(funcId, args);
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
        // Visita blocco "nudo", ie. NON il blocco di una funcdef
        if (ctx.statement().size() == 0) {
            return new NoOp();
        }

        env = new Environment(env);
        ASTNode seq = buildSequence_StatementContext(ctx.statement());
        env = env.outer();

        return seq;
    }
    private ASTNode buildSequence_StatementContext(List<BranziParser.StatementContext> statement) {
        // Costruisce sequenza di nodi con la lista di statement forniti
        if (statement.size() == 0) {
            return new NoOp();
        }

        // Solo un elemento => nessuna Sequence
        if (statement.size() == 1) {
            return this.visit(statement.get(0));
        }

        return new Sequence(statement
                .stream()
                .map(this::visit)
                .collect(Collectors.toList())
        );
    }


    @Override
    public ASTNode visitEmptyReturn(BranziParser.EmptyReturnContext ctx) {
        return new Return(new NoOp());
    }

    @Override
    public ASTNode visitHappyReturn(BranziParser.HappyReturnContext ctx) {
        System.out.println(env);
        return new Return(this.visit(ctx.expr()));
    }


    @Override
    public ASTNode visitIf_statement(BranziParser.If_statementContext ctx) {
        return new If(this.visit(ctx.expr()),
                      this.visit(ctx.block()));
    }

    @Override
    public ASTNode visitWhile_statement(BranziParser.While_statementContext ctx) {
        return new While(
                this.visit(ctx.expr()),
                this.visit(ctx.block_brk_cnt())
        );
    }

    @Override
    public ASTNode visitBlock_brk_cnt(BranziParser.Block_brk_cntContext ctx) {
        // Visita blocco "nudo", ie. NON il blocco di una funcdef
        if (ctx.statement_brk_cnt().size() == 0) {
            return new NoOp();
        }

        env = new Environment(env);
        ASTNode seq = buildSequence_statement_brk_cntContext(ctx.statement_brk_cnt());
        env = env.outer();

        return seq;
    }
    private ASTNode buildSequence_statement_brk_cntContext(List<BranziParser.Statement_brk_cntContext> statement) {
        // Costruisce sequenza di nodi con la lista di statement forniti
        if (statement.size() == 0) {
            return new NoOp();
        }

        // Solo un elemento => nessuna Sequence
        if (statement.size() == 1) {
            return this.visit(statement.get(0));
        }

        return new Sequence(statement
                .stream()
                .map(this::visit)
                .collect(Collectors.toList())
        );
//        ASTNode fstNode = this.visit(statement.get(0));
//        ASTNode sndNode = this.visit(statement.get(1));
//        ASTNode sequence = new Sequence(fstNode, sndNode);
//        for (BranziParser.Statement_brk_cntContext sc : statement.subList(2, statement.size())) {
//            ASTNode newNode = this.visit(sc);
//            sequence = new Sequence(sequence, newNode);
//        }
//
//        return sequence;

//        ASTNode sequence = new NoOp();
//        // Right recursive
//        for (int i = statement.size()-1; i >= 0; i--) {
//            sequence = new Sequence(this.visit(statement.get(i)), sequence);
//        }
//
//        System.out.println(sequence);
//        return sequence;
    }

    @Override
    public ASTNode visitBreak_statement(BranziParser.Break_statementContext ctx) {
        return new Break();
    }

    @Override
    public ASTNode visitContinue_statement(BranziParser.Continue_statementContext ctx) {
        return new Continue();
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
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

    @Override
    public ASTNode visitEqTest(BranziParser.EqTestContext ctx) {
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


//    @Override
//    public ASTNode visitBoolNot(BranziParser.BoolNotContext ctx) {
//        // TODO (crea classe UnaryOp)
//        return null;
//    }


    @Override
    public ASTNode visitBoolBinop(BranziParser.BoolBinopContext ctx) {
        return new BinOp(
                this.visit(ctx.Left),
                ctx.Op.getText(),
                this.visit(ctx.Right)
        );
    }

//    // TODO
//    @Override
//    public ASTNode visitBoolParens(BranziParser.BoolParensContext ctx) {
//        return this.visit(ctx.boolean_expr());
//    }
//
//    @Override
//    public ASTNode visitBoolIdentifier(BranziParser.BoolIdentifierContext ctx) {
//        return env.get(ctx.IDENTIFIER().getText());
//    }

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
            case "void": return Type.VOID;
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
