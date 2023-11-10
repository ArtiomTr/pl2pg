package org.example.plsql.ast;

public interface Visitor<T> {

    T visit(Program p);

    T visit(Block block);

    T visit(Body body);

    T visit(AssignmentStatement statement);

    T visit(BetweenExpression expression);

    T visit(BinaryExpression expression);

    T visit(BooleanLiteral literal);

    T visit(CaseStatement statement);

    T visit(CaseStatement.Else elseStatement);

    T visit(CaseStatement.When whenStatement);

    T visit(CollectionExistExpression expression);

    T visit(CursorExpression expression);

    T visit(ExpressionIdentifier identifier);

    T visit(Identifier identifier);

    T visit(IfStatement statement);

    T visit(IfStatement.Else elseStatement);

    T visit(IfStatement.Elsif elsifStatement);

    T visit(ImplicitCursor cursor);

    T visit(Label label);

    T visit(LoopStatement loopStatement);

    T visit(NamedCursor cursor);

    T visit(NullLiteral literal);

    T visit(NumberLiteral literal);

    T visit(StringLiteral literal);

    T visit(UnaryExpression expression);
}
