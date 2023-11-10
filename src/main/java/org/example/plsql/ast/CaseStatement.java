package org.example.plsql.ast;

import java.util.List;

public class CaseStatement extends Statement {
    public static class When extends Node {

        private Expression expression;

        private Statement then;

        public When(Expression expression, Statement then) {
            this.expression = expression;
            this.then = then;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }

        public Statement getThen() {
            return then;
        }

        public Expression getExpression() {
            return expression;
        }
    }

    public static class Else extends Node {
        private Statement then;

        public Else(Statement then) {
            this.then = then;
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }

        public Statement getThen() {
            return then;
        }
    }

    private Expression selector;

    private List<When> whens;

    private Else elseBlock;

    public CaseStatement(Expression selector, List<When> whens, Else elseBlock) {
        this.selector = selector;
        this.whens = whens;
        this.elseBlock = elseBlock;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Expression getSelector() {
        return selector;
    }

    public Else getElseBlock() {
        return elseBlock;
    }

    public List<When> getWhens() {
        return whens;
    }
}
