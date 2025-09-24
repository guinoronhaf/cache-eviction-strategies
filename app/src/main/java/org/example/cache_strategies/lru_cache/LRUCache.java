package org.example.cache_strategies.lru_cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação da estrátegia de cache LRU
 * Sua política se resume em remover sempre o elemento menos recentemente consultado.
 * Aqui está a Implementação da sua lógica, o qual é construído a partir de um tipo genérico V.
 *
 * @author Brunno Weslley (Github: brunnowxl).
 */
public class LRUCache<V>  {

    /**
     * Mapa utilizado para guardar os elementos do cache.
     */
    private final Map<V, LRUNode<V>> cache;

    /**
     * Capacidade da política de cache.
     */
    private final int capacity;

    /**
     * Capacidade padrão utilizada na intância do cache caso não seja fornecida.
     */
    private static final int CAPACIDADE_DEFAULT = 20;

    /**
     * Apontador do tipo Node que aponta(guarda referência) para o primeiro elemento do cache.
     */
    private LRUNode<V> head;

    /**
     * Apontador do tipo Node que aponta(guarda referência) para o último elemento do cache.
     */
    private LRUNode<V> tail;

    /**
     * Tamanho atual do cache(quantidade de elementos.
     */
    private int size;

    /**
     * Constrói uma estrutura de cache com um tamanho específico e intância o mapa do cache.
     *
     * @param capacidade Capacidade total do cache.
     */
    public LRUCache(int capacidade) { 
        this.capacity = capacidade; 
        this.cache = new HashMap<>();
    }

    /**
     * Constrói uma estrutura de cache com o tamanho padrão específico e intância o mapa do cache.
     */
    public LRUCache() {
        this(CAPACIDADE_DEFAULT);
    }

    /**
     * Retorna um booleno que informa se o cache está vazio.
     *
     * @return True caso o cache esteja vazio, false caso contrário.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Retorna um booleano que informa se o cache está cheio.
     *
     * @return True caso o cache esteja cheio, false caso contrário.
     */
    public boolean isFull() {
        return size() == this.capacity;
    }

    /**
     * Retorna o valor correpondente ao primeiro elemento do cache.
     *
     * @return Primeiro elemento do cache(valor do Node head).
     */
    public V getFirst() {
        return isFull() ? this.head.value : null;
    }

    /**
     * Retorna o tamanho atual do cache.
     *
     * @return A quantidade de elementos presente no cache até o momento.
     */
    public int size() {
        return this.size;
    }

    /**
     * Acessa um valor específico, caso o valor passado como parâmetro esteja no cache, 
     * ele é movido para o final(Node tail) e retornado, caso ele não esteja contido no
     * cache, ele é adicionado no final e retornado o valor null.
     *
     * @param value Valor que queremos acessar.
     * @return Value caso ele esteja contido no cache, null caso não value não esteja contido no cache.
     */
    public V get(V value) {

        if(!cache.containsKey(value)) {
            return null;
        }

        LRUNode<V> node = cache.get(value);
        moveToTail(node);
        return node.value;
    }

    /**
     * Adiciona um valor ao final do cache caso haja espaço. Se não houver espaço, remove o elemento com mais
     * tempo sem acesso, ou seja, o primeiro, e adiciona o valor no final.
     *
     * @param value Valor a ser adicionado ao cache.
     */
    public void addLast(V value) {
        
        if(isFull()) removeFirst();
        LRUNode<V> newNode = new LRUNode<>(value);
        
        if(isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        cache.put(newNode.value, newNode);
        this.size++;

    }

    /**
     * Remove o primeiro elemento do cache.
     *
     * @return Elemento do tipo Node removido.
     */
    public LRUNode<V> removeFirst() {
        
        if(isEmpty()) return null;

        LRUNode<V> toRemove = this.head;
        
        if(size() == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head.next.prev = null;
            this.head = this.head.next;
        }
        this.cache.remove(toRemove.value);
        this.size--;
        return toRemove;

    }

    /**
     * Move o nó passado como parâmetro para o final da fila/cache(Node tail).
     *
     * @param node Nó a ser movido para o final.
     */
    public void moveToTail(LRUNode<V> node) {
 
        if(node == this.tail) return;
        
        if(node == this.head) {
            this.head = node.next;
            this.head.prev = null;
        } else {
            if(node.next != null) node.next.prev = node.prev;
            if(node.prev != null) node.prev.next = node.next;
        }
        
        node.prev = this.tail;
        node.next = null;
        this.tail.next = node;
        this.tail = node;
    
    }


}
