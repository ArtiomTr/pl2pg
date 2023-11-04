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
        public boolean areEqual(Node node) {
            if (!(node instanceof When)) {
                return false;
            }

            When other = (When) node;

            return other.expression.areEqual(this.expression) && other.then.areEqual(this.then);
        }
    }

    public static class Else extends Node {
        private Statement then;

        public Else(Statement then) {
            this.then = then;
        }

        @Override
        public boolean areEqual(Node node) {
            return (node instanceof Else) && ((Else) node).then.areEqual(this.then);
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
    public boolean areEqual(Node node) {
        if (!(node instanceof CaseStatement)) {
            return false;
        }

        CaseStatement other = (CaseStatement) node;

        if ((other.selector == null) != (this.selector == null)) {
            return false;
        }

        if (other.selector != null && !other.selector.areEqual(this.selector)) {
            return false;
        }

        if ((other.elseBlock == null) != (this.elseBlock == null)) {
            return false;
        }

        if (other.elseBlock != null && !other.elseBlock.areEqual(this.elseBlock)) {
            return false;
        }

        return Node.areListsEqual(other.whens, this.whens);
    }
}
