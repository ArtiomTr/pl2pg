/*
 * Program for checking parsing of record type definition.
 * This is not a valid PL/SQL program, so parser must throw error.
 */
DECLARE
    -- Record type definition must contain at least one field definition
    TYPE second IS RECORD ();
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
