import java.util.ArrayList;
public class Set<T> {
    //variables
    private ArrayList<T> set;
    
    //constructor
    public Set(){
        this.set = new ArrayList<T>(0);
    }
    
    //constructor
    public Set(Set<T> older){
        ArrayList<T> temp = older.getList();
        this.set = new ArrayList<T>(temp);
    }
    
    //add
    public void add(T item){
        this.set.add(item);
    }
    
    //get
    public T get(int index){
        T item = this.set.get(index);
        return item;
    }
    
    //set
    public void set(int index, T item){
        this.set.set(index,item);
    }
    
    //getList
    public ArrayList<T> getList(){
        return this.set;
    }
    
    //remove
    public void remove(T item){
        this.set.remove(item);
    }
    
    //union
    public void union(Set Newset){
        ArrayList<T> temp = Newset.getList();
        for(T i: temp){
            if(!this.set.contains(i)){
                add(i);
            }
        }
    }
    
    //contains
    public boolean contains(Set<T> items){
        boolean[] checker = new boolean[items.size()];
        for(int i = 0; i < items.size(); i++){
            T item = items.get(i);
            for(T j: this.set){
                if(j == item){
                    checker[i] = true;
                }
            }
        }
        for(boolean i: checker){
            if(!i){
                return false;
            }
        }
        return true;
    }
    
    //size
    public int size(){
        return this.set.size();
    }
    
    //toString
    public String toString(){
        String end = "";
        end = end+"[ ";
        for(T i: this.set){
            end = end+i+",";
        }
        end = end+" ]";
        return end;
    }
    
    //clone
    public Set<T> clone(){
        Set<T> clone = new Set<T>();
        for(T i: this.set){
            clone.add(i);
        }
        return clone;
    }
}
