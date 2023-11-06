# Tests

Tests are written using [JUnit](https://junit.org/junit5/) framework. Subdirectory `./java` contains test source code.

Subdirectory `./resources` contains test cases:
- `./resources/parser_tests` is root directory for [Parser](../main/java/org/example/plsql/main/Parser.java) test cases.
  Each subdirectory in `parser_tests` must contain two files: `in.pls` and `out.json`.
  
  - `in.pls` — program written in PL/SQL, that will be parsed
  - `out.json` — expected abstract syntax tree root node ([Program](../main/java/org/example/plsql/ast/Program.java)) 
    or `null`.
  
  All other files will be ignored.

  For each subdirectory, file `out.json` is parsed using [gson](https://github.com/google/gson) library. Then, `in.pls`
  file is opened and passed to the parser. Output of parser is compared with expected output from `out.json` file.

  If `out.json` file contains `null`, then it means that content in `in.pls` file is not a valid PL/SQL program, so
  Parser must throw error.
