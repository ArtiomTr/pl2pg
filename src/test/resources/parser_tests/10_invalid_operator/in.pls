/*
 * Program for checking correctness of cursor operator.
 * This is not a valid PL/SQL program, so parser must throw error.
 */
BEGIN
    -- There must be no space between % and EXISTS. With space, EXISTS is parsed as identifier, not a boolean operator.
    x := SQL% EXISTS;
END;
