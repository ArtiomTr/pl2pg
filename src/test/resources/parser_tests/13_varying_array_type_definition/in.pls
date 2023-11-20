/*
 * Program for checking parsing of varying array type definition.
 * @see [Varying array type definition in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/collection-variable.html#GUID-89A1863C-65A1-40CF-9392-86E9FDC21BE9__GUID-1EAAA857-9282-4A34-ADF0-DC25E3ECE6DA)
 */
DECLARE
    -- Testing VARRAY keyword
    TYPE first IS VARRAY(10) OF sometype;
    -- Testing VARYING ARRAY keyword
    TYPE second IS VARYING ARRAY(12) OF some_collection%ROWTYPE;
    -- Testing ARRAY keyword
    TYPE third IS ARRAY(0) OF REF object_type;
    -- Testing not null
    TYPE fourth IS ARRAY(0) OF sometype NOT NULL;
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
