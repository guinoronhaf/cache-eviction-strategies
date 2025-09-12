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

public class MainPerformance {

    private static final String FIFO_CACHE = "fifo";
    private static final String LRU_CACHE = "lru";
    private static final String LFU_CACHE = "lfu";
    private static final String RANDOM_CACHE = "random_replacement";
    private static final String SECOND_CHANCE_CACHE = "second_chance";

    // A capacidade do cache pode ser ajustada aqui.
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
                    evictionStrategy = new lfuCacheEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                case RANDOM_CACHE:
                    evictionStrategy = new RandomReplaceEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                case SECOND_CHANCE_CACHE:
                    evictionStrategy = new SecondChanceEvictionStrategy<>(CACHE_CAPACITY);
                    break;
                default:
                    throw new IllegalArgumentException("Estratégia de cache desconhecida: " + cacheStrategy);
            }

            String line;
    
            int hitCounter = 0;
            int missCounter = 0;

            // Usamos 'long' para acumular o tempo total em nanosegundos e evitar overflow.
            long totalHitTime = 0;
            long totalMissTime = 0;

            // Loop principal que processa cada linha do workload
            while ((line = reader.readLine()) != null) {
                // Remove espaços em branco que possam existir na linha
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue; // Pula linhas vazias
                }

                long start = System.nanoTime();

                String result = evictionStrategy.get(Integer.parseInt(trimmedLine));

                long end = System.nanoTime();

                long timeElapsed = end - start;

                if (result.equals("hit")) {
                    hitCounter++;
                    totalHitTime += timeElapsed;
                } else if (result.equals("miss")) {
                    missCounter++;
                    totalMissTime += timeElapsed;
                }
            }
            
            // Calcula o tempo médio, tratando o caso de divisão por zero.
            // O resultado é em nanosegundos (ns).
            double averageHitTime = (hitCounter > 0) ? (double) totalHitTime / hitCounter : 0.0;
            double averageMissTime = (missCounter > 0) ? (double) totalMissTime / missCounter : 0.0;

            // Calcula o tamanho total do workload, a partir da soma entre as quantidades de hit e miss.
            int workloadLength = hitCounter + missCounter;

            // Imprime a saída no formato: [tamanho_do_cache] [tempo_medio_hit] [tempo_medio_miss]
            System.out.println(cacheStrategy + " " + workloadLength + " " + CACHE_CAPACITY + " " + averageHitTime + " " + averageMissTime);

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler a entrada: " + e.getMessage());
        }
		
	}

}

