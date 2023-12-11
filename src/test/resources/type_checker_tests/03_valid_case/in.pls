DECLARE
    a VARCHAR2;
BEGIN
    CASE 1
        WHEN 2 THEN a := '2';
        WHEN 3 THEN a := '3';
        ELSE a := 'other';
    END CASE;

    CASE
        WHEN a = '2' THEN a := '3';
        WHEN a = '3' THEN a := '4';
        ELSE a := 'other';
    END CASE;
END;
