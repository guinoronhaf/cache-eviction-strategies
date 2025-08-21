package org.example.cache_strategies.util;

public interface CacheEvictionStrategy<T> {

    public String get(T object);

    public T getNextEviction();

    public Integer size();

}
