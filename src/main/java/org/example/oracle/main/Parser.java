package org.example.oracle.main;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import org.example.oracle.ast.Program;
import org.example.oracle.main.generated.Scanner;

import java.io.Reader;

public class Parser {

    public Parser() {

    }

    public Program parse(Reader in) throws ParseException {
        ComplexSymbolFactory factory = new ComplexSymbolFactory();
        Scanner scanner = new Scanner(in, factory);
        var parser = new org.example.oracle.main.generated.Parser(scanner, factory);

        try {
            Symbol s = parser.parse();
            return (Program) s.value;
        } catch (Exception | Error e) {
            throw new ParseException(e);
        }
    }
}
