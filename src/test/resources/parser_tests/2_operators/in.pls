/*
 * Program for checking PL/SQL parser ability to recognize various operators
 */
BEGIN
    -- Boolean operators
    or_op := TRUE OR B;
    and_op := TRUE AND FALSE;
    not_op := NOT TRUE;
    not_op_2 := NOT (NOT FALSE);

    -- String operators
    concatenation := 'Hello' || q'['world'!]';

    -- Comparison operators
    lt := 5.3 < 4;
    lte := .3 <= 83;
    gt := 3e8 > 2;
    gte := 182 >= -12.3e8;
    eq := 4 = 1;
    ne := 18 != 3;
    ne_2 := 2.3 <> .28;
    ne_3 := 123 ~= .3e-12;
    ne_4 := 0 ^= 0.1e-8;

    -- Arithmetic operators
    addition := 12.34 + -3.2E-8;
    subtraction := 8.32e-4 - b;
    multiplication := 2e-3 * .30;
    division := 23 / 3.;
    negation := -a;
    negation_2 := - - -a;
    identity := +some_identifier;
    exponentiation := 2 ** 3.8e-12;
END;
