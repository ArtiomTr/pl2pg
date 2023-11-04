/*
 * Program for checking CASE statement parsing
 */
BEGIN
    -- Simple case
    CASE x
        WHEN 15 THEN x := 10;
    END CASE;

    -- Searched case
    CASE
        WHEN x = 15 THEN x := 10;
    END CASE;

    -- Complex expressions
    CASE x ** 2 > 15 * 3 / 4 - 5
        WHEN 3 * 82 * b THEN x := 10;
    END CASE;

    -- Simple case with else
    CASE x
        WHEN 15 THEN x := 10;
        ELSE x := 2;
    END CASE;

    -- Searched case with else
    CASE
        WHEN x = 15 THEN x := 10;
        ELSE x := 2;
    END CASE;

    -- Multiple whens
    CASE x
        WHEN 2 THEN x := 1;
        WHEN 3 THEN x := 2;
        WHEN 4 THEN x := 3;
        WHEN 5 THEN x := 4;
    END CASE;

    -- Multiple whens (searched case)
    CASE
        WHEN x <> 3 THEN x := 3;
        WHEN y <> 4 THEN x := 4;
        WHEN z <> 6 THEN x := 5;
    END CASE;

    -- Simple case with whens and else
    CASE x
        WHEN 2 THEN x := 1;
        WHEN 3 THEN x := 2;
        ELSE x := 3;
    END CASE;

    -- Searched case with whens and else
    CASE
        WHEN x <> 2 THEN x := 3;
        WHEN y <> 4 THEN x := 4;
        ELSE x := 5;
    END CASE;
END;
