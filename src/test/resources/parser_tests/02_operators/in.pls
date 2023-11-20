/*
 * Program for checking PL/SQL parser ability to recognize various operators
 */
BEGIN
    -- Boolean operators
    or_op := TRUE OR B;
    and_op := TRUE AND FALSE;
    not_op := NOT TRUE;
    not_op_2 := NOT (NOT FALSE);
    existence := coll.EXISTS(4);
    between_op := id BETWEEN 10 AND 20;
    not_between_op := 6 NOT BETWEEN -2.3 AND 5.32e8;
    is_op := id IS NULL;
    is_not_op := id IS NOT NULL;

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

    -- Cursor operators
    found := SQL%FOUND;
    found_named := :somecursor%FOUND;
    notfound := SQL%NOTFOUND;
    notfound_named := :somecursor%NOTFOUND;
    isopen := SQL%ISOPEN;
    isopen_named := :somecursor%ISOPEN;
END;
