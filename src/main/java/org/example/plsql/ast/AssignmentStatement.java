package org.example.plsql.ast;

public class AssignmentStatement extends Statement {

    private Identifier target;
    private Expression value;

    public AssignmentStatement(Identifier target, Expression value) {
        this.target = target;
        this.value = value;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof AssignmentStatement)) {
            return false;
        }

        AssignmentStatement other = (AssignmentStatement) node;

        return this.labelsEqual(other) && other.target.areEqual(this.target) && other.value.areEqual(this.value);
    }
}
