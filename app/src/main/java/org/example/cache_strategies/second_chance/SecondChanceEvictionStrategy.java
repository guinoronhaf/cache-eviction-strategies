package org.example.cache_strategies.second_chance;

import org.example.cache_strategies.util.CacheEvictionStrategy;

public class SecondChanceEvictionStrategy<T> implements CacheEvictionStrategy<T> {

    private SecondChanceCache<T> cache;

    public SecondChanceEvictionStrategy(int capacity) {
        this.cache = new SecondChanceCache<>(capacity);
    }

    public SecondChanceEvictionStrategy() {
        this.cache = new SecondChanceCache<>();
    }

    public String get(T value) {
        if (cache.contains(value)) {
            cache.updateReference(value);
            return "hit";
        } else {
            cache.add(value);
            return "miss";
        }
    }

    public T getNextEviction() {
        if (!cache.isFull())
            return null;
        return cache.getNextEvictionable();
    }

    public Integer size() {
        return cache.size();
    }

    @Override
    public String toString() {
        return cache.toString();
    }

}
