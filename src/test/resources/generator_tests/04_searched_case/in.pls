DECLARE
    a VARCHAR := NULL;
    b INT := 0;
BEGIN
    CASE
        WHEN a = '' THEN
            b := 12;
        ELSE
            a := a || NULL;
    END CASE;
END;
