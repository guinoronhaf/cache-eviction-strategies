package org.example.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.example.cache_strategies.fifo.FIFOEvictionStrategy;
import org.example.cache_strategies.lfu.lfuCacheEvictionStrategy;
import org.example.cache_strategies.lru_cache.LRUCacheEvictionStrategy;
import org.example.cache_strategies.random_replace.RandomReplaceEvictionStrategy;
import org.example.cache_strategies.second_chance.SecondChanceEvictionStrategy;
import org.example.cache_strategies.util.CacheEvictionStrategy;

public class MainWorkload {

    private static final String FIFO_CACHE = "fifo";
    private static final String LRU_CACHE = "lru";
    private static final String LFU_CACHE = "lfu";
    private static final String RANDOM_CACHE = "random";
    private static final String SECOND_CHANCE_CACHE = "second_chance";

    public static void main(String[] args) {

        try {

            String cacheStrategy = args[0].toLowerCase();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            CacheEvictionStrategy<Integer> evictionStrategy;

            switch (cacheStrategy) {
                case FIFO_CACHE:
                    evictionStrategy = new FIFOEvictionStrategy<>(30);
                    break;
                case LRU_CACHE:
                    evictionStrategy = new LRUCacheEvictionStrategy<>(30);
                    break;
                case LFU_CACHE:
                    evictionStrategy = new lfuCacheEvictionStrategy<>(30);
                    break;
                case RANDOM_CACHE:
                    evictionStrategy = new RandomReplaceEvictionStrategy<>(30);
                    break;
                case SECOND_CHANCE_CACHE:
                    evictionStrategy = new SecondChanceEvictionStrategy<>(30);
                    break;
                default:
                    throw new IllegalArgumentException("ERROR!");
            }

            int workloadSize = 0;
            int hitAmount = 0;
            int missAmount = 0;

            String line = "";

            while ((line = reader.readLine()) != null) {
    
                int value = Integer.parseInt(line);

                String result = evictionStrategy.get(value);

                if (result.equals("hit"))
                    hitAmount++;
                else if (result.equals("miss"))
                    missAmount++;

                workloadSize++;

            }

            System.out.println(workloadSize + " " + hitAmount + " " + missAmount);

        } catch(IOException ioe) {}

    }

}
