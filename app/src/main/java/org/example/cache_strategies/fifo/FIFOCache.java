package org.example.cache_strategies.fifo;

/**
 * Implementação de um cache do tipo FIFO (First In, First Out) genérico.
 * 
 * @param <T> Tipo dos elementos armazenados no cache.
 */
public class FIFOCache<T> {
    /** Array que armazena os elementos do cache */
    private T[] fila;

    /** Índice do primeiro elemento (head) */
    private int head;

    /** Índice do último elemento (tail) */
    private int tail;

    /** Número de elementos atualmente no cache */
    private int size;

    private static final int CAPACITY_DEFAULT = 10;

    /**
     * Construtor que inicializa o cache com uma capacidade fixa.
     *
     * @param capacidade Capacidade máxima do cache.
     */
    @SuppressWarnings("unchecked")
    public FIFOCache(int capacidade) {
        fila = (T[]) new Object[capacidade];
        this.head = -1;
        this.tail = -1;
        this.size = 0;
    }

    public FIFOCache() {
        this(CAPACITY_DEFAULT);
    }

    /**
     * Verifica se o cache está vazio.
     *
     * @return true se o cache estiver vazio, false caso contrário.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Verifica se o cache está cheio.
     *
     * @return true se o cache estiver cheio, false caso contrário.
     */
    public boolean isFull() {
        return this.size == this.fila.length;
    }

    /**
     * Adiciona um elemento ao final do cache. Caso o cache esteja cheio, remove o primeiro elemento.
     *
     * @param valor Elemento a ser adicionado.
     */
    public void addLast(T valor) {
        if(isFull()) {
            removeFirst();
        }
        if(this.size == 0) {
            this.head++;
        }
        this.tail = (this.tail + 1) % this.fila.length;
        this.fila[this.tail] = valor;
        this.size++;
    }

    /**
     * Remove e retorna o primeiro elemento do cache.
     *
     * @return O elemento removido.
     * @throws RuntimeException Se o cache estiver vazio.
     */
    public T removeFirst() {
        if(isEmpty()) {
            throw new RuntimeException("Esta linha não pode ser executada.");
        }
        T toRemove = this.fila[this.head];
        this.head = (this.head + 1) % this.fila.length;
        this.size--;
        return toRemove;
    }
    
    /**
     * Retorna o primeiro elemento do cache sem removê-lo.
     *
     * @return O primeiro elemento.
     * @throws RuntimeException Se o cache estiver vazio.
     */
    public T getFirst() {
        if(isEmpty()) {
            throw new RuntimeException("Esta linha não pode ser executada.");
        }
        return (T) this.fila[this.head];
    }

    /**
     * Retorna o último elemento do cache sem removê-lo.
     *
     * @return O último elemento.
     * @throws RuntimeException Se o cache estiver vazio.
     */
    public T getLast() {
        if(isEmpty()) {
            throw new RuntimeException("Esta linha não pode ser executada.");
        }
        return this.fila[this.tail];
    }

    /**
     * Retorna uma representação em String do cache, do primeiro ao último elemento.
     *
     * @return String representando os elementos do cache.
     */
    public String toString() {
        int headAux = this.head;
        String out = "";
        while(headAux % this.fila.length < this.tail % this.fila.length) {
            out += this.fila[headAux++] + ", ";
        }
        out += this.fila[headAux];
        return out;
    }

    /**
     * Retorna o índice de um elemento no cache.
     *
     * @param value Elemento a ser buscado.
     * @return Índice do elemento ou -1 se não encontrado.
     */
    public int indexOf(T value) {
        int count = 0;
        while(count < this.size) {
            if(this.fila[(this.head + count) % this.fila.length].equals(value)) {
                return (this.head + count) % this.fila.length;
            } else {
                count++;
            }
        }
        return -1;
    }

    /**
     * Retorna o número de elementos atualmente no cache.
     *
     * @return Tamanho do cache.
     */
    public int size() {
        return this.size;
    }

}

