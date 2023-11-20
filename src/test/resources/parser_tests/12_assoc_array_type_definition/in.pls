/*
 * Program for checking parsing of associative array type definition.
 * @see [Associative array type definition in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/collection-variable.html#GUID-89A1863C-65A1-40CF-9392-86E9FDC21BE9__GUID-EB11C413-8F86-4990-BF6E-FFD34729537F)
 */
DECLARE
    -- Testing type reference
    TYPE first IS TABLE OF record_type INDEX BY PLS_INTEGER;
    -- Testing object type reference
    TYPE second IS TABLE OF REF object_type INDEX BY BINARY_INTEGER;
    -- Testing type attribute operator
    TYPE third IS TABLE OF some_record%TYPE INDEX BY LONG;
    -- Testing rowtype attribute operator
    TYPE fourth IS TABLE OF some_collection%ROWTYPE INDEX BY STRING(10);
    -- Testing not null
    TYPE fifth IS TABLE OF record_type NOT NULL INDEX BY VARCHAR(15);
    -- Testing not null with ref
    TYPE sixth IS TABLE OF REF object_type NOT NULL INDEX BY VARCHAR2(1);
    -- Testing index by type attribute
    TYPE seventh IS TABLE OF record_type INDEX BY some_var%TYPE;
    -- Testing index by rowtype attribute
    TYPE eighth IS TABLE OF record_type INDEX BY some_collection%ROWTYPE;
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
