DECLARE
    a VARCHAR2;
BEGIN
    CASE 1
        WHEN 'asdf' THEN a := '2';
        WHEN TRUE THEN a := '3';
        ELSE a := 'other';
    END CASE;
END;
