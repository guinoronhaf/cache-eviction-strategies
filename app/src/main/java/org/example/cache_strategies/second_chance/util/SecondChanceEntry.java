package org.example.cache_strategies.second_chance.util;

public class SecondChanceEntry<T> {

    private T value;
    private boolean evictionable;

    public SecondChanceEntry(T value) {
        this.value = value;
        this.evictionable = false;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isEvictionable() {
        return this.evictionable;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setEvictionable(boolean evictionable) {
        this.evictionable = evictionable;
    }

    @Override
    public String toString() {
        return "<" + this.value + ", " + this.evictionable + ">";
    }

}
