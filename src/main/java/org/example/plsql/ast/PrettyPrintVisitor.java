package org.example.plsql.ast;

public class PrettyPrintVisitor implements Visitor<String> {

    private int currentIndent;
    private final int indent;

    public PrettyPrintVisitor(int indent) {
        this.indent = indent;
        this.currentIndent = 0;
    }

    @Override
    public String visit(Program p) {
        StringBuilder output =
                new StringBuilder("/*\n" +
                        " * Pretty-print output of PL/SQL program. For debugging purposes only.\n" +
                        " */\n");

        for (Block b : p.getBlocks()) {
            output.append(b.accept(this));
        }

        return output.toString();
    }

    @Override
    public String visit(Block block) {
        StringBuilder output = new StringBuilder();
        for (Label s : block.getLabels()) {
            output.append(s.accept(this));
            output.append('\n');
        }

        output.append(block.getBody().accept(this));
        return output.toString();
    }

    @Override
    public String visit(Body body) {
        StringBuilder output = new StringBuilder(" ".repeat(currentIndent) + "BEGIN\n");

        currentIndent += indent;
        for (Statement statement : body.getStatements()) {
            output.append(statement.accept(this));
            output.append('\n');
        }
        currentIndent -= indent;
        output.append(" ".repeat(currentIndent)).append("END;");

        return output.toString();
    }

    @Override
    public String visit(AssignmentStatement statement) {
        StringBuilder output = new StringBuilder();
        for (Label label : statement.getLabels()) {
            output.append(label.accept(this));
            output.append('\n');
        }
        output.append(" ".repeat(currentIndent));
        output.append(statement.getTarget().accept(this));
        output.append(" := ");
        output.append(statement.getValue().accept(this));
        output.append(";");
        return output.toString();
    }

    @Override
    public String visit(BetweenExpression expression) {
        return expression.getValue().accept(this) +
                " IS BETWEEN " +
                expression.getLeft().accept(this) +
                " AND " +
                expression.getRight().accept(this);
    }

    @Override
    public String visit(BinaryExpression expression) {
        return expression.getLeft().accept(this) +
                " " +
                expression.getOperator().prettyPrint() +
                " " + expression.getRight().accept(this);
    }

    @Override
    public String visit(BooleanLiteral literal) {
        return literal.getValue() ? "TRUE" : "FALSE";
    }

    @Override
    public String visit(CaseStatement statement) {
        StringBuilder output = new StringBuilder();
        for (Label l : statement.getLabels()) {
            output.append(l.accept(this));
            output.append('\n');
        }

        output.append(" ".repeat(currentIndent));
        output.append("CASE");
        if (statement.getSelector() != null) {
            output.append(' ');
            output.append(statement.getSelector().accept(this));
        }
        output.append('\n');

        currentIndent += indent;
        for (CaseStatement.When w : statement.getWhens()) {
            output.append(w.accept(this));
            output.append('\n');
        }

        if (statement.getElseBlock() != null) {
            output.append(statement.getElseBlock().accept(this));
            output.append('\n');
        }
        currentIndent -= indent;
        output.append(" ".repeat(currentIndent));
        output.append("END CASE;");

        return output.toString();
    }

    @Override
    public String visit(CaseStatement.Else elseStatement) {
        StringBuilder output = new StringBuilder(" ".repeat(currentIndent) + "ELSE\n");

        currentIndent += indent;
        output.append(elseStatement.getThen().accept(this));
        currentIndent -= indent;

        return output.toString();
    }

    @Override
    public String visit(CaseStatement.When whenStatement) {
        StringBuilder output = new StringBuilder(" ".repeat(currentIndent) + "WHEN ");
        output.append(whenStatement.getExpression().accept(this));
        output.append(" THEN\n");
        currentIndent += indent;
        output.append(whenStatement.getThen().accept(this));
        currentIndent -= indent;
        return output.toString();
    }

    @Override
    public String visit(CollectionExistExpression expression) {
        return expression.getCollection().accept(this) + ".EXISTS(" + expression.getIndex().accept(this) + ")";
    }

    @Override
    public String visit(CursorExpression expression) {
        return expression.getCursor().accept(this) + "%" + expression.getOperator().prettyPrint();
    }

    @Override
    public String visit(ExpressionIdentifier identifier) {
        return identifier.getIdentifier().accept(this);
    }

    @Override
    public String visit(Identifier identifier) {
        return identifier.getName();
    }

    @Override
    public String visit(IfStatement statement) {
        StringBuilder output = new StringBuilder();
        for (Label l : statement.getLabels()) {
            output.append(l.accept(this));
            output.append('\n');
        }

        output.append(" ".repeat(currentIndent));
        output.append("IF ");
        output.append(statement.getCondition().accept(this));
        output.append(" THEN\n");

        currentIndent += indent;
        for (Statement s : statement.getStatements()) {
            output.append(s.accept(this));
            output.append('\n');
        }
        currentIndent -= indent;

        for (IfStatement.Elsif elsif : statement.getElsifList()) {
            output.append(elsif.accept(this));
        }

        if (statement.getElseBlock() != null) {
            output.append(statement.getElseBlock().accept(this));
        }

        output.append(" ".repeat(currentIndent));
        output.append("END IF;");
        return output.toString();
    }

    @Override
    public String visit(IfStatement.Else elseStatement) {
        StringBuilder output = new StringBuilder();
        output.append(" ".repeat(currentIndent));
        output.append("ELSE\n");

        currentIndent += indent;
        for (Statement s : elseStatement.getStatements()) {
            output.append(s.accept(this));
            output.append('\n');
        }
        currentIndent -= indent;

        return output.toString();
    }

    @Override
    public String visit(IfStatement.Elsif elsifStatement) {
        StringBuilder output = new StringBuilder();
        output.append(" ".repeat(currentIndent));
        output.append("ELSIF ");
        output.append(elsifStatement.getCondition().accept(this));
        output.append(" THEN\n");

        currentIndent += indent;
        for (Statement s : elsifStatement.getStatements()) {
            output.append(s.accept(this));
            output.append('\n');
        }
        currentIndent -= indent;

        return output.toString();
    }

    @Override
    public String visit(ImplicitCursor cursor) {
        return "SQL";
    }

    @Override
    public String visit(Label label) {
        return " ".repeat(currentIndent) + "<<" + label.getName() + ">>";
    }

    @Override
    public String visit(LoopStatement loopStatement) {
        StringBuilder output = new StringBuilder();
        for (Label l : loopStatement.getLabels()) {
            output.append(l.accept(this));
            output.append('\n');
        }

        output.append(" ".repeat(currentIndent));
        output.append("LOOP\n");

        currentIndent += indent;
        for (Statement s : loopStatement.getStatements()) {
            output.append(s.accept(this));
            output.append('\n');
        }
        currentIndent -= indent;
        output.append(" ".repeat(currentIndent));
        output.append("END LOOP;");

        return output.toString();
    }

    @Override
    public String visit(NamedCursor cursor) {
        return cursor.getName();
    }

    @Override
    public String visit(NullLiteral literal) {
        return "NULL";
    }

    @Override
    public String visit(NumberLiteral literal) {
        return literal.getValue();
    }

    @Override
    public String visit(StringLiteral literal) {
        return "\"" + literal.getValue() + "\"";
    }

    @Override
    public String visit(UnaryExpression expression) {
        return expression.getOperator().prettyPrint() + " " + expression.getArgument().accept(this);
    }
}
