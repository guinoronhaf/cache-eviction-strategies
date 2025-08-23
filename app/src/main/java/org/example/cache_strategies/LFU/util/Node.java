package org.example.cache_strategies.LFU.util;

public class Node<k, v>{

    k key;
    v value;
    int frequency;
    Node<k, v> prev;
    Node<K, v> next;

    public Node(k key, v value, int frequency){
        this.key = key;
        this.value = value;
        this.frequency = frequency;

    }
    

}