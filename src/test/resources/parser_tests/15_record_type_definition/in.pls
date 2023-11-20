/*
 * Program for checking parsing of record type definition.
 * @see [Record type definition in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/record-variable-declaration.html#GUID-704FC014-561E-422C-9636-EDCA3B996AAD__GUID-61F643E2-3167-4451-90BB-7779D5F5E32C)
 */
DECLARE
    -- Testing record type definition
    TYPE first IS RECORD (
        -- Field definition
        value  some_type,
        -- Field with initial value
        value2 some_type := 12,
        -- Field with default value
        value3 some_type DEFAULT NULL,
        -- Field with non-nullable value
        value4 some_type NOT NULL := 3,
        -- Field with expression, as default value
        value5 some_type NOT NULL := 4 ** 2 - 3 + -a
    );
    -- Testing record type definition, with comma after field definitions
    TYPE second IS RECORD (
        value some_type, -- optional comma at the end of list
    );
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
