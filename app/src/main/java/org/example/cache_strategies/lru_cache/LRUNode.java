package org.example.cache_strategies.lru_cache;

/**
 * Implementação da classe LRUNode construída a partir de um tipo genérico T e responsável por simular
 * um nó e manter o comportamento de uma LinkedList na classe LRUCache.
 *
 * @author Brunno Weslley(Github: brunnowxl).
 */
public class LRUNode<V> {

    /**
     * Valor guardado pelo nó
     */
    V value;

    /**
     * Nó responsável por guardar a referência para o nó subsequente a esse.
     */
    LRUNode<V> next;
    
    /**
     * Nó responsável por guardar a referência para o nó anterior a esse.
     */
    LRUNode<V> prev;

    /**
     * Constrói um objeto do tipo LRUNode com um valor específica.
     *
     * @param value Valor do nó.
     */
    public LRUNode(V value) {
        this.value = value;
    }

} 
