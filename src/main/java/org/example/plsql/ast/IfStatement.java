package org.example.plsql.ast;

import java.util.List;

public class IfStatement extends Statement {

    public static class Elsif extends Node {

        private Expression condition;

        private List<Statement> statements;

        public Elsif(Expression condition, List<Statement> statements) {
            this.condition = condition;
            this.statements = statements;
        }

        @Override
        public boolean areEqual(Node node) {
            if (!(node instanceof Elsif)) {
                return false;
            }

            Elsif other = (Elsif) node;

            return other.condition.areEqual(this.condition) && Node.areListsEqual(other.statements, this.statements);
        }
    }

    public static class Else extends Node {

        private List<Statement> statements;

        public Else(List<Statement> statements) {
            this.statements = statements;
        }

        @Override
        public boolean areEqual(Node node) {
            return (node instanceof Else) && Node.areListsEqual(((Else) node).statements, this.statements);
        }
    }

    private Expression condition;

    private List<Statement> statements;

    private List<Elsif> elsifList;

    private Else elseBlock;

    public IfStatement(Expression condition, List<Statement> statements, List<Elsif> elsifList, Else elseBlock) {
        this.condition = condition;
        this.statements = statements;
        this.elsifList = elsifList;
        this.elseBlock = elseBlock;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof IfStatement)) {
            return false;
        }

        IfStatement other = (IfStatement) node;

        if (!other.condition.areEqual(this.condition)) {
            return false;
        }

        if (!Node.checkEqualityWithNull(this.elseBlock, other.elseBlock)) {
            return false;
        }

        return this.labelsEqual(other) && Node.areListsEqual(this.elsifList, other.elsifList) && Node.areListsEqual(this.statements, other.statements);
    }
}
