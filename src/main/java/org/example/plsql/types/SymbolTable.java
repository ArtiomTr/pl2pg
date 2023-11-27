package org.example.plsql.types;

import java.util.HashMap;

public class SymbolTable {

    private static final HashMap<String, Type> BUILTIN_TYPE_ALIASES;

    static {
        BUILTIN_TYPE_ALIASES = new HashMap<>();
        // TODO: depending on oracle version BINARY_INTEGER may be an alias to PLS_INTEGER
        BUILTIN_TYPE_ALIASES.put("BINARY_INTEGER", new NumericType.Builder(new BinaryIntegerType()).build());
        BUILTIN_TYPE_ALIASES.put("SIMPLE_INTEGER", new NumericType.Builder(new BinaryIntegerType()).nonNullable().build());
        BUILTIN_TYPE_ALIASES.put("NATURAL", new NumericType.Builder(new BinaryIntegerType()).minValue(0L).build());
        BUILTIN_TYPE_ALIASES.put("NATURALN", new NumericType.Builder(new BinaryIntegerType()).minValue(0L).nonNullable().build());
        BUILTIN_TYPE_ALIASES.put("POSITIVE", new NumericType.Builder(new BinaryIntegerType()).minValue(1L).nonNullable().build());
        BUILTIN_TYPE_ALIASES.put("POSITIVEN", new NumericType.Builder(new BinaryIntegerType()).minValue(1L).nonNullable().build());
        BUILTIN_TYPE_ALIASES.put("SIGNTYPE", new NumericType.Builder(new BinaryIntegerType()).minValue(-1L).maxValue(1L).build());

        BUILTIN_TYPE_ALIASES.put("PLS_INTEGER", new NumericType.Builder(new PlsIntegerType()).build());

        BUILTIN_TYPE_ALIASES.put("NUMBER", new NumericType.Builder(new NumberType(null, null)).build());
        BUILTIN_TYPE_ALIASES.put("DEC", new NumericType.Builder(new NumberType(38, 0)).build());
        BUILTIN_TYPE_ALIASES.put("DECIMAL", new NumericType.Builder(new NumberType(38, 0)).build());
        BUILTIN_TYPE_ALIASES.put("NUMERIC", new NumericType.Builder(new NumberType(38, 0)).build());
        BUILTIN_TYPE_ALIASES.put("FLOAT", new NumericType.Builder(new NumberType(38, null)).build());
        BUILTIN_TYPE_ALIASES.put("DOUBLE PRECISION", new NumericType.Builder(new NumberType(38, null)).build());
        BUILTIN_TYPE_ALIASES.put("REAL", new NumericType.Builder(new NumberType(18, null)).build());
        BUILTIN_TYPE_ALIASES.put("INTEGER", new NumericType.Builder(new NumberType(38, 0)).build());
        BUILTIN_TYPE_ALIASES.put("INT", new NumericType.Builder(new NumberType(38, 0)).build());
        BUILTIN_TYPE_ALIASES.put("SMALLINT", new NumericType.Builder(new NumberType(38, 0)).build());

        BUILTIN_TYPE_ALIASES.put("BINARY_FLOAT", new NumericType.Builder(new BinaryFloatType()).build());
        BUILTIN_TYPE_ALIASES.put("SIMPLE_FLOAT", new NumericType.Builder(new BinaryFloatType()).nonNullable().build());

        BUILTIN_TYPE_ALIASES.put("BINARY_DOUBLE", new NumericType.Builder(new BinaryDoubleType()).build());
        BUILTIN_TYPE_ALIASES.put("SIMPLE_DOUBLE", new NumericType.Builder(new BinaryDoubleType()).nonNullable().build());

        BUILTIN_TYPE_ALIASES.put("VARCHAR2", new VarChar2Type(null, null));
        // TODO: respect NLS_LENGTH_SEMANTICS parameter
        {
            // TODO: respect ORA_MAX_NAME_LEN
            {
                BUILTIN_TYPE_ALIASES.put("DBMS_ID", new VarChar2Type(128, VarChar2Type.MaxSizeUnit.CHAR));
                BUILTIN_TYPE_ALIASES.put("DBMS_QUOTED_ID", new VarChar2Type(128 + 2, VarChar2Type.MaxSizeUnit.CHAR));
            }
            BUILTIN_TYPE_ALIASES.put("DBMS_ID_30", new VarChar2Type(30, VarChar2Type.MaxSizeUnit.CHAR));
            BUILTIN_TYPE_ALIASES.put("DBMS_QUOTED_ID_30", new VarChar2Type(30 + 2, VarChar2Type.MaxSizeUnit.CHAR));
        }
        BUILTIN_TYPE_ALIASES.put("STRING", new VarChar2Type(null, null));
        BUILTIN_TYPE_ALIASES.put("VARCHAR", new VarChar2Type(null, null));
        BUILTIN_TYPE_ALIASES.put("CHARACTER VARYING", new VarChar2Type(null, null));
        BUILTIN_TYPE_ALIASES.put("CHAR VARYING", new VarChar2Type(null, null));

        BUILTIN_TYPE_ALIASES.put("CHAR", new CharType(null, null));
        BUILTIN_TYPE_ALIASES.put("CHARACTER", new CharType(null, null));

        BUILTIN_TYPE_ALIASES.put("NCHAR", new NCharType(null));
        BUILTIN_TYPE_ALIASES.put("NATIONAL CHAR", new NCharType(null));
        BUILTIN_TYPE_ALIASES.put("NATIONAL CHARACTER", new NCharType(null));
        BUILTIN_TYPE_ALIASES.put("NVARCHAR2", new NVarChar2(null));

        BUILTIN_TYPE_ALIASES.put("LONG", new LongType());

        BUILTIN_TYPE_ALIASES.put("RAW", new RawType(null));
        BUILTIN_TYPE_ALIASES.put("LONG RAW", new LongRawType());

        BUILTIN_TYPE_ALIASES.put("ROWID", new RowIdType());

        BUILTIN_TYPE_ALIASES.put("UROWID", new UniversalRowIdType());

        BUILTIN_TYPE_ALIASES.put("BOOLEAN", new BooleanType());

        BUILTIN_TYPE_ALIASES.put("BFILE", new BFileType());

        BUILTIN_TYPE_ALIASES.put("BLOB", new BlobType());
        BUILTIN_TYPE_ALIASES.put("BINARY LARGE OBJECT", new BlobType());

        BUILTIN_TYPE_ALIASES.put("CLOB", new ClobType());
        BUILTIN_TYPE_ALIASES.put("CHARACTER LARGE OBJECT", new ClobType());
        BUILTIN_TYPE_ALIASES.put("CHAR LARGE OBJECT", new ClobType());

        BUILTIN_TYPE_ALIASES.put("NCLOB", new NClobType());
        BUILTIN_TYPE_ALIASES.put("NATIONAL CHARACTER LARGE OBJECT", new NClobType());
        BUILTIN_TYPE_ALIASES.put("NCHAR LARGE OBJECT", new NClobType());
    }

    private final HashMap<String, Type> variableSymbolMap;

    private HashMap<String, Type> typeReferenceMap;

    private SymbolTable parent;

    public SymbolTable() {
        this.variableSymbolMap = new HashMap<>();
        this.typeReferenceMap = new HashMap<>(BUILTIN_TYPE_ALIASES);
    }

    private SymbolTable(SymbolTable parent) {
        this();
        this.parent = parent;
    }

    public Type lookupVariable(String name) {
        return innerLookupVariable(name.toUpperCase());
    }

    private Type innerLookupVariable(String name) {
        if (this.variableSymbolMap.containsKey(name)) {
            return this.variableSymbolMap.get(name);
        }

        if (parent != null) {
            return parent.innerLookupVariable(name);
        }

        return null;
    }

    public void addVariable(String name, Type type) {
        variableSymbolMap.put(name.toUpperCase(), type);
    }

    public SymbolTable cloneTable() {
        return new SymbolTable(this);
    }
}
