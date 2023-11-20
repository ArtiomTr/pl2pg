/*
 * Program for checking parsing of ref cursor type definition.
 * @see [Ref cursor type definition in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/cursor-variable-declaration.html#GUID-CE884B31-07F0-46AA-8067-EBAF73821F3D__CJAIGBFF)
 */
DECLARE
    -- Testing ref cursor type definition
    TYPE first IS REF CURSOR;
    -- Testing ref cursor type definition with return type
    TYPE second IS REF CURSOR RETURN some_type;
    -- Testing ref cursor type definition with return type attribute
    TYPE third IS REF CURSOR RETURN some_record%TYPE;
    -- Testing ref cursor type definition with return rowtype attribute
    TYPE fourth IS REF CURSOR RETURN cursor_variable%ROWTYPE;
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
