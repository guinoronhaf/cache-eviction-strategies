package org.example.cache_strategies.second_chance.util;

public class Entry<T> {

    private T value;
    private boolean evictionable;

    public Entry(T value) {
        this.value = value;
        this.evictionable = false;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isEvictionable() {
        return this.evictionable;
    }

}
