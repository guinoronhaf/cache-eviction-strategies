package org.example.cache_strategies.random_replace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe RandomReplaceEvictionStrategy.
 * <p>
 * Cobre os casos de inserção, hit/miss, capacidade máxima, substituição aleatória,
 * obtenção do próximo elemento a ser removido e representação em string.
 */
class RandomReplaceTest {

    /** Instância do cache utilizada nos testes. */
    private RandomReplaceEvictionStrategy<String> cache;

    /**
     * Inicializa o cache com capacidade 3 antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        cache = new RandomReplaceEvictionStrategy<>(3);
    }

    /**
     * Testa o caso de miss na primeira inserção.
     * <p>
     * Verifica se o retorno é "miss" e se o tamanho do cache aumenta corretamente.
     */
    @Test
    void testMissOnFirstInsert() {
        assertEquals("miss", cache.get("A"));
        assertEquals(1, cache.size());
    }

    /**
     * Testa o caso de hit após inserir o valor no cache.
     * <p>
     * Verifica se o retorno é "hit" e se o tamanho do cache permanece correto.
     */
    @Test
    void testHitAfterInsert() {
        cache.get("A");
        assertEquals("hit", cache.get("A"));
        assertEquals(1, cache.size());
    }

    /**
     * Testa múltiplos misses e hits subsequentes.
     * <p>
     * Insere três elementos diferentes e verifica "miss" em cada um.
     * Em seguida, verifica "hit" ao acessar novamente os mesmos elementos.
     */
    @Test
    void testMultipleMisses() {
        assertEquals("miss", cache.get("A"));
        assertEquals("miss", cache.get("B"));
        assertEquals("miss", cache.get("C"));
        assertEquals(3, cache.size());

        assertEquals("hit", cache.get("C"));
        assertEquals("hit", cache.get("A"));
        assertEquals("hit", cache.get("B"));
    }

    /**
     * Testa a substituição aleatória quando o cache atinge a capacidade máxima.
     * <p>
     * Após preencher o cache, adiciona um novo elemento e verifica que o tamanho permanece
     * igual à capacidade máxima. Também verifica se o elemento inserido agora existe no cache.
     */
    @Test
    void testCapacityEviction() {
        cache.get("A");
        cache.get("B");
        cache.get("C");
        assertTrue(cache.size() == 3);

        // Adiciona mais um valor; algum elemento existente deve ser substituído
        cache.get("D");
        assertEquals("hit", cache.get("D"));
        assertEquals(3, cache.size());
    }

    /**
     * Testa o comportamento de getNextEviction quando o cache não está cheio.
     * <p>
     * O método deve retornar null enquanto o cache não atingir a capacidade máxima.
     */
    @Test
    void testGetNextEvictionNotFull() {
        cache.get("A");
        assertNull(cache.getNextEviction());
        cache.get("B");
        assertNull(cache.getNextEviction());
    }

    /**
     * Testa o comportamento de getNextEviction quando o cache está cheio.
     * <p>
     * O método deve retornar um elemento válido do cache, garantindo que
     * não seja nulo.
     */
    @Test
       void testGetNextEvictionFull() {
        cache.get("A");
        cache.get("B");
        cache.get("C");

        String evicted = cache.getNextEviction();
        assertNotNull(evicted);

		assertEquals("hit", cache.get(evicted));
		cache.get("E");
		assertEquals("miss", cache.get(evicted));

    }	
    /**
     * Testa a representação em string do cache.
     * <p>
     * Verifica se o método toString retorna uma string não nula e se contém o nome da classe.
     */
    @Test
    void testToString() {
        cache.get("A");
        assertNotNull(cache.toString());
        assertTrue(cache.toString().contains("RandomReplaceCache"));
    }
}

