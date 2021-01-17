package com.ucab.taller6;

public class List {
    
    public Node head;
    public Node tail;

    public List() {
		head = null;
	}

    public Boolean isEmpty(){
		return head == null;
    }
    
    public void append(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    public String toString() {
        String str = "";
        for (Node node = head; node != null; node = node.next) {
            str += node.value;
            if (node.next != null) {
                str += ",";
            }
        }
        return str;
    }

}
