package org.example.cache_strategies.random_replace;

import org.example.cache_strategies.util.CacheEvictionStrategy;

public class RandomReplaceEvictionStrategy<T> implements CacheEvictionStrategy<T> {
    private final RandomReplaceCache<T> cache;

    public RandomReplaceEvictionStrategy(int capacity) {
        this.cache = new RandomReplaceCache<>(capacity);
    }

    public RandomReplaceEvictionStrategy() {
        this.cache = new RandomReplaceCache<>();
    }

    public String get(T value) {
        if (cache.contains(value)) {
            return "hit";
        } else {
            cache.add(value);
            return "miss";
        }
    }

    public T getNextEviction() {
        if (!cache.isFull()) {
            return null;
        } else {
            return cache.nextEviction();
        }
    }

    public Integer size() {
        return cache.size();
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}

