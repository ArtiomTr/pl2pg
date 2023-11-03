/*
 * Program for checking PL/SQL parser operator associativity
 */
BEGIN
    -- Boolean operators
    a := TRUE OR FALSE OR NULL OR someval;    -- OR operator is left-associative
    b := TRUE AND FALSE AND NULL AND someval; -- AND operator is left-associative

    -- String operators
    c := 'Hello' || q'!'world'!' || '!';      -- Concatenation operator is left-associative

    -- Arithmetic operators
    d := 1 + 5 + 7 + 8;                       -- Addition operator is left-associative
    e := 1 ** 2 - 2 ** 2 - 3 ** 2;            -- Subtraction operator is left-associative
    f := 3 ** (1 + 4 - 6 - 7 + 8);            -- Both addition and subtraction are left-associative, and have same precedence
    g := (4 * 2 / 3 * d) + 1;                 -- Both multiplication and division have same precedence and associativity (left)
END;
