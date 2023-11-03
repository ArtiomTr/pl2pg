package org.example.plsql.ast;

import java.util.List;

public class Program extends Node {
    private List<Block> blocks;

    public Program(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public boolean areEqual(Node node) {
        if (!(node instanceof Program)) {
            return false;
        }

        Program b = (Program) node;

        if (b.blocks.size() != this.blocks.size()) {
            return false;
        }

        for (int i = 0; i < this.blocks.size(); ++i) {
            if (!b.blocks.get(i).areEqual(this.blocks.get(i))) {
                return false;
            }
        }

        return true;
    }
}
