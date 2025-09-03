import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.example.cache_strategies.lfu.lfuCacheEvictionStrategy;

public class LfuTest{
    
    @Test
    public void testLfu(){
        
        lfuCacheEvictionStrategy<Integer> cache = new lfuCacheEvictionStrategy<>(3);
        
        assertEquals(0, cache.size());

        assertNull(cache.getNextEviction());

        assertEquals("miss", cache.get(3));
        assertEquals("miss", cache.get(5));
        assertEquals("hit", cache.get(3));
        assertEquals("hit", cache.get(3));
        assertEquals(2, cache.size());
        assertEquals("miss", cache.get(6));
        assertEquals("hit", cache.get(5));
        assertEquals(6, cache.getNextEviction());
        assertEquals("miss", cache.get(2));
        assertEquals(2, cache.getNextEviction());
        assertEquals(3, cache.size());
                 
    }


    @Test
    public void testCacheFrequenciaMinimaRepetida(){

        lfuCacheEvictionStrategy<String> cache = new lfuCacheEvictionStrategy<>(3);

        assertEquals("miss", cache.get("a"));
        assertEquals("miss", cache.get("b"));
        assertEquals("miss", cache.get("c"));
        assertEquals("hit", cache.get("b"));
        assertEquals("a", cache.getNextEviction());


    }












}
