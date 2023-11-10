package org.example.plsql.ast;

import java.util.List;

public class Program extends Node {
    private List<Block> blocks;

    public Program(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
