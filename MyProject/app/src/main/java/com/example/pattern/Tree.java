package com.example.pattern;

import java.util.LinkedList;
import java.util.List;

public class Tree<T> {

    public Tree<T> parent;
    public T value;
    public int level;
    public List<Tree<T>> children;

    public Tree(T v, Tree<T> p) {
        parent = p;
        value = v;
        level = parent == null ? 0 : parent.level + 1;
        children = new LinkedList<Tree<T>>();
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public boolean hasAncestor(T value) {
        if (value != null && parent != null) {
            return parent.value == value || parent.hasAncestor(value);
        }
        else {
            return false;
        }
    }

}