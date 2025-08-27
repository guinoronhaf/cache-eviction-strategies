package org.example.cache_strategies.random_replace;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomReplaceCache<T> {
    private final int capacity;
    private final List<T> items;
    private final Random random;

    public RandomReplaceCache(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
        this.random = new Random();
    }

    public RandomReplaceCache() {
        this.capacity = 10;
        this.items = new ArrayList<>();
        this.random = new Random();
    }

    public boolean contains(T value) {
        return items.contains(value);
    }

    public void add(T value) {
        if (items.size() < capacity) {
            items.add(value);
        } else {

            int idx = random.nextInt(items.size());
            items.set(idx, value);
        }
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }

    public T nextEviction() {
        if (items.isEmpty()) return null;
        int idx = random.nextInt(items.size());
        return items.get(idx);
    }

    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return "RandomReplaceCache{" +
                "items=" + items +
                ", capacity=" + capacity +
                '}';
    }
}

