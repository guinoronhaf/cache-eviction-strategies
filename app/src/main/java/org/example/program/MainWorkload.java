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

/**
 * Classe responsável por devolver os dados de hit de cada política a partir de um arquivo
 * .txt vindo da entrada-padrão.
 *
 * [MODO DE USO]
 * 1 - caso sua política ainda não possua um diretório próprio em app/data/output/, crie um.
 * 2 - em cada um desses subdiretórios próprios, deve existir três arquivos .data: randomic_output, periodic_output e spike_output.
 * 3 - em cada um dos três arquivos criados, deve existir um cabeçalho para ajudar no plot dos gráficos:
 *      
 *      cacheStrategy WorkloadLength Hits
 *
 * 4 - para cada um dos inputs.txt, execute esse arquivo .java com o .txt entrada padrão e direcione para o output adequado, passando o nome da sua política como parâmetro.
 *
 *      gradle runMainWorkload --quiet --args="fifo" < app/data/input/spike_input_50000.txt >> app/data/output/fifo/spike_output.data
 *
 *  OBS.: atenção para o --quiet no comando gradle e no ">>" e não ">".
 */
public class MainWorkload {

    private static final String FIFO_CACHE = "fifo";
    private static final String LRU_CACHE = "lru";
    private static final String LFU_CACHE = "lfu";
    private static final String RANDOM_CACHE = "random";
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
                    evictionStrategy = new lfuCacheEvictionStrategy<>(CACHE_CAPACITY);
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

            int workloadSize = 0;
            int hitAmount = 0;

            String line = "";

            while ((line = reader.readLine()) != null) {
    
                int value = Integer.parseInt(line);

                String result = evictionStrategy.get(value);

                if (result.equals("hit"))
                    hitAmount++;

                workloadSize++;

            }

            System.out.println(cacheStrategy + " " + workloadSize + " " + hitAmount);

        } catch(IOException ioe) {}

    }

}
