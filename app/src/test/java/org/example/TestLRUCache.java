//package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.example.cache_strategies.lru_cache.LRUCacheEvictionStrategy;

public class TestLRUCache {

    @Test
    public void testLRU() {
        
        LRUCacheEvictionStrategy<String> cache = new LRUCacheEvictionStrategy<>(4);

        assertNull(cache.getNextEviction());
        assertEquals("miss", cache.get("a"));
        assertEquals("hit", cache.get("a"));
        assertNull(cache.getNextEviction());
        assertEquals("miss", cache.get("b"));
        assertNull(cache.getNextEviction());
        assertEquals("miss", cache.get("c"));
        assertNull(cache.getNextEviction());
        assertEquals("miss", cache.get("d"));
        assertEquals("a", cache.getNextEviction());
        assertEquals(4, cache.size());

        //cache cheio a partir de agora;
        assertEquals("hit", cache.get("a"));
        assertEquals("hit", cache.get("b"));
        assertEquals("hit", cache.get("c"));
        assertEquals("hit", cache.get("d"));

        assertEquals("a", cache.getNextEviction());
        assertEquals("miss", cache.get("e"));
        assertEquals("b", cache.getNextEviction());
        assertEquals("hit", cache.get("b"));
        
        assertEquals("c", cache.getNextEviction());
        assertEquals("hit", cache.get("c"));
        assertEquals("hit", cache.get("d"));
        assertEquals(4, cache.size());

        assertEquals("e", cache.getNextEviction());
        assertEquals("miss", cache.get("a"));
        assertEquals("b", cache.getNextEviction());
        assertEquals(4, cache.size());
    }

    @Test
    public void testCacheUmElemento() {

        LRUCacheEvictionStrategy<String> cache = new LRUCacheEvictionStrategy<>(1);

        assertNull(cache.getNextEviction());
        assertEquals("miss", cache.get("a"));
        assertEquals("a", cache.getNextEviction());

        assertEquals("hit", cache.get("a"));
        assertEquals("miss", cache.get("b"));
        assertEquals("b", cache.getNextEviction());

        assertEquals("miss", cache.get("a"));
        assertEquals(1, cache.size());
    }
}
