package org.example.cache_strategies.fifo;

import org.example.cache_strategies.util.CacheEvictionStrategy;

/**
 * Implementação da estratégia de substituição de cache FIFO.
 * Mantém os elementos em ordem de inserção e remove o mais antigo quando necessário.
 *
 * @param <T> Tipo dos elementos armazenados no cache.
 */
public class FIFOEvictionStrategy<T> implements CacheEvictionStrategy<T> {
    
    /** Cache FIFO interno utilizado para armazenar os elementos */
    private FIFOCache<T> cache;
    
    /**
     * Construtor que inicializa a estratégia FIFO com uma capacidade máxima.
     *
     * @param capacidade Capacidade máxima do cache.
     */
    public FIFOEvictionStrategy(int capacidade) {
        this.cache = new FIFOCache<>(capacidade);
    }

    public FIFOEvictionStrategy() {
        this.cache = new FIFOCache<>();
    }

    /**
     * Consulta a presença de uma chave no cache.
     * Se o elemento já estiver no cache, retorna "hit".
     * Se o elemento não estiver no cache, adiciona-o e retorna "miss".
     *
     * @param chave Elemento a ser consultado no cache.
     * @return "hit" se o elemento estiver presente, "miss" caso contrário.
     */
    public String get(T chave) {
        if(this.cache.isEmpty()) {
            this.cache.addLast(chave);
            return "miss";
        }
        if(this.cache.indexOf(chave) != -1) {
            return "hit";
        } else {
            this.cache.addLast(chave);
            return "miss";
        }
    }

    /**
     * Retorna o próximo elemento que deve ser removido do cache, caso esteja cheio.
     * Segue a política FIFO (remove o elemento mais antigo).
     *
     * @return O elemento a ser removido ou null se o cache não estiver cheio.
     */
    public T getNextEviction() {
        if(!this.cache.isFull()) {
            return null;
        } else {
            return (T) this.cache.getFirst();
        }
    }

    /**
     * Retorna o número de elementos atualmente no cache.
     *
     * @return Tamanho do cache.
     */
    public Integer size() {
        return this.cache.size();
    }
}

