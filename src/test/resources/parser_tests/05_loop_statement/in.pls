/*
 * Program for checking LOOP statement parsing
 */
BEGIN
    -- Basic loop
    LOOP
        x := 5;
    END LOOP;

    -- Loop with no statements
    LOOP
    END LOOP;

    -- Multiple statements in loop
    LOOP
        x := 5;
        x := x * 5 ** 6 - 3;
    END LOOP;

    -- Nested loop
    LOOP
        i := 1;
        LOOP
            i := i + 1;
        END LOOP;
    END LOOP;
END;
