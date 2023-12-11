DECLARE
    a VARCHAR := NULL;
BEGIN
    CASE
        WHEN a = '' THEN
            a := 'some value';
        ELSE
            a := a || NULL;
    END CASE;
END;
