package com.ucab.proyecto2.structures;

public class Tree<T> {

    private Node<T> root;

    public Tree() {
        root = null;
    }

    public Tree(T o) {
        root = new Node<T>(o);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public boolean isEmpty() {
        if (root == null)
            return true;
        return false;
    }

}
