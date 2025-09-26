package org.example.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.example.cache_strategies.fifo.FIFOEvictionStrategy;
import org.example.cache_strategies.lfu.LfuCacheEvictionStrategy;
import org.example.cache_strategies.lru_cache.LRUCacheEvictionStrategy;
import org.example.cache_strategies.random_replace.RandomReplaceEvictionStrategy;
import org.example.cache_strategies.second_chance.SecondChanceEvictionStrategy;
import org.example.cache_strategies.util.CacheEvictionStrategy;

/**
 * Classe responsável por gerar outputs para a quantidade de hits de cada política.
 *
 * @author Artur (Github: ArturALW)
 * @author Brunno (Github: Brunnowxl)
 * @author Guilherme (Github: guinoronhaf)
 * @author Victor França (Github: victorfrancacg)
 * @author Vitor Hugo (Github: vitorh333)
 */
public class MainWorkload {

    private static final String FIFO_CACHE = "fifo";
    private static final String LRU_CACHE = "lru";
    private static final String LFU_CACHE = "lfu";
    private static final String RANDOM_CACHE = "random_replacement";
    private static final String SECOND_CHANCE_CACHE = "second_chance";

    private static final int CACHE_CAPACITY = 10000;

    public static void main(String[] args) {

        try {

            String cacheStrategy = args[0].toLowerCase();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            CacheEvictionStrategy<Integer> evictionStrategy;

            switch (cacheStrategy) {
                case FIFO_CACHE:
                    evictionStrategy = new FIFOEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                case LRU_CACHE:
                    evictionStrategy = new LRUCacheEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                case LFU_CACHE:
                    evictionStrategy = new LfuCacheEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                case RANDOM_CACHE:
                    evictionStrategy = new RandomReplaceEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                case SECOND_CHANCE_CACHE:
                    evictionStrategy = new SecondChanceEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                default:
                    throw new IllegalArgumentException("ERROR!");
            }

            String line;
    
            int hitCounter = 0;
			int missCounter = 0;

            while ((line = reader.readLine()) != null) {

                String result = evictionStrategy.get(Integer.parseInt(line));

                if (result.equals("hit")) {
                    hitCounter++;	
				} else {
					missCounter++;
				}
			}

			int workloadLength = missCounter + hitCounter;

            System.out.println(cacheStrategy + " "  +  workloadLength + " " +  hitCounter);

        } catch(IOException ioe) {}

    }

}
