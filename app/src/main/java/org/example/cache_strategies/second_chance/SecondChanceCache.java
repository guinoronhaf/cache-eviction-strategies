package org.example.cache_strategies.second_chance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.example.cache_strategies.second_chance.util.SecondChanceEntry;

/**
 * Implementação da estratégia de cache SecondChance, cuja representação prática mais difundida é chamada de Clock. Aqui está definida
 * a lógica da estrutura de cache, o qual é construído a partir de um tipo genérico T.
 *
 * @author guinoronhaf
 */
public class SecondChanceCache<T> {

    /**
     * Instância de um array de objetos do tipo SecondChanceEntry<T>.
     */
    private SecondChanceEntry<T>[] entryCache;

    /**
     * Apontador inteiro clockPointer, que atravessa o cache de maneira circular, similar aos ponteiros de um relógio.
     */
    private int clockPointer;

    /**
     * Apontador inteiro que indica a posição da última adição de um elemento no cache. Seu valor padrão é -1, já que,
     * ao inicializar o cache, não há nenhum elemento contido nele.
     */
    private int last;

    /**
     * HashSet<T> que armazena os elementos atualmente presentes no cache. Tem-se assim, a verificação da presença de um elemento 
     * em cache em tempo aproximadamente constante, ou seja, O(1).
     */
    private HashSet<T> inCache;

    /**
     * Capacidade padrão (default) da estrutura de cache.
     */
    private static final int CAPACITY_DEFAULT = 10;

    /**
     * Constrói uma estrutura de cache com uma capacidade específica. Além disso, os apontadores inteiros são setados com seus valores
     * iniciais e o array de objetos SecondChanceEntry<T> é devidamente povoado.
     *
     * @param capacity capacidade do cache.
     */
    @SuppressWarnings("unchecked")
    public SecondChanceCache(int capacity) {
        this.entryCache = (SecondChanceEntry<T>[]) new SecondChanceEntry[capacity];
        this.clockPointer = 0;
        this.last = -1;
        this.inCache = new HashSet<>();
        for (int i = 0; i < this.entryCache.length; i++)
            this.entryCache[i] = new SecondChanceEntry<T>(null);
    }

    /**
     * Constrói uma estrutura de cache sem uma capacidade específica. Além disso, os apontadores inteiros são setados com seus valores
     * iniciais e o array de objetos SecondChanceEntry<T> é devidamente povoado.
     */
    public SecondChanceCache() {
        this(CAPACITY_DEFAULT);
    }

    /**
     * Retorna o índice do elemento genérico T buscado no array principal. Caso o elemento não esteja presente, o valor retornado é -1.
     *
     * @param value valor procurado.
     * @return o índice do elemento no array.
     */
    private int indexOf(T value) {

        if (value == null)
            throw new IllegalArgumentException();

        for (int i = 0; i <= this.last; i++) {
            if (this.entryCache[i].getValue().equals(value))
                return i;
        }

        return -1;

    }

    /**
     * Retorna o índice do próximo elemento do cache candidato a sair, considerando o valor da flag booleana. Além disso, a cada iteração, caso
     * um valor com a flag "false" seja encontrado, a flag é setada para "true".
     *
     * @return o índice do próximo elemento a sair do cache através da travessia do ponteiro circular.
     */
    private int indexOfNextEvictionable() {

        if (this.isEmpty())
            throw new NoSuchElementException();

        int idx = clockPointer;

        while (!this.entryCache[idx].isEvictionable()) {
            this.entryCache[idx].setEvictionable(true);
            idx = (idx + 1) % this.entryCache.length;
        }

        return idx;

    }

    /**
     * Retorna se o cache está vazio.
     *
     * @return booleano que indica se o cache está vazio ou não.
     */
    public boolean isEmpty() {
        return this.last == -1;
    }

    /**
     * Retorna se o cache está cheio.
     *
     * @return booleano que indica se o cache está cheio ou não.
     */
    public boolean isFull() {
        return this.last == this.entryCache.length - 1;
    }

    /**
     * Atualiza a referência para o elemento do tipo genérico T. Caso esse valor
     * esteja presente no cache, sua flag "evictionable" é setada para false, o 
     * que indica que o elemento tem mais uma chance de permanecer no cache. Caso
     * contrário, adiciona-se o elemento no cache.
     *
     * @param value valor a ter a referência atualizada.
     */
    public void updateReference(T value) {

        int idxValue = indexOf(value);

        if (idxValue == -1)
            this.add(value);
        else
            this.entryCache[idxValue].setEvictionable(false);

    }

    /**
     * Adiciona o elemento no cache caso não esteja. Para tal, verifica-se qual elemento
     * deve sair a partir do ponteiro circular "clock" e substitui-se o valor associado ao 
     * SecondChanceEntry naquela posição. Além disso, todo elemento adicionado em cache tem 
     * sua flag booleana "evictionable" setada para false.
     *
     * @param value elemento a ser adicionado no cache.
     */
    public void add(T value) {

        if (this.inCache.contains(value)) {
            this.updateReference(value);
        } else {
            if (this.isFull()) {
                int idxEvictionable = indexOfNextEvictionable();
                this.inCache.remove(this.entryCache[idxEvictionable].getValue());
                this.entryCache[idxEvictionable].setValue(value);
                this.entryCache[idxEvictionable].setEvictionable(false);;
                clockPointer = (idxEvictionable + 1) % this.entryCache.length;
            } else {
                this.entryCache[++this.last].setValue(value);
            }
            this.inCache.add(value);
        }

    }

    /**
     * Retorna o valor associado ao elemento presente na posição indicada pelo ponteiro
     * circular "clock", desconsiderando todo o processo de travessia pelo array e o "set"
     * das flags booleanas.
     *
     * @return valor associado ao SecondChanceEntry na posição indicada pelo ponteiro circular "clock".
     */
    public T getNextEvictionable() {
        return this.entryCache[clockPointer].getValue();
    }

    /**
     * Indica se determinado elemento do tipo genérico T está presente no cache. Para tal, verifica-se se 
     * esse mesmo elemento está contido no HashSet<T> auxiliar da classe.
     *
     * @param value elemento que deve-se saber se está presente no cache.
     * @return booleano que indica se o elemento está presente ou não.
     */
    public boolean contains(T value) {
        return this.inCache.contains(value);
    }

    /**
     * Retorna a quantidade de elementos em cache.
     *
     * @return a quantidade de elementos em cache.
     */
    public int size() {
        return this.last + 1;
    }

    /**
     * Representação, em String, do cache.
     *
     * @return representação, em String, do cache.
     */
    @Override
    public String toString() {
        return Arrays.toString(this.entryCache);
    }

}
