package org.example.oracle.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class Statement extends Node {

    private List<Label> labels = new ArrayList<>();

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Label> getLabels() {
        return labels;
    }
}
