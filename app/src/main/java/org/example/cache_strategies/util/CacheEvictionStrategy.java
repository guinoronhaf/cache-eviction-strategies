package org.example.cache_strategies.util;

/**
 * Interface CacheEvictionStrategy, que define um contrato para as classes
 * que operam sobre as classes do lógica das políticas de cache.
 *
 * @author Artur (Github: ArturALW)
 * @author Brunno (Github: Brunnowxl)
 * @author Guilherme (Github: guinoronhaf)
 * @author Victor França (Github: victorfrancacg)
 * @author Vitor Hugo (Github: vitorh333)
 */
public interface CacheEvictionStrategy<T> {

    /**
     * Busca o elemento em cache, retornando String caso encontre ou não.
     *
     * @param value valor buscado em cache.
     * @return String indicando a presença ou não do elemento em cache.
     */
    public String get(T value);

    /**
     * Retorna o próximo elemento a deixar o cache.
     *
     * @return próximo elemento a deixar o cache.
     */
    public T getNextEviction();

    /**
     * Retorna o comprimento do cache, ou seja, a quantidade de elemntos presente nele.
     *
     * @return a quantidade de elementos em cache.
     */
    public Integer size();

}
