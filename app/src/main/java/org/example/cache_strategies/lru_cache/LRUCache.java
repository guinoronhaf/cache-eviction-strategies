package org.example.cache_strategies.lru_cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<V>  {

    private final Map<V, LRUNode<V>> cache;
    private final int capacity;
    private static final int CAPACIDADE_DEFAULT = 20;
    private LRUNode<V> head;
    private LRUNode<V> tail;
    private int size;

    public LRUCache(int capacidade) { 
        this.capacity = capacidade; 
        this.cache = new HashMap<>();
    }

    public LRUCache() {
        this(CAPACIDADE_DEFAULT);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isFull() {
        return size() == this.capacity;
    }

    public V getFirst() {
        if(isEmpty()) return null;
        return head.value;
    }

    public int size() {
        return this.size;
    }

    public V get(V value) {

        if(!cache.containsKey(value)) {
            return null;
        }

        LRUNode<V> node = cache.get(value);
        moveToTail(node);
        return node.value;
    }

    public void addLast(V value) {
        
        if(isFull()) removeFirst();
        LRUNode<V> newNode = new LRUNode<>(value);
        
        if(isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        cache.put(newNode.value, newNode);
        this.size++;

    }

    public LRUNode<V> removeFirst() {
        
        if(isEmpty()) return null;

        LRUNode<V> toRemove = this.head;
        
        if(size() == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head.next.prev = null;
            this.head = this.head.next;
        }
        this.cache.remove(toRemove.value);
        this.size--;
        return toRemove;

    }

    public void moveToTail(LRUNode<V> node) {
 
        if(node == this.tail) return;
        
        if(node == this.head) {
            this.head = node.next;
            this.head.prev = null;
        } else {
            if(node.next != null) node.next.prev = node.prev;
            if(node.prev != null) node.prev.next = node.next;
        }
        
        node.prev = this.tail;
        node.next = null;
        this.tail.next = node;
        this.tail = node;
    
    }


}
