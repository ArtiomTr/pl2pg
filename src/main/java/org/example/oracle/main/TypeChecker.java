package org.example.oracle.main;

import org.example.oracle.ast.Program;
import org.example.oracle.types.TypeCheckVisitor;

public class TypeChecker {


    public void doTypeCheck(Program program) {
        TypeCheckVisitor visitor = new TypeCheckVisitor();

        visitor.visit(program);
    }

}
