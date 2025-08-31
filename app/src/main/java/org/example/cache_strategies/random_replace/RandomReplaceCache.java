package org.example.cache_strategies.random_replace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementação de um cache com política de substituição aleatória (Random Replace).
 * <br>
 * Quando o cache atinge sua capacidade máxima, um item é escolhido aleatoriamente
 * para ser substituído pelo novo valor. O próximo item a ser removido é armazenado
 * no atributo {@code nextEviction}. A cada nova adição, o elemento apontado por
 * {@code nextEviction} é substituído, e outro elemento aleatório é sorteado como
 * próximo a sair.
 *
 * @param <T> tipo dos elementos armazenados no cache
 */
public class RandomReplaceCache<T> {

    /** Capacidade máxima do cache. */
    private final int capacity;

    /** Lista que armazena os elementos do cache. */
    private final List<T> items;

    /** Gerador de números aleatórios usado para escolher elementos a serem substituídos. */
    private final Random random;

    /** Próximo item a ser removido do cache. */
    private T nextEviction;

    /**
     * Construtor que inicializa o cache com a capacidade informada.
     *
     * @param capacity capacidade máxima do cache
     */
    public RandomReplaceCache(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
        this.random = new Random();
        this.nextEviction = null;
    }

    /**
     * Construtor que inicializa o cache com uma capacidade padrão de 10.
     */
    public RandomReplaceCache() {
        this(10);
    }

    /**
     * Verifica se o valor informado já está presente no cache.
     *
     * @param value valor a ser verificado
     * @return {@code true} se o valor já estiver presente, {@code false} caso contrário
     */
    public boolean contains(T value) {
        return items.contains(value);
    }

    /**
     * Adiciona um valor ao cache.
     * <br>
     * - Se ainda houver espaço, o valor é apenas inserido e {@code nextEviction} é {@code null}. <br>
     * - Caso o cache esteja cheio, substitui o elemento guardado em {@code nextEviction} 
     *   e sorteia outro elemento aleatório como próximo a sair.
     *
     * @param value valor a ser adicionado
     */
	public void add(T value) {
		if (items.size() < capacity) {
			// Adiciona normalmente
			items.add(value);

			// Se após adicionar atingir a capacidade, sorteia o próximo a sair
			if (items.size() == capacity) {
				int idx = random.nextInt(items.size());
				nextEviction = items.get(idx);
			}
		} else {
			// Substitui o elemento guardado em nextEviction
			int idx = items.indexOf(nextEviction);
			items.set(idx, value);

			// Sorteia outro elemento aleatório como próximo a sair
			int newIdx = random.nextInt(items.size());
			nextEviction = items.get(newIdx);
		}
	}
    /**
     * Verifica se o cache atingiu sua capacidade máxima.
     *
     * @return {@code true} se o cache estiver cheio, {@code false} caso contrário
     */
    public boolean isFull() {
        return items.size() >= capacity;
    }

    /**
     * Retorna o próximo item que será removido do cache.
     * <br>
     * Esse valor é atualizado a cada adição quando o cache estiver cheio.
     *
     * @return elemento a ser removido ou {@code null} se o cache não estiver cheio
     */
    public T nextEviction() {
        return nextEviction;
    }

    /**
     * Retorna a quantidade atual de elementos armazenados no cache.
     *
     * @return número de elementos presentes no cache
     */
    public int size() {
        return items.size();
    }

    /**
     * Retorna uma representação em string do estado atual do cache.
     *
     * @return string representando os elementos, a capacidade e o próximo item a sair
     */
    @Override
    public String toString() {
        return "RandomReplaceCache{" +
                "items=" + items +
                ", capacity=" + capacity +
                ", nextEviction=" + nextEviction +
                '}';
    }
}

