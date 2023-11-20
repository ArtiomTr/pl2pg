/*
 * Program for checking parsing of subtype definition.
 * @see [Subtype definition in PL/SQL language reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/lnpls/block.html#GUID-9ACEB9ED-567E-4E1A-A16A-B8B35214FC9D__CHDCIGAD)
 */
DECLARE
    -- Testing subtype definition
    SUBTYPE first IS some_base_type;
    -- Testing subtype definition with character set
    SUBTYPE second IS some_base_type CHARACTER SET 'utf-8';
    -- Testing subtype definition with precision
    SUBTYPE third IS some_base_type 5;
    -- Testing subtype definition with precision and scale
    SUBTYPE fourth IS some_base_type 5, 6;
    -- Testing subtype definition with range
    SUBTYPE fifth IS some_base_type RANGE 120..380;
    -- Testing subtype definition with not null
    SUBTYPE sixth IS some_base_type NOT NULL;
    -- Testing subtype definition with constraint and not null
    SUBTYPE seventh IS some_base_type 4, 3 NOT NULL;
BEGIN
    -- Dummy statement, as PL/SQL block cannot be empty
    smth := 1;
END;
