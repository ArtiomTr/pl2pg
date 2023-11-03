BEGIN
    -- Checking binary operator on literals
    a := TRUE OR FALSE;

    -- Checking unary operator on literal
    b := NOT TRUE;

    -- Checking binary operator precedence
    c := FALSE OR TRUE AND NULL;
END;
