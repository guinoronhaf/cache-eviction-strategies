package org.example.cache_strategies.random_replacement;

import org.example.cache_strategies.util.CacheEvictionStrategy;

/**
 * Estratégia de substituição aleatória (Random Replace) para cache.
 * <p>
 * Esta implementação define quando um item será removido do cache
 * de acordo com a política de escolha aleatória. Caso o cache esteja cheio,
 * um item é escolhido aleatoriamente para ser removido.
 *
 * @param <T> tipo dos elementos armazenados no cache
 */
public class RandomReplaceEvictionStrategy<T> implements CacheEvictionStrategy<T> {

    /** Estrutura de cache que utiliza substituição aleatória. */
    private final RandomReplaceCache<T> cache;

    /**
     * Construtor que inicializa o cache com a capacidade definida.
     *
     * @param capacity capacidade máxima do cache
     */
    public RandomReplaceEvictionStrategy(int capacity) {
        this.cache = new RandomReplaceCache<>(capacity);
    }

    /**
     * Construtor que inicializa o cache com capacidade padrão.
     */
    public RandomReplaceEvictionStrategy() {
        this.cache = new RandomReplaceCache<>();
    }

    /**
     * Obtém um valor no cache.
     * <p>
     * Caso o valor já esteja presente, retorna "hit".
     * Caso contrário, insere o valor e retorna "miss".
     *
     * @param value valor a ser buscado/inserido
     * @return "hit" se o valor já estiver no cache, "miss" caso contrário
     */
    public String get(T value) {
        if (cache.contains(value)) {
            return "hit";
        } else {
            cache.add(value);
            return "miss";
        }
    }

    /**
     * Retorna o próximo item a ser removido do cache.
     * <p>
     * Se o cache não estiver cheio, retorna {@code null}.
     *
     * @return elemento a ser removido ou {@code null} caso não haja
     */
    public T getNextEviction() {
        if (!cache.isFull()) {
            return null;
        } else {
            return cache.nextEviction();
        }
    }

    /**
     * Retorna a quantidade de elementos armazenados no cache.
     *
     * @return tamanho atual do cache
     */
    public Integer size() {
        return cache.size();
    }

    /**
     * Retorna uma representação em string do estado atual do cache.
     *
     * @return string representando os elementos do cache
     */
    @Override
    public String toString() {
        return cache.toString();
    }
}

