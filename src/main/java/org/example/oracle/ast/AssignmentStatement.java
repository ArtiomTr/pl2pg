package org.example.oracle.ast;

public class AssignmentStatement extends Statement {

    private Identifier target;
    private Expression value;

    public AssignmentStatement(Identifier target, Expression value) {
        this.target = target;
        this.value = value;
    }


    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Identifier getTarget() {
        return target;
    }

    public Expression getValue() {
        return value;
    }
}
