import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;

public class LRU<K, V>  {

    private final Map<K, Node<K, V>> cache;
    private final int capacity;
    private static final int CAPACIDADE_DEFAULT = 20;
    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public LRU(int capacidade) { 
        this.capacity = capacidade; 
        this.cache = new HashMap<>();
    }

    public LRU() {
        this(CAPACIDADE_DEFAULT);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isFull() {
        return size() == this.capacity;
    }

    public int size() {return this.size;}

    public Node<K, V> get(K key) {

        if(isEmpty()) throw new NoSuchElementException();

        if(!cache.containsKey(key)) return null;

        Node<K, V> node = cache.get(key);
        moveToTail(node);
        return node;
    }

    public void put(K key, V value) {
        
        if(isFull()) removeFirst();

        if(cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            node.value = value;
            moveToTail(node);
        } else {
            if(isEmpty()) {
                Node<K, V> node = new Node(key, value);
                cache.put(key, node);
                this.head = node;
                this.tail = node;
            } else {    
                Node<K, V> node = new Node(key, value);
                moveToTail(node);
            }
        }

    }

    public Node<K, V> removeFirst() {
        
        if(isEmpty()) throw new NoSuchElementException();

        Node toRemove = this.head;
        this.head.next.prev = null;
        this.head = this.head.next;
        return toRemove;

    }

    public void moveToTail(Node<K, V> node) {
    
        if(isEmpty()) throw new NoSuchElementException();

        if(node.prev != null) node.prev.next = node.next;
        if(node.next != null) node.next.prev = node.prev;
        this.tail.next = node;
        node.prev = this.tail;
        node.next = null;
        this.tail = node;
    
    }


}
