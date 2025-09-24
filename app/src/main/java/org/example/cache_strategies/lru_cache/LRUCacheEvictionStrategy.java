package org.example.cache_strategies.lru_cache;

import org.example.cache_strategies.util.CacheEvictionStrategy;

/**
 * Implementação da classe LRUCacheEvictionStrategy, que implementa a interface CacheEvictionStrategy
 * e possui uma instância da classe de Lógica LRUCache, construída a partir de um tipo genérico T.
 *
 * @author Brunno Weslley (Github: brunnowxl).
 */
public class LRUCacheEvictionStrategy<T> implements CacheEvictionStrategy<T> {

    /**
     * Definindo o cache o tipo LRUCache, classe lógica do cache.
     */
    private LRUCache<T> cache;

    /**
     * Constrói um objeto do tipo LRUCacheEvictionStrategy com uma capacidade específica.
     *
     * @param capacidade Capacidade máxima de elementos cache.
     */
    public LRUCacheEvictionStrategy(int capacidade) {
        this.cache = new LRUCache<>(capacidade);
    }

    /**
     * Constrói um objeto do tipo LRUCacheEvictionStrategy com a capacidade padrão da politica de cache em uso.
     */
    public LRUCacheEvictionStrategy() {
        this.cache = new LRUCache<>();
    }

    /**
     * Consulta um determinado valor e retorna hit caso o elemento esteja contido no cache, ou miss caso não esteja.
     *
     * @param value Valor a ser consultado.
     * @return Hit caso o valor faça parte do cache, miss caso contrário.
     */
    public String get(T value) {
        if(cache.get(value) == null){
            cache.addLast(value);
            return "miss";
        }
        return "hit";
    }

    /**
     * Retorna o elemento a ser removido caso o cache atinja a capacidade máxima e seja feita mais uma adição.
     *
     * @return Proximo elemento a ser removido do cache.
     */
    public T getNextEviction() {
        return cache.getFirst();
    }

    /**
     * Retonra o tamanho do cache atual.
     *
     * @return Quantidade de elementos presentes no cache.
     */
    public Integer size() {
        return cache.size();
    }

}
