package ds.queue;

import ds.list.Node;

public class MyQueue {
    private Node front;
    private Node rear;
    private int size;

    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(Object item) {
        Node newNode = new Node(item);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public Object dequeue() {
        if (front == null) {
            return null;
        }
        Object item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    public Object peek() {
        if (front == null) {
            return null;
        }
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}