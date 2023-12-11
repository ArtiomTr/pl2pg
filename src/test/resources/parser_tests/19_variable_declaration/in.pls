/*
 * Program for checking parsing of variable declaration.
 * @see [Variable declaration in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/scalar-variable-declaration.html)
 */
DECLARE
    a some_type;
    a some_type NOT NULL DEFAULT 12 * 13;
BEGIN
    a := 1;
END;
