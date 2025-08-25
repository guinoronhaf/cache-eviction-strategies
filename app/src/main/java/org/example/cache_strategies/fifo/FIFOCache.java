public class FIFOCache<T> {
    private T[] fila;
    private int head;
    private int tail;
    private int size;

    @SuppressWarnings("unchecked")
    public FIFOCache(int capacidade) {
        fila = (T[]) new Object[capacidade];
        this.head = -1;
        this.tail = -1;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.fila.length;
    }

    public void addLast(T valor) {
        if(isFull()) {
            removeFirst();
        }
        if(this.size == 0) {
            this.head++;
        }
        this.tail = (this.tail + 1) % this.fila.length;
        this.fila[this.tail] = valor;
        this.size++;
    }

    public T removeFirst() {
        if(isEmpty()) {
            throw new RuntimeException("Esta linha não pode ser executada.");
        }
        T toRemove = this.fila[this.head];
        this.head = (this.head - 1) % this.fila.length;
        this.size--;
        return toRemove;
    }
    
    public T getFirst() {
        if(isEmpty()) {
            throw new RuntimeException("Esta linha não pode ser executada.");
        }
        return (T) this.fila[this.head];
    }

    public T getLast() {
        if(isEmpty()) {
            throw new RuntimeException("Esta linha não pode ser executada.");
        }
        return this.fila[this.tail];
    }

    public String toString() {
        int headAux = this.head;
        String out = "";
        while(headAux % this.fila.length < this.tail % this.fila.length) {
            out += this.fila[headAux++] + ", ";
        }
        out += this.fila[headAux];
        return out;
    }

    public int indexOf(T value) {
        int count = 0;
        while(count < this.size) {
            if(this.fila[(this.head + count) % this.fila.length].equals(value)) {
                return (this.head + count) % this.fila.length;
            } else {
                count++;
            }
        }
        return -1;
    }

    public int size() {
        return this.size;
    }

}
