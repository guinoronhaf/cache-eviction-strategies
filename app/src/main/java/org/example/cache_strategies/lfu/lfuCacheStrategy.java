package org.example.cache_strategies.lfu;

import org.example.cache_strategies.util.CacheEvictionStrategy; 

public class lfuCacheStrategy<T> implements CacheEvictionStrategy<T>{

    private lfuCache<T> cache;

    public lfuCacheStrategy(int capacity){
        this.cache = new lfuCache<>(capacity);
    }

    public lfuCacheStrategy(){
        this.cache = new lfuCache<>();
    }

    
    public String get(T value){
        if(this.cache.contains(value)){
            this.cache.get(value);
            return "hit"; 
        }else{
            this.cache.put(value);
            return "miss";
        }


    }

    
    public T getNextEviction() {
       return this.cache.getNextEviction(); 
    }

    public Integer size(){
        return this.cache.size(); 
    }
 
}


