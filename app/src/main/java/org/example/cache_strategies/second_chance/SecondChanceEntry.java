package org.example.cache_strategies.second_chance;

/**
 * Representação de um Entry para o cache Second Chance, que contém o valor, do tipo genérico T, a ser armazenado
 * e uma flag booleana que indica se o elemento contido no Entry pode ser retirado do cache. A flag booleana representa
 * um bit de referência, que indica a possibilidade do elemento de deixar a estrutura de cache.
 *
 * @author Guilherme (Github: guinoronhaf)
 */
public class SecondChanceEntry<T> {

    /**
     * Valor armazenado em cada posição do cache, envolto pelo Entry. O valor é do tipo genérico T.
     */
    private T value;

    /**
     * Flag booleana que indica se o elemento pode ou não deixar o cache.
     */
    private boolean evictionable;

    /**
     * Constrói um SecondChanceEntry a partir de um valor do tipo genérico T. Além disso, o valor da 
     * flag booleana é setado para false.
     *
     * @param value valor a ser armazenado no Entry.
     */
    public SecondChanceEntry(T value) {
        this.value = value;
        this.evictionable = false;
    }

    /**
     * Retorna o valor, do tipo genérico T, armazenado no Entry.
     *
     * @return o valor armazenado no Entry.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Retorna a flag do Entry, que indica se o elemento pode ou não deixar o cache.
     *
     * @return a flag booleana do Entry.
     */
    public boolean isEvictionable() {
        return this.evictionable;
    }

    /**
     * Modifica o valor genérico T armazenado no Entry.
     *
     * @param value valor a ser armazenado no Entry.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Modifica a flag booleana que indica se o elemento pode ou não deixar o cache.
     *
     * @param evictionable valor booleano a ser setado para a flag.
     */
    public void setEvictionable(boolean evictionable) {
        this.evictionable = evictionable;
    }

    /**
     * Retorna um representação, em String, do objeto Entry.
     */
    @Override
    public String toString() {
        return "<" + this.value + ", " + this.evictionable + ">";
    }

}
