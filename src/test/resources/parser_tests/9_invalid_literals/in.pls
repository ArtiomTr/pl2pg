/*
 * Program for checking validity of numeric literal.
 *
 * This is not a valid PL/SQL program, so parser must throw error.
 */
BEGIN
    -- Missing exponent, so that's not a valid numeric literal
    x := .10e;
END;
