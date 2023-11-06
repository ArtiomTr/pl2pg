/*
 * Program for checking correctness of identifier name.
 * This is not a valid PL/SQL program, so parser must throw error.
 */
BEGIN
    -- SQL is a reserved keyword, so it cannot be used as identifier name.
    SQL := 1 + 2;
END;
