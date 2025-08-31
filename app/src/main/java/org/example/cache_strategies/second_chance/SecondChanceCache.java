package org.example.cache_strategies.second_chance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.example.cache_strategies.second_chance.util.SecondChanceEntry;

public class SecondChanceCache<T> {

    private SecondChanceEntry<T>[] entryCache;
    private int clockPointer;
    private int last;
    private HashSet<T> inCache;

    private static final int CAPACITY_DEFAULT = 10;

    @SuppressWarnings("unchecked")
    public SecondChanceCache(int capacity) {
        this.entryCache = (SecondChanceEntry<T>[]) new SecondChanceEntry[capacity];
        this.clockPointer = 0;
        this.last = -1;
        this.inCache = new HashSet<>();
        for (int i = 0; i < this.entryCache.length; i++)
            this.entryCache[i] = new SecondChanceEntry<T>(null);
    }

    public SecondChanceCache() {
        this(CAPACITY_DEFAULT);
    }

    private int indexOf(T value) {

        if (value == null)
            throw new IllegalArgumentException();

        for (int i = 0; i <= this.last; i++) {
            if (this.entryCache[i].getValue().equals(value))
                return i;
        }

        return -1;

    }

    private int indexOfNextEvictionable() {

        if (this.isEmpty())
            throw new NoSuchElementException();

        int idx = clockPointer;

        while (!this.entryCache[idx].isEvictionable()) {
            this.entryCache[idx].setEvictionable(true);
            idx = (idx + 1) % this.entryCache.length;
        }

        return idx;

    }

    public boolean isEmpty() {
        return this.last == -1;
    }

    public boolean isFull() {
        return this.last == this.entryCache.length - 1;
    }

    public void updateReference(T value) {

        int idxValue = indexOf(value);

        if (idxValue == -1)
            this.add(value);
        else
            this.entryCache[idxValue].setEvictionable(false);

    }

    public void add(T value) {

        if (this.inCache.contains(value)) {
            this.updateReference(value);
        } else {
            if (this.isFull()) {
                int idxEvictionable = indexOfNextEvictionable();
                this.inCache.remove(this.entryCache[idxEvictionable].getValue());
                this.entryCache[idxEvictionable].setValue(value);
                this.entryCache[idxEvictionable].setEvictionable(false);;
                clockPointer = (idxEvictionable + 1) % this.entryCache.length;
            } else {
                this.entryCache[++this.last].setValue(value);
            }
            this.inCache.add(value);
        }

    }

    public T getNextEvictionable() {
        return this.entryCache[clockPointer].getValue();
    }

    public boolean contains(T value) {
        return this.inCache.contains(value);
    }

    public int size() {
        return this.last + 1;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.entryCache);
    }

}
