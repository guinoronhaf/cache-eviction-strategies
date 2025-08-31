package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.example.cache_strategies.fifo.FIFOEvictionStrategy;

public class FIFOCacheTest {

    @Test
    void test() {

        FIFOEvictionStrategy<Integer> cache = new FIFOEvictionStrategy<>(3);

        assertEquals(0, cache.size());
        // cache vazio
        
        assertNull(cache.getNextEviction());
        // cache não está cheio.

        assertEquals("miss", cache.get(7));
        // [7, null, null]
        assertEquals(1, cache.size());
        // cache com 1 elemento.
        assertNull(cache.getNextEviction());
        // cache não está cheio.
        assertEquals("miss", cache.get(0));
        // [7, 0, null]
        assertEquals(2, cache.size());
        // cache com 2 elementos
        assertNull(cache.getNextEviction());
        // cache não está cheio.
        assertEquals("miss", cache.get(1));
        // [7, 0, 1]
        assertEquals(3, cache.size());
        // cache com 3 elementos.
        
        assertEquals(7, cache.getNextEviction());
        // 7 é o primeiro da fila;
        
        assertEquals("miss", cache.get(2));
        // [0, 1, 2]
        // 7 retirado
        assertEquals(0, cache.getNextEviction());
        // agora o 0 é o próximo a sair

        assertEquals("hit", cache.get(0));
        // 0 está presente na fila 
        assertEquals("miss", cache.get(3));
        // o 0 sai e agora nosso cache armazena [1, 2, 3]

        assertEquals(3, cache.size());
        // 3 elementos em cache.

    }

}
