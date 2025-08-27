package org.example.cache_strategies.lru_cache;

import org.example.cache_strategies.util.CacheEvictionStrategy;

public class LRUCacheEvictionStrategy<T> implements CacheEvictionStrategy<T> {

    private LRUCache<T> cache;

    public LRUCacheEvictionStrategy(int capacidade) {
        this.cache = new LRUCache<>(capacidade);
    }

    public LRUCacheEvictionStrategy() {
        this.cache = new LRUCache<>();
    }

    public String get(T value) {
        if(cache.get(value) == null){
            cache.addLast(value);
            return "miss";
        }
        return "hit";
    }

    public T getNextEviction() {
        return cache.getFirst();
    }

    public Integer size() {
        return cache.size();
    }

}
