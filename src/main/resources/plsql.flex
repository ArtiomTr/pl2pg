package org.example;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import org.example.plsql.ast.*;

%%

%public
%class PLSQLScanner
%unicode
%line
%column
%char
%cup

%{
    StringBuffer string = new StringBuffer();
    String customDelimiter = "";
    ComplexSymbolFactory factory;

    private void setDelimiter(String openingDelimiter) {
        switch (openingDelimiter) {
            case "[":
                this.customDelimiter = "]";
                break;
            case "{":
                this.customDelimiter = "}";
                break;
            case "<":
                this.customDelimiter = ">";
                break;
            case "(":
                this.customDelimiter = ")";
                break;
            default:
                this.customDelimiter = openingDelimiter;
                break;
        }
    }

    private Symbol symbol(String name, int type) {
        Location start = new Location(yyline + 1, yycolumn + 1, (int) yychar);
        Location end = new Location(yyline + 1, yycolumn + yylength(), (int) (yychar + yylength()));

        return factory.newSymbol(name, type, start, end);
    }

    private Symbol symbol(String name, int type, Object value) {
        Location start = new Location(yyline + 1, yycolumn + 1, (int) yychar);
        Location end = new Location(yyline + 1, yycolumn + yylength(), (int) (yychar + yylength()));

        return factory.newSymbol(name, type, start, end, value);
    }

    private Symbol symbol(String name, int type, Object value, int buflen) {
        Location start = new Location(yyline + 1, yycolumn + yylength() - buflen, (int) (yychar + yylength() - buflen));
        Location end = new Location(yyline + 1, yycolumn + yylength(), (int) (yychar + yylength()));
        return factory.newSymbol(name, type, start, end, value);
    }

    public PLSQLScanner(java.io.Reader in, ComplexSymbolFactory factory) {
        this(in);
        this.factory = factory;
    }
%}

%eofval{
    Location start = new Location(yyline + 1, yycolumn + 1, (int) yychar);
    Location end = new Location(yyline + 1, yycolumn + 1, (int) (yychar + 1));
    return factory.newSymbol("EOF", sym.EOF, start, end);
%eofval}

EOL                 = \R
WHITE_SPACE         = \s+
NUMBER_LITERAL      = [+-]?((\d+(\.(\d+)?)?([eE][+-]?\d+)?)|(\.\d+([eE][+-]?\d+)?))
IDENTIFIER          = [A-Za-z_$][\w_$]*
NAMED_CURSOR        = :{IDENTIFIER}
LABEL               = "<<" {IDENTIFIER} ">>"
SINGLE_LINE_COMMENT = "--" [^\r\n]* (\r|\n|\r\n)?
MULTILINE_COMMENT   = "/*" ~"*/"
COMMENT             = {SINGLE_LINE_COMMENT} | {MULTILINE_COMMENT}
DELIMITER           = .

%state SQ_STRING_LITERAL
%state DQ_STRING_LITERAL
%state CS_STRING_LITERAL

%%

