package ASTnodes.ASTvisitors;

import ASTnodes.*;
import ASTnodes.Number;

public class ASTVisitor {
    public void visitArrayAccess(ArrayAccess ac) {
        ac.getVariableName().astvisit(this);
        ac.getOffset().astvisit(this);
        
    }

    public void visitAssignVar(AssignVar av) {
        av.varId.astvisit(this);
        av.value.astvisit(this);
        
    }

    public void visitBinOp(BinOp bo) {
        bo.getArg1().astvisit(this);
        bo.getArg2().astvisit(this);
        
    }

    public void visitBool(Bool b) {   }
    public void visitBreak(Break b) {  }
    public void visitContinue(Continue c) {  }

    public void visitFuncall(Funcall c) {
        c.getFuncId().astvisit(this);
        c.getArgs().forEach(x -> x.astvisit(this));
        
    }

    public void visitFunction(Function f) {
        f.getFuncId().astvisit(this);
        f.getFuncArgs().forEach(x -> x.astvisit(this));
        f.getFuncType().astvisit(this);
        f.getBody().astvisit(this);
        f.getReturnNodes().forEach(t -> t.astvisit(this));
        
    }

    public void visitIdentifier(Identifier id) {  }

    public void visitIf(If i) {
        i.getCondition().astvisit(this);
        i.getCode().astvisit(this);
        
    }

    public void visitMyArray(MyArray ma) {
        ma.getList().forEach(x -> x.astvisit(this));
        
    }

    public void visitNumber(Number n) { }

    public void visitProgram(Program p) {
        p.getUnits().forEach(x -> x.astvisit(this));
        
    }

    public void visitReturn(Return r) {
        r.getExpr().astvisit(this);
    }

    public void visitSequence(Sequence s) {
        s.getN1().astvisit(this);
        s.getN2().astvisit(this);

    }

    public void visitType(Type t) {
        
//        t.astvisit(this);
    }

    public void visitUpdateVar(UpdateVar uv) {
        uv.getVarId().astvisit(this);
        uv.getValue().astvisit(this);
        
    }

    public void visitWhile(While w) {
        w.getCondition().astvisit(this);
        w.getBlock().astvisit(this);
        
    }


}
