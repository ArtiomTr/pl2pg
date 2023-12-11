package org.example.oracle.ast;

import java.util.List;

public class LoopStatement extends Statement {

    private List<Statement> statements;

    public LoopStatement(List<Statement> statements) {
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
