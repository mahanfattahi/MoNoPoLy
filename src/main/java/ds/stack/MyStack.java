package ds.stack;

import ds.list.Node;

public class MyStack {
    private Node top;
    private int size;

    public MyStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(Object item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Object pop() {
        if (top == null) {
            return null;
        }
        Object item = top.data;
        top = top.next;
        size--;
        return item;
    }

    public Object peek() {
        if (top == null) {
            return null;
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}