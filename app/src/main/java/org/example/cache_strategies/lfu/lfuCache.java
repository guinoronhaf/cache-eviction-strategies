package org.example.cache_strategies.lfu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *  Esta classe implementa toda a lógica da política de cache LFU
 *
 *  @author Artur de Lima Wanderley (@ArturALW)
 *
 */

public class LfuCache<T> {

    private final static int CAPACITY_DEFAULT = 10;
    private final int capacity;
    private int minFrequency;
    private final Map<T, Integer> freqMap;
    private final Map<Integer, LinkedList<T>> freqListMap;

    public LfuCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.freqMap = new HashMap<>();
        this.freqListMap = new HashMap<>();
    }

    public LfuCache() {
        this(CAPACITY_DEFAULT);
    }

   /**
    * 
    * @param item O item que queremos encontrar dentro do cache
    * @return O método retorna null caso o item não esteja dentro do cache e retorna o item caso ele esteja.
    *
    */
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

    /**
     * 
     * @param item O item que será adicionado no cache.
     * 
     */
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

    /**
     * 
     * @param item O que será verificado se está ou não no cache.
     * @return {@code true } se o item estiver no cache; {@code false} caso contrário.
     * 
     */
    public boolean contains(T item){
        return this.freqMap.containsKey(item);
    }

    /**
     * 
     * @return Retorna o tamanho do cache.
     * 
     */
    public Integer size(){
        return this.freqMap.size();
    }

    /**
     * 
     * @return Retorna o próximo item que será removido do cache, caso seja necessário.
     * 
     */
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

    /**
     * 
     * @return {@code true} caso o cache tenha atingido sua capacidade máxima; {@code false} caso contrário.
     * 
     */
    public boolean isFull(){
        return this.freqMap.size() == this.capacity;
    }



}
