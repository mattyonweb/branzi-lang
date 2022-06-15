package ASTnodes;

import java.util.ArrayList;

public class MyArray extends ASTNode {
    private ArrayList<ASTNode> list;

    public MyArray(ArrayList<ASTNode> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MyArray{" +
                "list=" + list +
                '}';
    }
}
