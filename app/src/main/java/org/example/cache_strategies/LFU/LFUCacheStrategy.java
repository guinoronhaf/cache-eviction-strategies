import java.util.HashMap; 
import java.util.LinkedList;
import java.util.Map;


public class LFUCacheStrategy<K, V> {

    private final int capacity;
    private int minFrequency;
    private final Map<K, V> itemMap;
    private final Map<K, Integer> freqMap;
    private final Map<Integer, LinkedList<K>> freqListMap;

    public LFUCacheStrategy(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.itemMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.freqListMap = new HashMap<>();
    }

    public V get(K key) {
        if (!itemMap.containsKey(key)) {
            return null;
        }

        int currentFreq = freqMap.get(key);
        LinkedList<K> currentList = freqListMap.get(currentFreq);
        currentList.remove(key);

        if (currentList.isEmpty() && currentFreq == minFrequency) {
            minFrequency++;
        }

        int newFreq = currentFreq + 1;
        freqMap.put(key, newFreq);
        freqListMap.putIfAbsent(newFreq, new LinkedList<>());
        freqListMap.get(newFreq).addFirst(key);

        return itemMap.get(key);
    }

    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }

        if (itemMap.containsKey(key)) {
            itemMap.put(key, value);
            get(key);
            return;
        }

        if (itemMap.size() >= capacity) {
            LinkedList<K> minFreqList = freqListMap.get(minFrequency);
            K keyToRemove = minFreqList.removeLast();
            itemMap.remove(keyToRemove);
            freqMap.remove(keyToRemove);
        }

        itemMap.put(key, value);
        freqMap.put(key, 1);
        freqListMap.putIfAbsent(1, new LinkedList<>());
        freqListMap.get(1).addFirst(key);
        minFrequency = 1;
    }

}