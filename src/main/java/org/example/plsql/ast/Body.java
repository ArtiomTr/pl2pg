package org.example.plsql.ast;

import java.util.List;

public class Body extends Node {
    private List<Statement> statements;

    public Body(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof Body)) {
            return false;
        }

        Body b = (Body) node;

        if (this.statements.size() != b.statements.size()) {
            return false;
        }

        for (int i = 0; i < this.statements.size(); ++i) {
            if (!this.statements.get(i).areEqual(b.statements.get(i))) {
                return false;
            }
        }

        return true;
    }
}
