DECLARE
    a VARCHAR2;
BEGIN
    CASE
        WHEN 1 + 1 THEN a := '3';
        WHEN a = '3' THEN a := '4';
        ELSE a := 'other';
    END CASE;
END;
