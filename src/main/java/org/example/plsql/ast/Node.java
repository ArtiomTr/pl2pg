package org.example.plsql.ast;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Node {
    public abstract boolean areEqual(Node node);

    public static boolean areListsEqual(List<? extends Node> listA, List<? extends Node> listB) {
        if (listA.size() != listB.size()) {
            return false;
        }

        for (int i = 0; i < listA.size(); ++i) {
            if (!listA.get(i).areEqual(listB.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkEqualityWithNull(Node a, Node b) {
        if (a == null && b == null) {
            return true;
        }

        if (a != null && b != null) {
            return a.areEqual(b);
        }

        return false;
    }
}
