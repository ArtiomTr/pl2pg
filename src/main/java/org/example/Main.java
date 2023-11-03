package org.example;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ScannerBuffer;
import java.io.BufferedReader;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) {
//        StringReader reader = new StringReader("BEGIN\n" +
//                "    items := arr(1, 2, 3, 4, 5);\n" +
//                "    i := items.COUNT;\n" +
//                "    sum := 0;\n" +
//                "\n" +
//                "    LOOP\n" +
//                "        IF i = 0 THEN\n" +
//                "            RETURN sum;\n" +
//                "        END IF;\n" +
//                "\n" +
//                "        sum := sum + items(i);\n" +
//                "        i := i - 1;\n" +
//                "    END LOOP;\n" +
//                "END;");
        StringReader reader = new StringReader("BEGIN\n" +
                "    items := 'Hello, world!';\n" +
                "END;");
        ComplexSymbolFactory csf = new ComplexSymbolFactory();
        // create a buffering scanner wrapper
        ScannerBuffer lexer = new ScannerBuffer(new PLSQLScanner(new BufferedReader(reader), csf));
        PLSQLParser parser = new PLSQLParser(lexer, csf);

//        while (!scanner.yyatEOF()) {
//            try {
//                Symbol s = scanner.next_token();
//                if (s != null)
//                    System.out.println(sym.terminalNames[s.sym]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        try {
            ComplexSymbolFactory.ComplexSymbol v = (ComplexSymbolFactory.ComplexSymbol) parser.parse();
            System.out.println("Parsed: " + v.value);
//            XMLElement e = (XMLElement) v.value;
//            XMLOutputFactory f = XMLOutputFactory.newInstance();
//            XMLStreamWriter sw = f.createXMLStreamWriter(new FileOutputStream("a.xml"));
//            System.out.println(v + " " + e);
//            XMLElement.dump(lexer, sw, e);
//            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource())
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}