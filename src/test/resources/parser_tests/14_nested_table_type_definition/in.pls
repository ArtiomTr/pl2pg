/*
 * Program for checking parsing of nested table type definition.
 * @see [Nested table type definition in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/collection-variable.html#GUID-89A1863C-65A1-40CF-9392-86E9FDC21BE9__GUID-FA2DEF25-017F-4F7B-9F78-6DD14251B1F8)
 */
DECLARE
    -- Testing nested table definition
    TYPE first IS TABLE OF sometype;
    -- Testing not null
    TYPE second IS TABLE OF sometype NOT NULL;
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
