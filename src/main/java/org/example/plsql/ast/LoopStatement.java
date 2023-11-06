package org.example.plsql.ast;

import java.util.List;

public class LoopStatement extends Statement {

    private List<Statement> statements;

    public LoopStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof LoopStatement)) {
            return false;
        }

        LoopStatement other = (LoopStatement) node;

        if (!this.labelsEqual(other)) {
            return false;
        }

        if (other.statements.size() != this.statements.size()) {
            return false;
        }

        for (int i = 0; i < this.statements.size(); ++i) {
            if (!this.statements.get(i).areEqual(other.statements.get(i))) {
                return false;
            }
        }

        return true;
    }
}
