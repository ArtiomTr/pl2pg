package org.example.oracle.main.generated;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import org.example.oracle.ast.*;

%%

%public
%class Scanner
%unicode
%line
%column
%char
%cup
%caseless

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

    public Scanner(java.io.Reader in, ComplexSymbolFactory factory) {
        this(in);
        this.factory = factory;
    }
%}

%eofval{
    Location start = new Location(yyline + 1, yycolumn + 1, (int) yychar);
    Location end = new Location(yyline + 1, yycolumn + 1, (int) (yychar + 1));
    return factory.newSymbol("EOF", PlSymbol.EOF, start, end);
%eofval}

EOL                             = \R
WHITE_SPACE                     = \s+
NUMBER_LITERAL                  = [+-]?((\d+\.[eE][+-]?\d+)|(\d+(\.\d+)?([eE][+-]?\d+)?)|(\.\d+([eE][+-]?\d+)?))
NUMBER_WITHOUT_FRACTION_LITERAL = [+-]?(\d+\.)
IDENTIFIER                      = [A-Za-z_$][\w_$]*
NAMED_CURSOR                    = :{IDENTIFIER}
LABEL                           = "<<" {IDENTIFIER} ">>"
SINGLE_LINE_COMMENT             = "--" [^\r\n]* (\r|\n|\r\n)?
MULTILINE_COMMENT               = "/*" ~"*/"
COMMENT                         = {SINGLE_LINE_COMMENT} | {MULTILINE_COMMENT}
DELIMITER                       = .

%state SQ_STRING_LITERAL
%state DQ_STRING_LITERAL
%state CS_STRING_LITERAL

%%

