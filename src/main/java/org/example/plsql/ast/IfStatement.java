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
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }

        public Expression getCondition() {
            return condition;
        }

        public List<Statement> getStatements() {
            return statements;
        }
    }

    public static class Else extends Node {

        private List<Statement> statements;

        public Else(List<Statement> statements) {
            this.statements = statements;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }

        public List<Statement> getStatements() {
            return statements;
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
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Else getElseBlock() {
        return elseBlock;
    }

    public List<Elsif> getElsifList() {
        return elsifList;
    }
}
