package org.example.plsql.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class Statement extends Node {

    private List<Label> labels = new ArrayList<>();

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    protected boolean labelsEqual(Statement st) {
        if (st.labels.size() != this.labels.size()) {
            return false;
        }

        for (int i = 0; i < this.labels.size(); ++i) {
            if (!st.labels.get(i).areEqual(this.labels.get(i))) {
                return false;
            }
        }

        return true;
    }
}
