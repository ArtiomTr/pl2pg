package org.example;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static generated.GeneratedTypes.*;

%%

%{
  public _PlsqlLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _PlsqlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+


%%
<YYINITIAL> {
  {WHITE_SPACE}                                      { return WHITE_SPACE; }

  "<program>"                                        { return <PROGRAM>; }
  "<plsql_block>"                                    { return <PLSQL_BLOCK>; }
  "<label_block>"                                    { return <LABEL_BLOCK>; }
  "<declaration_block>"                              { return <DECLARATION_BLOCK>; }
  "<body>"                                           { return <BODY>; }
  "E"                                                { return E; }
  "<label>"                                          { return <LABEL>; }
  "<opt_whitespace>"                                 { return <OPT_WHITESPACE>; }
  "<identifier>"                                     { return <IDENTIFIER>; }
  "<declare_section>"                                { return <DECLARE_SECTION>; }
  "<whitespace>"                                     { return <WHITESPACE>; }
  "<item_list_1>"                                    { return <ITEM_LIST_1>; }
  "<item_list_2>"                                    { return <ITEM_LIST_2>; }
  "<item_list_1_sub>"                                { return <ITEM_LIST_1_SUB>; }
  "<type_definition>"                                { return <TYPE_DEFINITION>; }
  "<cursor_declaration>"                             { return <CURSOR_DECLARATION>; }
  "<item_declaration>"                               { return <ITEM_DECLARATION>; }
  "<function_declaration>"                           { return <FUNCTION_DECLARATION>; }
  "<procedure_declaration>"                          { return <PROCEDURE_DECLARATION>; }
  "<item_list_2_sub>"                                { return <ITEM_LIST_2_SUB>; }
  "<cursor_definition>"                              { return <CURSOR_DEFINITION>; }
  "<function_definition>"                            { return <FUNCTION_DEFINITION>; }
  "<procedure_definition>"                           { return <PROCEDURE_DEFINITION>; }
  "<statement_list>"                                 { return <STATEMENT_LIST>; }
  "<opt_label>"                                      { return <OPT_LABEL>; }
  "<statement>"                                      { return <STATEMENT>; }
  "<statement_atom>"                                 { return <STATEMENT_ATOM>; }
  "<assignment_statement>"                           { return <ASSIGNMENT_STATEMENT>; }
  "<basic_loop_statement>"                           { return <BASIC_LOOP_STATEMENT>; }
  "<if_statement>"                                   { return <IF_STATEMENT>; }
  "<return_statement>"                               { return <RETURN_STATEMENT>; }
  "<boolean_expression>"                             { return <BOOLEAN_EXPRESSION>; }
  "<opt_elsif_statement_list>"                       { return <OPT_ELSIF_STATEMENT_LIST>; }
  "<opt_else_statement>"                             { return <OPT_ELSE_STATEMENT>; }
  "<elsif_statement_list>"                           { return <ELSIF_STATEMENT_LIST>; }
  "<elsif_statement>"                                { return <ELSIF_STATEMENT>; }
  "<expression>"                                     { return <EXPRESSION>; }
  "<assignment_statement_target>"                    { return <ASSIGNMENT_STATEMENT_TARGET>; }
  "<index>"                                          { return <INDEX>; }
  "<placeholder>"                                    { return <PLACEHOLDER>; }
  "<character_expression>"                           { return <CHARACTER_EXPRESSION>; }
  "<collection_constructor>"                         { return <COLLECTION_CONSTRUCTOR>; }
  "<date_expression>"                                { return <DATE_EXPRESSION>; }
  "<searched_case_expression>"                       { return <SEARCHED_CASE_EXPRESSION>; }
  "<simple_case_expression>"                         { return <SIMPLE_CASE_EXPRESSION>; }
  "<opt_boolean_not>"                                { return <OPT_BOOLEAN_NOT>; }
  "<boolean_expression_atom>"                        { return <BOOLEAN_EXPRESSION_ATOM>; }
  "<boolean_expression_right_operands>"              { return <BOOLEAN_EXPRESSION_RIGHT_OPERANDS>; }
  "<binary_boolean_operator>"                        { return <BINARY_BOOLEAN_OPERATOR>; }
  "<unary_boolean_operator>"                         { return <UNARY_BOOLEAN_OPERATOR>; }
  "<boolean_literal>"                                { return <BOOLEAN_LITERAL>; }
  "<function_call>"                                  { return <FUNCTION_CALL>; }
  "<conditional_predicate>"                          { return <CONDITIONAL_PREDICATE>; }
  "<other_boolean_form>"                             { return <OTHER_BOOLEAN_FORM>; }
  "<boolean_expression_collection>"                  { return <BOOLEAN_EXPRESSION_COLLECTION>; }
  "<boolean_expression_null_check>"                  { return <BOOLEAN_EXPRESSION_NULL_CHECK>; }
  "<boolean_expression_between>"                     { return <BOOLEAN_EXPRESSION_BETWEEN>; }
  "<boolean_expression_in>"                          { return <BOOLEAN_EXPRESSION_IN>; }
  "<boolean_expression_like>"                        { return <BOOLEAN_EXPRESSION_LIKE>; }
  "<boolean_expression_relational_operator>"         { return <BOOLEAN_EXPRESSION_RELATIONAL_OPERATOR>; }
  "<boolean_expression_sql>"                         { return <BOOLEAN_EXPRESSION_SQL>; }
  "<in_expression_arguments>"                        { return <IN_EXPRESSION_ARGUMENTS>; }
  "<boolean_expression_sql_operator>"                { return <BOOLEAN_EXPRESSION_SQL_OPERATOR>; }
  "<named_cursor>"                                   { return <NAMED_CURSOR>; }
  "<pattern>"                                        { return <PATTERN>; }
  "<pattern_content>"                                { return <PATTERN_CONTENT>; }
  "<pattern_characters>"                             { return <PATTERN_CHARACTERS>; }
  "<letter>"                                         { return <LETTER>; }
  "<digit>"                                          { return <DIGIT>; }
  "<relational_operator>"                            { return <RELATIONAL_OPERATOR>; }
  "<character_expression_atom>"                      { return <CHARACTER_EXPRESSION_ATOM>; }
  "<character_literal>"                              { return <CHARACTER_LITERAL>; }
  "<collection_constructor_values>"                  { return <COLLECTION_CONSTRUCTOR_VALUES>; }
  "<value>"                                          { return <VALUE>; }
  "<date_expression_atom>"                           { return <DATE_EXPRESSION_ATOM>; }
  "<date_subexpression>"                             { return <DATE_SUBEXPRESSION>; }
  "<date_binary_operator>"                           { return <DATE_BINARY_OPERATOR>; }
  "<numeric_expression>"                             { return <NUMERIC_EXPRESSION>; }
  "<date_literal>"                                   { return <DATE_LITERAL>; }
  "<numeric_subexpression>"                          { return <NUMERIC_SUBEXPRESSION>; }
  "<numeric_subexpression_right>"                    { return <NUMERIC_SUBEXPRESSION_RIGHT>; }
  "<numeric_binary_operator>"                        { return <NUMERIC_BINARY_OPERATOR>; }
  "<numeric_opt_unary_operator>"                     { return <NUMERIC_OPT_UNARY_OPERATOR>; }
  "<numeric_subexpression_atom>"                     { return <NUMERIC_SUBEXPRESSION_ATOM>; }
  "<numeric_opt_exponent_expression>"                { return <NUMERIC_OPT_EXPONENT_EXPRESSION>; }
  "<numeric_collection_expression>"                  { return <NUMERIC_COLLECTION_EXPRESSION>; }
  "<exponent>"                                       { return <EXPONENT>; }
  "<searched_case_expression_where_clause_list>"     { return <SEARCHED_CASE_EXPRESSION_WHERE_CLAUSE_LIST>; }
  "<case_else_clause_expression>"                    { return <CASE_ELSE_CLAUSE_EXPRESSION>; }
  "<searched_case_expression_where_clause>"          { return <SEARCHED_CASE_EXPRESSION_WHERE_CLAUSE>; }
  "<result>"                                         { return <RESULT>; }
  "<simple_case_expression_where_clause_list>"       { return <SIMPLE_CASE_EXPRESSION_WHERE_CLAUSE_LIST>; }
  "<simple_case_expression_where_clause>"            { return <SIMPLE_CASE_EXPRESSION_WHERE_CLAUSE>; }
  "<function_call_parameters_array>"                 { return <FUNCTION_CALL_PARAMETERS_ARRAY>; }
  "<function_call_parameter>"                        { return <FUNCTION_CALL_PARAMETER>; }
  "<numeric_literal>"                                { return <NUMERIC_LITERAL>; }
  "<identifier_body>"                                { return <IDENTIFIER_BODY>; }
  "<identifier_body_symbol>"                         { return <IDENTIFIER_BODY_SYMBOL>; }


}

[^] { return BAD_CHARACTER; }
