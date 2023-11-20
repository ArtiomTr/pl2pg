/*
 * Program for checking labels parsing
 */
BEGIN
    -- Labeling statement
    <<hello>>
    a := 10 + 15;

    -- Multiple labels on statement
    <<stmt_label_1>>
    <<stmt_label_2>>
    <<stmt_label_3>>
    b := 4;

    -- Labeling block
    <<block_label>>
    BEGIN
        a := 2;
    -- Closing block with label
    END block_label;
END;