<YYINITIAL> {
  /* keywords */
  "BEGIN"          { return symbol("begin", PlSymbol.BEGIN); }
  "END"            { return symbol("end", PlSymbol.END); }
  "LOOP"           { return symbol("loop", PlSymbol.LOOP); }
  "IF"             { return symbol("if", PlSymbol.IF); }
  "THEN"           { return symbol("then", PlSymbol.THEN); }
  "ELSIF"          { return symbol("elsif", PlSymbol.ELSIF); }
  "ELSE"           { return symbol("else", PlSymbol.ELSE); }
  "RETURN"         { return symbol("return", PlSymbol.RETURN); }
  "SQL"            { return symbol("implicitcursor", PlSymbol.IMPLICIT_CURSOR); }
  "CASE"           { return symbol("case", PlSymbol.CASE); }
  "WHEN"           { return symbol("when", PlSymbol.WHEN); }
  "DECLARE"        { return symbol("declare", PlSymbol.DECLARE); }
  "TYPE"           { return symbol("type", PlSymbol.TYPE); }
  "SUBTYPE"        { return symbol("subtype", PlSymbol.SUBTYPE); }
  "RANGE"          { return symbol("range", PlSymbol.RANGE); }
  "CHARACTER"      { return symbol("character", PlSymbol.CHARACTER); }
  "SET"            { return symbol("set", PlSymbol.SET); }
  "TABLE"          { return symbol("table", PlSymbol.TABLE); }
  "OF"             { return symbol("of", PlSymbol.OF); }
  "INDEX"          { return symbol("index", PlSymbol.INDEX); }
  "BY"             { return symbol("by", PlSymbol.BY); }
  "RECORD"         { return symbol("record", PlSymbol.RECORD); }
  "REF"            { return symbol("ref", PlSymbol.REF); }
  "CURSOR"         { return symbol("cursor", PlSymbol.CURSOR); }
  "VARRAY"         { return symbol("varray", PlSymbol.VARRAY); }
  "ARRAY"          { return symbol("array", PlSymbol.ARRAY); }
  "VARYING"        { return symbol("varying", PlSymbol.VARYING); }
  "DEFAULT"        { return symbol("default", PlSymbol.DEFAULT); }

  /* separators */
  ";" { return symbol("semi", PlSymbol.SEMI); }
  "," { return symbol("comma", PlSymbol.COMMA); }
  "." { return symbol("dot", PlSymbol.DOT); }
  "(" { return symbol("(", PlSymbol.LEFT_PARENTHESIS); }
  ")" { return symbol(")", PlSymbol.RIGHT_PARENTHESIS); }

  /* operators */
  ":="                      { return symbol("assign", PlSymbol.ASSIGNMENT_OPERATOR); }
  "="                       { return symbol("eq", PlSymbol.EQUAL_OPERATOR); }
  "<>" | "!=" | "~=" | "^=" { return symbol("neq", PlSymbol.NOT_EQUAL_OPERATOR); }
  "<"                       { return symbol("le", PlSymbol.LESS_THAN_OPERATOR); }
  ">"                       { return symbol("gt", PlSymbol.GREATER_THAN_OPERATOR); }
  "<="                      { return symbol("leq", PlSymbol.LESS_THAN_OR_EQUAL_OPERATOR); }
  ">="                      { return symbol("gtq", PlSymbol.GREATER_THAN_OR_EQUAL_OPERATOR); }
  "||"                      { return symbol("concat", PlSymbol.CONCATENATION_OPERATOR); }
  "+"                       { return symbol("add", PlSymbol.ADDITION_OPERATOR); }
  "-"                       { return symbol("sub", PlSymbol.SUBTRACTION_OPERATOR); }
  "*"                       { return symbol("mult", PlSymbol.MULTIPLICATION_OPERATOR); }
  "/"                       { return symbol("div", PlSymbol.DIVISION_OPERATOR); }
  "NOT"                     { return symbol("not", PlSymbol.NOT_OPERATOR); }
  "AND"                     { return symbol("booloperator", PlSymbol.AND_OPERATOR); }
  "OR"                      { return symbol("booloperator", PlSymbol.OR_OPERATOR); }
  "**"                      { return symbol("exp", PlSymbol.EXPONENTIATION_OPERATOR); }
  "EXISTS"                  { return symbol("exists", PlSymbol.EXISTS_OPERATOR); }
  "BETWEEN"                 { return symbol("between", PlSymbol.BETWEEN_OPERATOR); }
  "IS"                      { return symbol("is", PlSymbol.IS_OPERATOR); }
  ".."                      { return symbol("rangeoperator", PlSymbol.RANGE_OPERATOR); }
  "%ROWTYPE"                { return symbol("rowtype", PlSymbol.ROWTYPE_ATTRIBUTE_OPERATOR); }
  "%TYPE"                   { return symbol("type", PlSymbol.TYPE_ATTRIBUTE_OPERATOR); }

  /* cursor operators */
  "%FOUND"                  { return symbol("found", PlSymbol.FOUND_OPERATOR); }
  "%ISOPEN"                 { return symbol("isopen", PlSymbol.ISOPEN_OPERATOR); }
  "%NOTFOUND"               { return symbol("notfound", PlSymbol.NOTFOUND_OPERATOR); }

  /* literals */
  "TRUE"                  { return symbol("boolliteral", PlSymbol.BOOLEAN_LITERAL, true); }
  "FALSE"                 { return symbol("boolliteral", PlSymbol.BOOLEAN_LITERAL, false); }
  "NULL"                  { return symbol("null", PlSymbol.NULL_LITERAL); }

  {NUMBER_WITHOUT_FRACTION_LITERAL}/[^.\d] { return symbol("number", PlSymbol.NUMBER_LITERAL, yytext()); }
  {NUMBER_LITERAL}        { return symbol("number", PlSymbol.NUMBER_LITERAL, yytext()); }
  \'                      { string.setLength(0); yybegin(SQ_STRING_LITERAL); }
  \"                      { string.setLength(0); yybegin(DQ_STRING_LITERAL); }
  q\'{DELIMITER}          { string.setLength(0); this.setDelimiter(yytext().substring(2)); yybegin(CS_STRING_LITERAL); }

  /* identifiers */
  {NAMED_CURSOR} { return symbol("namedcursor", PlSymbol.NAMED_CURSOR, yytext().substring(1)); }
  {IDENTIFIER}   { return symbol("identifier", PlSymbol.IDENTIFIER, new Identifier(yytext())); }
  {LABEL}        { String label = yytext(); return symbol("label", PlSymbol.LABEL, label.substring(2, label.length() - 2)); }

  /* misc */
  {WHITE_SPACE} { /* ignore */ }
  {COMMENT}     { /* ignore */ }
}

/* single quotes string literal */
<SQ_STRING_LITERAL> {
  \' { yybegin(YYINITIAL); return symbol("string", PlSymbol.STRING_LITERAL, string.toString(), string.length()); }
  [^\n\r\'\\]+ { string.append(yytext()); }
  \\n { string.append('\n'); }
  \\r { string.append('\r'); }
  \'\' { string.append('\''); }
  \\\\ { string.append('\\'); }
}

/* double quotes string literal */
<DQ_STRING_LITERAL> {
  \" { yybegin(YYINITIAL); return symbol("string", PlSymbol.STRING_LITERAL, string.toString(), string.length()); }
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
          return symbol("string", PlSymbol.STRING_LITERAL, string.toString(), string.length());
      } else {
          string.append(yytext());
      }
 }

 [^] { string.append(yytext()); }
}
[^] { throw new Error("Illegal character '" + yytext() + "' on line " + yyline + " column " + yycolumn); }
