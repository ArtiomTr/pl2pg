/*
 * Program for checking IF statement parsing
 */
BEGIN
    -- Empty if
    IF x THEN
    END IF;

    -- Simple condition
    IF x THEN
        x := 15;
    END IF;

    -- Complex condition
    IF (x ** 2) / 3 + 2 <> 4 * 15 THEN
        x := 2;
    END IF;

    -- With else block
    IF TRUE THEN
        x := 2;
    ELSE
        x := 3;
    END IF;

    -- With elsif block
    IF x = 2 THEN
        x := x + 1;
    ELSIF x < 2 THEN
        x := x - 1;
    END IF;

    -- Multiple elsif blocks
    IF x = 2 THEN
        x := 1;
    ELSIF x < 2 THEN
        x := 2;
    ELSIF x > 2 THEN
        x := 3;
    ELSIF x > 5 THEN
        x := 4;
    END IF;

    -- Multiple elsif blocks & else block
    IF x = 2 THEN
        x := 1;
    ELSIF x = 3 THEN
        x := 4;
    ELSIF x = 4 THEN
        x := 5;
    ELSE
        x := NULL;
    END IF;
END;