<YYINITIAL> {
  /* keywords */
  "BEGIN"  { return symbol("begin", sym.BEGIN); }
  "END"    { return symbol("end", sym.END); }
  "LOOP"   { return symbol("loop", sym.LOOP); }
  "IF"     { return symbol("if", sym.IF); }
  "THEN"   { return symbol("then", sym.THEN); }
  "ELSIF"  { return symbol("elsif", sym.ELSIF); }
  "ELSE"   { return symbol("else", sym.ELSE); }
  "RETURN" { return symbol("return", sym.RETURN); }

  /* separators */
  ";" { return symbol("semi", sym.SEMI); }
  "," { return symbol("comma", sym.COMMA); }
  "." { return symbol("dot", sym.DOT); }
  "(" { return symbol("(", sym.LEFT_PARENTHESIS); }
  ")" { return symbol(")", sym.RIGHT_PARENTHESIS); }

  /* operators */
  ":="                      { return symbol("assign", sym.ASSIGNMENT_OPERATOR); }
  "="                       { return symbol("eq", sym.EQUAL_OPERATOR); }
  "<>" | "!=" | "~=" | "^=" { return symbol("neq", sym.NOT_EQUAL_OPERATOR); }
  "<"                       { return symbol("le", sym.LESS_THAN_OPERATOR); }
  ">"                       { return symbol("gt", sym.GREATER_THAN_OPERATOR); }
  "<="                      { return symbol("leq", sym.LESS_THAN_OR_EQUAL_OPERATOR); }
  ">="                      { return symbol("gtq", sym.GREATER_THAN_OR_EQUAL_OPERATOR); }
  "||"                      { return symbol("concat", sym.CONCATENATION_OPERATOR); }
  "+"                       { return symbol("add", sym.ADDITION_OPERATOR); }
  "-"                       { return symbol("sub", sym.SUBTRACTION_OPERATOR); }
  "*"                       { return symbol("mult", sym.MULTIPLICATION_OPERATOR); }
  "/"                       { return symbol("div", sym.DIVISION_OPERATOR); }
  "NOT"                     { return symbol("not", sym.NOT_OPERATOR); }
  "AND"                     { return symbol("booloperator", sym.AND_OPERATOR); }
  "OR"                      { return symbol("booloperator", sym.OR_OPERATOR); }
  "**"                      { return symbol("exp", sym.EXPONENTIATION_OPERATOR); }

  /* literals */
  "TRUE"              { return symbol("boolliteral", sym.BOOLEAN_LITERAL, true); }
  "FALSE"             { return symbol("boolliteral", sym.BOOLEAN_LITERAL, false); }
  "NULL"              { return symbol("null", sym.NULL_LITERAL); }
  {NUMBER_LITERAL}    { return symbol("number", sym.NUMBER_LITERAL, yytext()); }
  \'                  { string.setLength(0); yybegin(SQ_STRING_LITERAL); }
  \"                  { string.setLength(0); yybegin(DQ_STRING_LITERAL); }
  q\'{DELIMITER}      { string.setLength(0); this.setDelimiter(yytext().substring(2)); yybegin(CS_STRING_LITERAL); }

  /* identifiers */
  {NAMED_CURSOR} { return symbol("namedcursor", sym.NAMED_CURSOR, yytext().substring(1)); }
  {IDENTIFIER}   { return symbol("identifier", sym.IDENTIFIER, new Identifier(yytext())); }
  {LABEL}        { String label = yytext(); return symbol("label", sym.LABEL, label.substring(2, label.length() - 2)); }

  /* misc */
  {WHITE_SPACE} { /* ignore */ }
  {COMMENT}     { /* ignore */ }
}

/* single quotes string literal */
<SQ_STRING_LITERAL> {
  \' { yybegin(YYINITIAL); return symbol("string", sym.STRING_LITERAL, string.toString(), string.length()); }
  [^\n\r\'\\]+ { string.append(yytext()); }
  \\n { string.append('\n'); }
  \\r { string.append('\r'); }
  \'\' { string.append('\''); }
  \\\\ { string.append('\\'); }
}

/* double quotes string literal */
<DQ_STRING_LITERAL> {
  \" { yybegin(YYINITIAL); return symbol("string", sym.STRING_LITERAL, string.toString(), string.length()); }
  [^\n\r\"\\]+ { string.append(yytext()); }
  \\n { string.append('\n'); }
  \\r { string.append('\r'); }
  \"\" { string.append('"'); }
  \\\\ { string.append('\\'); }
}

/* custom delimiter string literal */
<CS_STRING_LITERAL> {
  [^]\' {
      if (yytext().substring(0, 1).equals(customDelimiter)) {
          yybegin(YYINITIAL);
          return symbol("string", sym.STRING_LITERAL, string.toString(), string.length());
      } else {
          string.append(yytext());
      }
 }

 [^] { string.append(yytext()); }
}
[^] { throw new Error("Illegal character '" + yytext() + "' on line " + yyline + " column " + yycolumn); }
