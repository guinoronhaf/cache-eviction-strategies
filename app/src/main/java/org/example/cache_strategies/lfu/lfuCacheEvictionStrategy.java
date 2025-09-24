package org.example.cache_strategies.lfu;

import org.example.cache_strategies.util.CacheEvictionStrategy; 

public class LfuCacheEvictionStrategy<T> implements CacheEvictionStrategy<T>{

    private LfuCache<T> cache;

    public LfuCacheEvictionStrategy(int capacity){
        this.cache = new LfuCache<>(capacity);
    }

    public LfuCacheEvictionStrategy(){
        this.cache = new LfuCache<>();
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
       if(!this.cache.isFull()){
            return null;
       } 
       return this.cache.getNextEviction(); 
    }

    public Integer size(){
        return this.cache.size(); 
    }
 
}


