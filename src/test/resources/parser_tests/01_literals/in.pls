/*
 * Program for checking PL/SQL parser ability to recognize various literals
 */
BEGIN
    -- Checking string literals
    double_quote_string_literal := "Hello, ""world""!";
    single_quote_string_literal := 'Hello, ''world''!';
    custom_delimiter_string_literal_1 := q'#Hello, 'world"!#';
    custom_delimiter_string_literal_2 := q'!Hello, "world'!';
    custom_delimiter_string_literal_3 := q'{Hello, "world'!}';
    null_string_literal := '';

    -- Checking boolean literals
    a := TRUE;
    b := FALSE;

    -- Checking null literal
    c := NULL;

    -- Checking numeric literals
    integer_1 := 147;
    integer_2 := -15;
    integer_3 := +15;
    real_1 := 1.123;
    real_2 := +0.0015;
    real_3 := -173.12;
    real_4 := 19.;
    real_5 := .1;
    real_6 := -.47;
    real_7 := +156.;
    scientific_1 := 12e19;
    scientific_2 := -15E8;
    scientific_3 := 1e-8;
    scientific_4 := 93e+8;
    scientific_5 := 1.152e-2;
    scientific_6 := .3E+12;
    scientific_7 := 12.e3;
END;

