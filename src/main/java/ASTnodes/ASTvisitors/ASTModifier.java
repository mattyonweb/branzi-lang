package ASTnodes.ASTvisitors;

import ASTnodes.*;
import ASTnodes.Number;

import java.util.stream.Collectors;

public class ASTModifier {
    public ASTNode visitArrayAccess(ArrayAccess ac) {
        ac.setId(ac.getVariableName().astmodify(this));
        ac.setOffset(ac.getOffset().astmodify(this));
        return ac;
    }

    public ASTNode visitAssignVar(AssignVar av) {
        av.setVarId(av.varId.astmodify(this));
        av.setValue(av.value.astmodify(this));
        return av;
    }

    public ASTNode visitBinOp(BinOp bo) {
        bo.setArg1(bo.getArg1().astmodify(this));
        bo.setArg2(bo.getArg2().astmodify(this));
        return bo;
    }

    public ASTNode visitBool(Bool b) { return b; }
    public ASTNode visitBreak(Break b) { return b; }
    public ASTNode visitContinue(Continue c) { return c; }

    public ASTNode visitFuncall(Funcall c) {
        c.setFuncId((Identifier) c.getFuncId().astmodify(this));
        c.setArgs(c.getArgs().stream().map(x -> x.astmodify(this)).collect(Collectors.toList()));
        return c;
    }

    public ASTNode visitFunction(Function f) {
        f.setFuncId((Identifier) f.getFuncId().astmodify(this));
        f.setFuncType((Type) f.getFuncType().astmodify(this));
        f.setBody(f.getBody().astmodify(this));
        f.setReturnNodes(f.getReturnNodes().stream().map(t -> (Return) t.astmodify(this)).collect(Collectors.toList()));
        return f;
    }

    public ASTNode visitIdentifier(Identifier id) {   return id;  }

    public ASTNode visitIf(If i) {
        i.setCondition(i.getCondition().astmodify(this));
        i.setCode(i.getCode().astmodify(this));
        return i;
    }

    public ASTNode visitMyArray(MyArray ma) {
        ma.setList(ma.getList().stream().map(x -> x.astmodify(this)).collect(Collectors.toList()));
        return ma;
    }

    public ASTNode visitNumber(Number n) { return n; }

    public ASTNode visitProgram(Program p) {
        p.setUnits(p.getUnits().stream().map(x -> x.astmodify(this)).collect(Collectors.toList()));
        return p;
    }

    public ASTNode visitReturn(Return r) {
        return r;
    }

    public ASTNode visitSequence(Sequence s) {
        s.setN1(s.getN1().astmodify(this));
        s.setN2(s.getN2().astmodify(this));
        return s;
    }

    public ASTNode visitType(Type t) {
        return t;
    }

    public ASTNode visitUpdateVar(UpdateVar uv) {
        uv.setVarId(uv.getVarId().astmodify(this));
        uv.setValue(uv.getValue().astmodify(this));
        return uv;
    }

    public ASTNode visitWhile(While w) {
        w.setCondition(w.getCondition().astmodify(this));
        w.setBlock(w.getBlock().astmodify(this));
        return w;
    }
}
