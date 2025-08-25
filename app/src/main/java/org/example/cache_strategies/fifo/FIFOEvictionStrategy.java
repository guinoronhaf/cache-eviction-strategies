public class FIFOEvictionStrategy<T> {
    
    FIFOCache<T> cache;
    
    public FIFOEvictionStrategy(int capacidade) {
        this.cache = new FIFOCache<>(capacidade);
    }

    public String get(T chave) {
        if(this.cache.isEmpty()) {
            this.cache.addLast(chave);
            return "miss";
        }
        if(this.cache.indexOf(chave) != -1) {
            return "hit";
        } else {
            this.cache.addLast(chave);
            return "miss";
        }
    }

    public T getNextEviction() {
        if(!this.cache.isFull()) {
            return null;
        } else {
            return (T) this.cache.getFirst();
        }
    }

    public int size() {
        return this.cache.size();
    }
}
