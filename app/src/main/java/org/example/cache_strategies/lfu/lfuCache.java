package org.example.cache_strategies.lfu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class lfuCache<T> {

    private final static int CAPACITY_DEFAULT = 10;
    private final int capacity;
    private int minFrequency;
    private final Map<T, Integer> freqMap;
    private final Map<Integer, LinkedList<T>> freqListMap;

    public lfuCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.freqMap = new HashMap<>();
        this.freqListMap = new HashMap<>();
    }

    public lfuCache() {
        this(CAPACITY_DEFAULT);
    }

    public T get(T item) {
        if (!this.freqMap.containsKey(item)) {
            return null;
        }

        int currentFreq = this.freqMap.get(item);
        LinkedList<T> currentList = this.freqListMap.get(currentFreq);
        currentList.remove(item);

        if (currentList.isEmpty() && currentFreq == minFrequency) {
            minFrequency++;
        }

        int newFreq = currentFreq + 1;
        this.freqMap.put(item, newFreq);
        this.freqListMap.putIfAbsent(newFreq, new LinkedList<>());
        this.freqListMap.get(newFreq).addFirst(item);

        return item;
    }

    public void put(T item) {
        if (capacity == 0) {
            return;
        }

        if (this.freqMap.containsKey(item)) {
            get(item);
            return;
        }

        if (this.freqMap.size() >= capacity) {
            LinkedList<T> minFreqList = this.freqListMap.get(minFrequency);
            T itemToRemove = minFreqList.removeLast();
            this.freqMap.remove(itemToRemove);
        }

        this.freqMap.put(item, 1);
        this.freqListMap.putIfAbsent(1, new LinkedList<>());
        this.freqListMap.get(1).addFirst(item);
        minFrequency = 1;
        
    }

    public boolean contains(T item){
        return this.freqMap.containsKey(item);
    }

    public Integer size(){
        return this.freqMap.size();
    }

    
public T getNextEviction(){
    if (this.freqMap.isEmpty()) {
        return null; 
    }
    LinkedList<T> minFrenquencyList = this.freqListMap.get(minFrequency);
    if (minFrenquencyList != null && !minFrenquencyList.isEmpty()) {
        return minFrenquencyList.getLast();
    }
    return null;
}


}