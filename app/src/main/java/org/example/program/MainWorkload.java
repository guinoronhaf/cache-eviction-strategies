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
 * Classe responsável por selecionar a política e gerar os resultados do método "get"
 * de cada 'eviction strategy'.
 *
 * MODO DE USO:
 * 1 - no diretório scripts/, utilize os arquivos main_randomic.py e main_spike.py para
 * gerar os workloads e coloque-os nos arquivos apropriados. Leia a documentação de cada
 * 'main' para entender os estilos dos argumentos.
 *
 * Ex.:
 *     python3 ./main_spike.py 150000 1500 100000 > ../data/input/spike_input.txt
 *
 * 2 - No DIRETÓRIO RAIZ DO PROJETO, com o arquivo de input em mãos, execute esta classe Java para obter os resultados
 * dos testes de carga para sua política de cache
 * 
 * Ex.:
 *     gradle runMainWorkload --quiet --args="second_chance" < app/data/input/spike_input.txt > app/data/output/second_chance/spike_output.txt
 *
 * o output é <tamanho do workload>-<quantidade de hits>
 *
 * 
 * --IMPORTANTE--
 *  É importante que, na primeira linha de cada arquivo de output, você coloque o seguinte cabeçalho:
 *
 *  Workload length-Hits
 *
 *
 *  
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
