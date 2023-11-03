/*
 * Program for checking PL/SQL parser operator precedence
 */
BEGIN
    -- Boolean operators
    a := TRUE OR FALSE AND TRUE;   -- AND operator has higher precedence
    b := NOT TRUE AND NOT FALSE;   -- NOT operator has higher precedence
    c := NOT (TRUE AND NOT FALSE); -- Parentheses alter default operator precedence

    -- Arithmetic operators
    d := 5 + 3 * 8;                -- Multiplication has higher precedence
    e := 6 / 3 ** 8;               -- Exponentiation has higher precedence
    f := 117 + -id;                -- Negation has higher precedence
    g := +a * -c;                  -- Identity and negation have higher precedence
    h := -(a + c) * 3;             -- Parentheses alter default operator precedence

    -- Comparison operators
    i := 5 < 6 AND 3 >= 4;         -- AND operator has lower precedence
    j := id <> 8 OR id = 6;        -- OR operator has lower precedence
END;
