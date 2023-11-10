package org.example.plsql.ast;

import java.util.List;

public class Body extends Node {
    private List<Statement> statements;

    private Label closingLabel;

    public Body(List<Statement> statements, Label closingLabel) {
        this.statements = statements;
        this.closingLabel = closingLabel;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public Label getClosingLabel() {
        return closingLabel;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
