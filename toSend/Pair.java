public class Pair<K,V> {
    //variables
    private K key;
    private V value;
    
    //constructor
    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }
    
    //getKey
    public K getKey(){
        return this.key;
    }
    
    //getValue
    public V getValue(){
        return this.value;
    }
    
    //setKey
    public void setKey(K key){
        this.key = key;
    }
    
    //setValue
    public void setValue(V value){
        this.value = value;
    }
    
}
