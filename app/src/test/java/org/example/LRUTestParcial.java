package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    public void testLRU() {
        
        LRU cache = new LRU(5);
        assertNull(cache.get(5));
        assertEquals(0, cache.size());
        
        cache.put(1, "um");
        assertEquals(new Node<Integer, String>, cache.(1));


    }

    @Test
    public void testStrategy() {
        
        LRUEvictionStrategy lrucache = new LRUEvictionStrategy(4);
        assertNull(lrucache.getNextEviction());
        assertEquals(0, lrucache.size());
        assertEquals("miss", lrucache.get("a"));

        // cache status: ["a", null, null, null]
        assertNull(lrucache.getNextEviction());
        assertEquals(1, lrucache.size());
        assertEquals("hit", lrucache.get("a"));
        assertEquals(1, lrucache.size());

        assertEquals("miss", lrucache.get("b"));
        assertNull(lrucache.getNextEviction());
        assertEquals("miss", lrucache.get("c"));
        assertEquals("miss", lrucache.get("d"));

        // cache status: ["a", "b", "c", "d"]
        assertEquals("a", lrucache.getNextEviction());

        assertEquals("hit", lrucache.get("b"));
        // cache status: ["a", "c", "d", "b"]. "b" foi para o final da fila
        // porque foi acessado.
        assertEquals("a", lrucache.getNextEviction());
        
        assertEquals("hit", lrucache.get("a"));
        // cache status: ["c", "d", "b", "a"]. "a" foi para o final da fila
        // porque foi acessado. "c" passa a ser o mais "frio".

        assertEquals("miss", lrucache.get("e"));
        // cache status: ["d", "b", "a", "e"]. "e" foi para o final da fila
        // porque entrou no cache agora. "c" foi removido do cache, porque era
        // o mais frio (cabeça da fila).
        assertEquals("d", lrucache.getNextEviction());

        // "c" teve que sair para a entrada do "e"
        assertEquals("miss", lrucache.get("c"));
        // "c" volta.
        // cache status: ["b", "a", "e", "c"]. "c" foi para o final da fila
        // porque entrou no cache agora. "d" foi removido do cache, porque era
        // o mais frio (cabeça da fila).

        assertEquals("b", lrucache.getNextEviction());
        assertEquals("hit", lrucache.get("e"));
        // cache status: ["b", "a", "c", "e"].
        assertEquals("b", lrucache.getNextEviction());
        assertEquals("miss", lrucache.get("x"));
        // cache status: ["a", "c", "e", "x"].
        assertEquals("miss", lrucache.get("y"));
        // cache status: ["c", "e", "x", "y"].
        assertEquals("miss", lrucache.get("z"));
        // cache status: ["e", "x", "y", "z"].
        assertEquals("e", lrucache.getNextEviction());
        assertEquals("miss", lrucache.get("w"));
        // cache status: ["x", "y", "z", "w"].
        assertEquals(4, lrucache.size());
        assertEquals("x", lrucache.getNextEviction());    
    }

    @Test
    public void testsCache() {

        LRUCache cache = new LRUCache(4);
        assertTrue(cache.isEmpty());
        assertEquals(0, cache.size());

        cache.get("a");
        cache.addLast("b");
        assertEquals(2, cache.size());
        cache.addLast("c");
        assertEquals(3, cache.size());
        cache.addLast("d");

        assertEquals("a", cache.getFirst());
        assertEquals("d", cache.getLast());
        assertEquals(4, cache.size());

        assertEquals("b", cache.get("b"));
        assertEquals("a", cache.getFirst());
        assertEquals("b", cache.getLast());

    }

}
