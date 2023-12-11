# Tests

Tests are written using [JUnit](https://junit.org/junit5/) framework. Subdirectory `./java` contains test source code.

Subdirectory `./resources` contains test cases:
- `./resources/parser_tests` is root directory for [Parser](../main/java/org/example/oracle/main/Parser.java) test cases.
  Each subdirectory in `parser_tests` must contain two files: `in.pls` and `out.json`.
  
  - `in.pls` — program written in PL/SQL, that will be parsed
  - `out.json` — expected abstract syntax tree root node ([Program](../main/java/org/example/oracle/ast/Program.java)) 
    or `null`.
  
  All other files will be ignored.

  For each subdirectory, file `out.json` is parsed using [gson](https://github.com/google/gson) library. Then, `in.pls`
  file is opened and passed to the parser. Output of parser is compared with expected output from `out.json` file.

  If `out.json` file contains `null`, then it means that content in `in.pls` file is not a valid PL/SQL program, so
  Parser must throw error.
- `./resources/type_checker_tests` is root directory for [TypeChecker](../main/java/org/example/oracle/main/TypeChecker.java) test cases.
  Each subdirectory in `type_checker_tests` must contain two files: `in.pls` and `out.json`.

  - `in.pls` — program written in PL/SQL, that will be type checked
  - `out.json` — expected TypeChecker output - `true` if code is valid, and `false` otherwise.

  All other files will be ignored.

  For each subdirectory, file `out.json` is parsed using [gson](https://github.com/google/gson) library. Then, `in.pls`
  file is opened and passed to the parser, and then to the type checker.
- `./resources/generator_tests` is root directory for [Generator](../main/java/org/example/postgres/generation/Generator.java) test cases.
  Each subdirectory in `generator_tests` must contain two files: `in.pls` and `out.psql`.

  - `in.pls` — program written in PL/SQL, that will be converted
  - `out.psql` — expected PL/pgSQL program 

  All other files will be ignored.

  For each subdirectory, `in.pls` file is opened and passed to the generator. Output of generator is compared with 
  expected output from `out.psql` file.
