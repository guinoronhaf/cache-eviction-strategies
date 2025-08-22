package org.example.cache_strategies.random_replace;

import org.example.cache_strategies.util.CacheEvictionStrategy;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomReplace<T> implements CacheEvictionStrategy<T> {

    private final List<T> elementos;
    private final int capacidade;
    private final Random random;
    private T proximoEviction;

    public RandomReplace(int capacidade) {
        this.capacidade = capacidade;
        this.elementos = new ArrayList<>();
        this.random = new Random();
        this.proximoEviction = null;
    }

    @Override
    public String get(T object) {
        if (elementos.contains(object)) {
            return "hit";
        }

        if (elementos.size() < capacidade) {
            elementos.add(object);
            proximoEviction = null;
        } else {
            int idx = random.nextInt(capacidade);
            proximoEviction = elementos.get(idx);
            elementos.set(idx, object);
        }

        return "miss: " + object.toString();
    }

    @Override
    public T getNextEviction() {
        return proximoEviction;
    }

    @Override
    public Integer size() {
        return elementos.size();
    }
}
