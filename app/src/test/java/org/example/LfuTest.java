import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.example.cache_strategies.lfu.lfuCacheStrategy;

public class LfuTest{
    
    @Test
    public void testLfu(){
        
        lfuCacheStrategy<Integer> cache = new lfuCacheStrategy<>(4);

        assertNull(cache.getNextEviction());
        assertEquals("miss", cache.get(3));
        assertEquals("miss", cache.get(5));
        assertEquals("hit", cache.get(3));
        assertEquals("hit", cache.get(3));
        assertEquals(2, cache.size());
        assertEquals("miss", cache.get(6));
        assertEquals("hit", cache.get(5));
        assertEquals("miss", cache.get(10));
        assertEquals(3, cache.getNextEviction());
        assertEquals("miss", cache.get(2));
        assertEquals(5, cache.getNextEviction());
        
    
    }












}
