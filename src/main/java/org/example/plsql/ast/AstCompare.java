package org.example.plsql.ast;

import java.util.List;

public class AstCompare {

    public static boolean labelsEqual(Statement a, Statement b) {
        List<Label> aLabels = a.getLabels();
        List<Label> bLabels = b.getLabels();
        if (aLabels.size() != bLabels.size()) {
            return false;
        }

        for (int i = 0; i < aLabels.size(); ++i) {
            if (!areEqual(aLabels.get(i), bLabels.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(Label a, Label b) {
        if (a == null && b == null) {
            return true;
        }

        if (a != null && b != null) {
            return a.getName().equals(b.getName());
        }

        return false;
    }

    public static boolean areEqual(Identifier a, Identifier b) {
        return a.getName().equals(b.getName());
    }

    public static boolean areEqual(BetweenExpression a, BetweenExpression b) {
        return areEqual(a.getValue(), b.getValue()) && areEqual(a.getLeft(), b.getLeft()) && areEqual(a.getRight(), b.getRight());
    }

    public static boolean areEqual(BinaryExpression a, BinaryExpression b) {
        return a.getOperator().equals(b.getOperator()) && areEqual(a.getLeft(), b.getLeft()) && areEqual(a.getRight(), b.getRight());
    }

    public static boolean areEqual(BooleanLiteral a, BooleanLiteral b) {
        return a.getValue() == b.getValue();
    }

    public static boolean areEqual(CollectionExistExpression a, CollectionExistExpression b) {
        return areEqual(a.getIndex(), b.getIndex()) && areEqual(a.getCollection(), b.getCollection());
    }

    public static boolean areEqual(ImplicitCursor a, ImplicitCursor b) {
        return true;
    }

    public static boolean areEqual(NamedCursor a, NamedCursor b) {
        return a.getName().equals(b.getName());
    }

    public static boolean areEqual(Cursor a, Cursor b) {
        if (a instanceof ImplicitCursor && b instanceof ImplicitCursor) {
            return areEqual((ImplicitCursor) a, (ImplicitCursor) b);
        }

        if (a instanceof NamedCursor && b instanceof NamedCursor) {
            return areEqual((NamedCursor) a, (NamedCursor) b);
        }

        return false;
    }

    public static boolean areEqual(CursorExpression a, CursorExpression b) {
        return a.getOperator().equals(b.getOperator()) && areEqual(a.getCursor(), b.getCursor());
    }

    public static boolean areEqual(ExpressionIdentifier a, ExpressionIdentifier b) {
        return areEqual(a.getIdentifier(), b.getIdentifier());
    }

    public static boolean areEqual(NullLiteral a, NullLiteral b) {
        return true;
    }

    public static boolean areEqual(NumberLiteral a, NumberLiteral b) {
        return a.getValue().equals(b.getValue());
    }

    public static boolean areEqual(StringLiteral a, StringLiteral b) {
        return a.getValue().equals(b.getValue());
    }

    public static boolean areEqual(UnaryExpression a, UnaryExpression b) {
        return a.getOperator().equals(b.getOperator()) && areEqual(a.getArgument(), b.getArgument());
    }

    public static boolean areEqual(Expression a, Expression b) {
        if (a == null || b == null) {
            return a == null && b == null;
        }

        if (a instanceof BetweenExpression && b instanceof BetweenExpression) {
            return areEqual((BetweenExpression) a, (BetweenExpression) b);
        }

        if (a instanceof BinaryExpression && b instanceof BinaryExpression) {
            return areEqual((BinaryExpression) a, (BinaryExpression) b);
        }

        if (a instanceof BooleanLiteral && b instanceof BooleanLiteral) {
            return areEqual((BooleanLiteral) a, (BooleanLiteral) b);
        }

        if (a instanceof CollectionExistExpression && b instanceof CollectionExistExpression) {
            return areEqual((CollectionExistExpression) a, (CollectionExistExpression) b);
        }

        if (a instanceof CursorExpression && b instanceof CursorExpression) {
            return areEqual((CursorExpression) a, (CursorExpression) b);
        }

        if (a instanceof ExpressionIdentifier && b instanceof ExpressionIdentifier) {
            return areEqual((ExpressionIdentifier) a, (ExpressionIdentifier) b);
        }

        if (a instanceof NullLiteral && b instanceof NullLiteral) {
            return areEqual((NullLiteral) a, (NullLiteral) b);
        }

        if (a instanceof NumberLiteral && b instanceof NumberLiteral) {
            return areEqual((NumberLiteral) a, (NumberLiteral) b);
        }

        if (a instanceof StringLiteral && b instanceof StringLiteral) {
            return areEqual((StringLiteral) a, (StringLiteral) b);
        }

        if (a instanceof UnaryExpression && b instanceof UnaryExpression) {
            return areEqual((UnaryExpression) a, (UnaryExpression) b);
        }

        return false;
    }

    public static boolean areEqual(CaseStatement.Else a, CaseStatement.Else b) {
        if (a == null && b == null) {
            return true;
        }

        if (a != null && b != null) {
            return areEqual(a.getThen(), b.getThen());
        }

        return false;
    }

    public static boolean areEqual(CaseStatement.When a, CaseStatement.When b) {
        return areEqual(a.getExpression(), b.getExpression()) && areEqual(a.getThen(), b.getThen());
    }

    public static boolean areEqual(CaseStatement a, CaseStatement b) {
        if (!labelsEqual(a, b)) {
            return false;
        }

        if (!areEqual(a.getSelector(), b.getSelector())) {
            return false;
        }

        if (!areEqual(a.getElseBlock(), b.getElseBlock())) {
            return false;
        }

        List<CaseStatement.When> aWhens = a.getWhens();
        List<CaseStatement.When> bWhens = b.getWhens();

        if (aWhens.size() != bWhens.size()) {
            return false;
        }

        for (int i = 0; i < aWhens.size(); ++i) {
            if (!areEqual(aWhens.get(i), bWhens.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(AssignmentStatement a, AssignmentStatement b) {
        if (!labelsEqual(a, b)) {
            return false;
        }

        return areEqual(a.getTarget(), b.getTarget()) && areEqual(a.getValue(), b.getValue());
    }

    public static boolean areEqual(IfStatement.Else a, IfStatement.Else b) {
        if (a == null || b == null) {
            return a == null && b == null;
        }

        List<Statement> aStatements = a.getStatements();
        List<Statement> bStatements = b.getStatements();

        if (aStatements.size() != bStatements.size()) {
            return false;
        }

        for (int i = 0; i < aStatements.size(); ++i) {
            if (!areEqual(aStatements.get(i), bStatements.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(IfStatement.Elsif a, IfStatement.Elsif b) {
        if (!areEqual(a.getCondition(), b.getCondition())) {
            return false;
        }

        List<Statement> aStatements = a.getStatements();
        List<Statement> bStatements = b.getStatements();

        if (aStatements.size() != bStatements.size()) {
            return false;
        }

        for (int i = 0; i < aStatements.size(); ++i) {
            if (!areEqual(aStatements.get(i), bStatements.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(IfStatement a, IfStatement b) {
        if (!labelsEqual(a, b)) {
            return false;
        }

        if (!areEqual(a.getCondition(), b.getCondition())) {
            return false;
        }

        if (!areEqual(a.getElseBlock(), b.getElseBlock())) {
            return false;
        }

        List<Statement> aStatements = a.getStatements();
        List<Statement> bStatements = b.getStatements();

        if (aStatements.size() != bStatements.size()) {
            return false;
        }

        for (int i = 0; i < aStatements.size(); ++i) {
            if (!areEqual(aStatements.get(i), bStatements.get(i))) {
                return false;
            }
        }

        List<IfStatement.Elsif> aElsifList = a.getElsifList();
        List<IfStatement.Elsif> bElsifList = b.getElsifList();

        if (aElsifList.size() != bElsifList.size()) {
            return false;
        }

        for (int i = 0; i < aElsifList.size(); ++i) {
            if (!areEqual(aElsifList.get(i), bElsifList.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(LoopStatement a, LoopStatement b) {
        if (!labelsEqual(a, b)) {
            return false;
        }

        List<Statement> aStatements = a.getStatements();
        List<Statement> bStatements = b.getStatements();

        if (aStatements.size() != bStatements.size()) {
            return false;
        }

        for (int i = 0; i < aStatements.size(); ++i) {
            if (!areEqual(aStatements.get(i), bStatements.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(Statement a, Statement b) {
        if (a instanceof AssignmentStatement && b instanceof AssignmentStatement) {
            return areEqual((AssignmentStatement) a, (AssignmentStatement) b);
        }

        if (a instanceof Block && b instanceof Block) {
            return areEqual((Block) a, (Block) b);
        }

        if (a instanceof CaseStatement && b instanceof CaseStatement) {
            return areEqual((CaseStatement) a, (CaseStatement) b);
        }

        if (a instanceof IfStatement && b instanceof IfStatement) {
            return areEqual((IfStatement) a, (IfStatement) b);
        }

        if (a instanceof LoopStatement && b instanceof LoopStatement) {
            return areEqual((LoopStatement) a, (LoopStatement) b);
        }

        return false;
    }

    public static boolean areEqual(Body a, Body b) {
        if (!areEqual(a.getClosingLabel(), b.getClosingLabel())) {
            return false;
        }

        List<Statement> aStatements = a.getStatements();
        List<Statement> bStatements = b.getStatements();

        if (aStatements.size() != bStatements.size()) {
            return false;
        }

        for (int i = 0; i < aStatements.size(); ++i) {
            if (!areEqual(aStatements.get(i), bStatements.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean areEqual(Block a, Block b) {
        if (!labelsEqual(a, b)) {
            return false;
        }

        Body aBody = a.getBody();
        Body bBody = b.getBody();

        return areEqual(aBody, bBody);
    }

    public static boolean areEqual(Program a, Program b) {
        List<Block> aBlocks = a.getBlocks();
        List<Block> bBlocks = b.getBlocks();

        if (aBlocks.size() != bBlocks.size()) {
            return false;
        }

        for (int i = 0; i < aBlocks.size(); ++i) {
            if (!areEqual(aBlocks.get(i), bBlocks.get(i))) {
                return false;
            }
        }

        return true;
    }
}
