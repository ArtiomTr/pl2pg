package org.example.postgres.generation;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import org.example.oracle.ast.Program;
import org.example.oracle.main.TypeChecker;
import org.example.oracle.main.generated.Scanner;

import java.io.Reader;

public class Generator {

    public Generator() {

    }

    public String generate(Reader in) throws GenerateException {
        ComplexSymbolFactory factory = new ComplexSymbolFactory();
        Scanner scanner = new Scanner(in, factory);
        org.example.oracle.main.generated.Parser parser = new org.example.oracle.main.generated.Parser(scanner, factory);

        try {
            Symbol s = parser.parse();
            Program program = (Program) s.value;

            TypeChecker typeChecker = new TypeChecker();
            typeChecker.doTypeCheck(program);

            GeneratorVisitor visitor = new GeneratorVisitor();
            return visitor.visit(program);
        } catch (Exception | Error e) {
            throw new GenerateException(e);
        }
    }

}
