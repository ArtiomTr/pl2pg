package org.example.plsql.ast;

import java.util.List;

public class DeclareSection extends Node {

    private List<Declaration> declarationList;

    public DeclareSection(List<Declaration> declarationList) {
        this.declarationList = declarationList;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        // TODO:
        throw new RuntimeException("Not implemented!");
    }

    public List<Declaration> getDeclarationList() {
        return declarationList;
    }
}
