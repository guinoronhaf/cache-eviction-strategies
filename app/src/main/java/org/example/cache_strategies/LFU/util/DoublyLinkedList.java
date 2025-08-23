package org.example.cache_strategies.LFU.util;

import java.util.NoSuchElementException;

public class DoublyLinkedList{

    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty(){
        return this.head == null;
    }
    public void addHead(Node node){
        if(isEmpty()){
            this.head = node;
            this.tail = node;
        }else{
            node.next = this.head;
            this.head.prev = node;
            this.head = node;  
        }

        this.size++;

    }

    public Node removeHead(){
        if(isEmpty()) throw new NoSuchElementException("this list is empty");
        Node nodeToRemove = this.head; 

        if(this.head.next == null){
            this.head = null;
            this.tail = null;
        }else{
            this.head = this.head.next;
            this.head.prev = null;
        }
        this.size--;
        return nodeToRemove;


    }

    public Node removeTail(){
        if(isEmpty()) throw new NoSuchElementException("this list is empty");
        Node nodeToRemove = this.tail;

        if(this.tail.prev == null){
            this.head = null;
            this.tail = null;
        }else{
            this.tail = this.tail.prev;
            this.tail.next = null;
        }

        this.size--;
        return nodeToRemove;
    }

    public void remove(Node node){
        if(isEmpty()) throw new NoSuchElementException("this list is empty");

        if(node == this.head ){
            removeHead();
            return; 
        }
        if(node == this.tail){
            removeTail();
            return;
        }
            
        node.prev.next = node.next;
        node.next.prev = node.prev;
        this.size--;
        

    }

}