package org.example.cache_strategies.second_chance;

import org.example.cache_strategies.util.CacheEvictionStrategy;

/**
 * Implementação da classe SecondChanceEvictionStrategy, que implementa a interface CacheEvictionStrategy
 * e possui uma instância da classe de Lógica SecondChanceCache, construída a partir de um tipo genérico T.
 *
 * @author Guilherme (Github: guinoronhaf)
 */
public class SecondChanceEvictionStrategy<T> implements CacheEvictionStrategy<T> {

    /**
     * Instância da classe SecondChanceCache, a classe de lógica do cache.
     */
    private SecondChanceCache<T> cache;

    /**
     * Constrói um objeto do tipo SecondChanceEvictionStrategy a partir de uma capacidade
     * específica.
     *
     * @param capacity capacidade do cache.
     */
    public SecondChanceEvictionStrategy(int capacity) {
        this.cache = new SecondChanceCache<>(capacity);
    }

    /**
     * Constrói um objeto do tipo SecondChanceEvictionStrategy sem uma capacidade específica.
     */
    public SecondChanceEvictionStrategy() {
        this.cache = new SecondChanceCache<>();
    }

    /**
     * Busca um elemento no cache. Caso esteja presente retorna "hit". Caso contrário, retorna "miss".
     * Em ambos os casos, as alterações adequadas são realizadas no cache.
     *
     * @param value elemento buscado no cache.
     * @return "hit" ou "miss" dependendo da presença ou não do elemento em cache.
     */
    public String get(T value) {
        if (cache.contains(value)) {
            cache.updateReference(value);
            return "hit";
        } else {
            cache.add(value);
            return "miss";
        }
    }

    /**
     * Retorna o próximo candidato a sair do cache.
     *
     * @return próximo candidato a deixar o cache.
     */
    public T getNextEviction() {
        if (!cache.isFull())
            return null;
        return cache.getNextEvictionable();
    }

    /**
     * Retorna o comprimento do cache, isto é, a quantidade de elementos
     * presente nele.
     *
     * @return a quantidade de elementos presente no cache.
     */
    public Integer size() {
        return cache.size();
    }

    /**
     * Representação, em String, do cache.
     *
     * @return representação, em String, do cache.
     */
    @Override
    public String toString() {
        return cache.toString();
    }

}
