package org.example.oracle.main;

import org.example.oracle.ast.Program;
import org.example.oracle.types.TypeCheckError;
import org.example.oracle.types.TypeCheckVisitor;

public class TypeChecker {


    public void doTypeCheck(Program program) throws TypeCheckException {
        TypeCheckVisitor visitor = new TypeCheckVisitor();

        try {
            visitor.visit(program);
        } catch (TypeCheckError error) {
            throw new TypeCheckException(error);
        }
    }

}
