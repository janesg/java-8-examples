package com.devxpress;

import java.util.List;

public class LinkedList<E> {
    
    private Node<E> head = null;
    private Node<E> tail = null;
    private int nodeCount;
    
    public LinkedList() {
        this.nodeCount = 0;
    }
    
    public void add(E value) {
        // Add at tail to maintain insertion sequence
        add(nodeCount, value);
    }
    
    public void add(List<E> values) {
        values.stream().forEach((e) -> add(e));
    }
    
    public void add(int index, E value) {
        
        if (index < 0) {
            throw new IllegalArgumentException("Index must be a positive value");
        }
        
        if (index > nodeCount) {
            throw new IllegalArgumentException("Unable to add element at index " + index + " when list contains " + nodeCount + " element(s)");
        }
        
        Node<E> newNode = new Node<>(value);
        
        if (nodeCount == 0) {
            // adding first node
            this.head = newNode;
            this.tail = this.head;
        } else if (index == 0) {
            // adding to the head
            newNode.next = this.head;
            this.head = newNode;
        } else if (index == nodeCount) {
            // adding to the tail
            this.tail.next = newNode;
            this.tail = newNode;
        } else {
            // adding between nodes
            Node<E> prevNode = get(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        }

        nodeCount++;
    }
    
    public void remove(int index) {
        
        if (index < 0) {
            throw new IllegalArgumentException("Index must be a positive value");
        }
        
        if (index >= nodeCount) {
            throw new IllegalArgumentException("Unable to remove element at index " + index + " when list contains " + nodeCount + " element(s)");
        }

        if (index == 0) {
            // removing at the head
            this.head = this.head.next;
        } else if (index == (nodeCount - 1)) {
            // removing at the tail
            Node<E> prevNode = get(index - 1);
            prevNode.next = null;
            this.tail = prevNode;
        } else {
            // removing between nodes
            Node<E> prevNode = get(index - 1);
            prevNode.next = prevNode.next.next;
        }

        nodeCount--;
    }
    
    public int size() {
        return this.nodeCount;
    }
    
    public Node<E> get(int index) {
        
        if (index < 0) {
            throw new IllegalArgumentException("Index must be a positive value");
        }
        
        if (index >= nodeCount) {
            throw new IllegalArgumentException("Unable to get element at index " + index + " when list contains " + nodeCount + " element(s)");
        }
        
        if (index == (nodeCount - 1)) {
            return this.tail;
        }
        
        Node<E> n = this.head;
        for (int i = 0; i < index; i++) {
            n = n.next();
        }
        
        return n;
    }
    
    public Node<E> getMiddle() {
        
        switch(nodeCount) {
            case 0:
                throw new IllegalArgumentException("Unable to get middle element when list is empty");
            case 1:
                return this.head;
            case 2:
                return this.tail;
            default:
                break;
        }
        
        Node<E> slowPtr = this.head;
        Node<E> fastPtr = this.head;
        
        while (fastPtr != null && fastPtr.next != null) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next.next;
        }
        
        return slowPtr;
    }
    
    public void reverseByIteration() {
        
        Node<E> currNode = this.head;
        Node<E> prevNode = null;
        Node<E> nextNode = null;
        
        while (currNode != null) {
            // Transfer current node references
            nextNode = currNode.next;
            currNode.next = prevNode;
            
            // Shuffle references for next node along 
            prevNode = currNode;
            currNode = nextNode;
        }
        
        this.head = prevNode;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> n = this.head;
        while (n != null) {
            sb.append("[" + n.data().toString() + "] -> ");
            n = n.next();
        }
        
        if (sb.length() > 0) {
            sb.append("NULL");
        }
        
        return sb.toString();
    }
    
    class Node<E> {
        Node<E> next = null;
        E data = null;
        
        Node(E data) {
            this.data = data;
        }
        
        Node<E> next() {
            return this.next;
        }
        
        E data() {
            return this.data;
        } 
    }
    
}