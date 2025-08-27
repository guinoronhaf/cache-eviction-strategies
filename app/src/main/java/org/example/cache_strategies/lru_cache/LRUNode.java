package org.example.cache_strategies.lru_cache;

public class LRUNode<V> {

    V value;
    LRUNode<V> next;
    LRUNode<V> prev;

    public LRUNode(V value) {
        this.value = value;
    }

} 
