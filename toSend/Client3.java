import java.util.List;
import java.util.LinkedList;
import java.io.*;
public class Client3 {
    //variables
        //nothing
    
    //constrcutor
        //nothing
    
    //support
    public int support(Set<Integer> Itemset, List<Set<Integer>> Database){
        int support = 0;
        for(Set<Integer> i: Database){
            if(i.contains(Itemset)){
                support++;
            }
        }
        return support;
    }
    
    //findFrequentItemsets
    public List<Set<Integer>> findFrequentItemsets(Set<Set<Integer>> Items, List<Set<Integer>> Database, int MinSupport){
        List<Set<Integer>> frequent = new LinkedList<Set<Integer>>();
        Set<Set<Integer>> Cands = new Set<Set<Integer>>();
        for(Set<Integer> i: Items.getList()){
            Cands.add(i.clone());
        }
        System.out.println(Cands);
        while(Cands.size() > 0){
            List<Set<Integer>> Hypo = new LinkedList<Set<Integer>>();
            for(Set<Integer> i: Cands.getList()){
                if(support(i,Database) >= MinSupport){
                    Hypo.add(i);
                    //System.out.println(i+"added");
                }
            }
            Cands = new Set<Set<Integer>>();
            frequent.addAll(Hypo);
            System.out.println("added"+Hypo+" to frequent");
            List<Set<Integer>> temp = new LinkedList<Set<Integer>>(Hypo);
            for(Set<Integer> i: Hypo){
                temp.remove(i);
                for(Set<Integer> j: temp){
                    Set<Integer> toMerge = j.clone();
                    toMerge.union(i);
                    Cands.add(toMerge);
                }
            }
            
            //removes copy sets from frequent
            List<Set<Integer>> list = new LinkedList<Set<Integer>>();
            for(Set<Integer> i: frequent){
                for(Set<Integer> j: Cands.getList()){
                    boolean[] array = new boolean[j.size()];
                    for(int k = 0; k < j.size(); k++){
                        int item1 = j.get(k);
                        for(int l = 0; l < i.size(); l++){
                            int item2 = i.get(l);
                            if(item1 == item2){
                                array[k] = true;
                                break;
                            }
                        }
                    }
                    boolean full = true;
                    for(boolean k: array){
                        if(!k){
                            full = false;
                        }
                    }
                    if(full){
                       list.add(j); 
                    }
                }
            }
            
            for(Set<Integer> i: list){
                Cands.remove(i);
            }
            
            System.out.println("Cands is: "+Cands.size()+" long");
        }
        System.out.println("Done with frequent");
        return frequent;
    }
    
    //confidence
    public float confidence(Set<Integer> pre, Set<Integer> ant, List<Set<Integer>> Database){
        float confidence = 0;
        int supportPre = support(pre,Database);
        int supportAnt = support(ant,Database);
        confidence = supportPre/supportAnt;
        return confidence;
    }
    
    //findRules
    public List<Pair<Set<Integer>,Set<Integer>>> findRules(List<Set<Integer>> frequent, List<Set<Integer>> Database, float MinConfidence){
        List<Pair<Set<Integer>,Set<Integer>>> rules = new LinkedList<Pair<Set<Integer>,Set<Integer>>>();
        for(Set<Integer> i: frequent){
            List<Set<Integer>> Cands = new LinkedList<Set<Integer>>();
            for(Object j: i.getList()){
                Set<Integer> set = new Set<Integer>();
                set.add((Integer)j);
                Cands.add(set);
            }
            
            while(Cands.size() > 0){
                List<Pair<Set<Integer>,Set<Integer>>> Hypo = new LinkedList<Pair<Set<Integer>,Set<Integer>>>();
                for(Set<Integer> j: Cands){
                    //System.out.println("Checkpoint 4");
                    if(confidence(j,i,Database) >= MinConfidence){
                        Hypo.add(new Pair(j,i));
                    }
                }
                Cands = new LinkedList<Set<Integer>>();
                rules.addAll(Hypo);
                List<Pair<Set<Integer>,Set<Integer>>> temp = new LinkedList<Pair<Set<Integer>,Set<Integer>>>(Hypo);
                for(Pair<Set<Integer>,Set<Integer>> j: Hypo){
                    //System.out.println("Checkpoint 5");
                    temp.remove(j);
                    for(Pair<Set<Integer>,Set<Integer>> k: temp){
                        //System.out.println("Checkpoint 6");
                        Set<Integer> toMerge = k.getKey().clone();
                        toMerge.union(j.getKey());
                        Cands.add(toMerge);
                    }
                }
                System.out.println("Cands is: "+Cands.size()+" long");
            }
            System.out.println("Now removing copies");
            //removes copies
            List<Pair<Set<Integer>, Set<Integer>>> list = new LinkedList<Pair<Set<Integer>, Set<Integer>>>();
            for(Pair<Set<Integer>, Set<Integer>> pair: rules){
                Set<Integer> key = pair.getKey();
                Set<Integer> value = pair.getValue();
                boolean[] array = new boolean[value.size()];
                for(int v = 0; v < value.size(); v++){
                    //System.out.println("Checkpoint 1");
                    for(int k: key.getList()){
                        //System.out.println("Checkpoint 2");
                        if(k == value.get(v)){
                            array[v] = true;
                        }
                    }
                }
                boolean full = true;
                for(boolean b: array){
                    //System.out.println("Checkpoint 3");
                    if(!b){
                        full = false;
                    }
                }
                if(full){
                    list.add(pair);
                }
            }
            for(Pair<Set<Integer>, Set<Integer>> pair: list){
                rules.remove(pair);
            }
        }
        
        return rules;
    }
    
    //findRules2
    public List<Pair<Set<Integer>,Set<Integer>>> findRules2(List<Set<Integer>> frequent, List<Set<Integer>> Database, float MinConfidence){
        List<Pair<Set<Integer>,Set<Integer>>> rules = new LinkedList<Pair<Set<Integer>,Set<Integer>>>();
        for(Set<Integer> i: frequent){
            System.out.println("Looping through frequent");
            List<Set<Integer>> subset = subset2(i);
            System.out.println("Past subset");
            for(Set<Integer> j:subset){
                System.out.println("Looping through subset");
                if(confidence(j,i,Database) >= MinConfidence){
                    Pair<Set<Integer>,Set<Integer>> pair = new Pair(j,i);
                    rules.add(pair);
                }
            }
        }
        return rules;
    }
    
    //subset
    private List<Set<Integer>> subset(Set<Integer> items){
        List<Set<Integer>> list = new LinkedList<Set<Integer>>();
        List<Set<Integer>> Cands = new LinkedList<Set<Integer>>();
        for(int i = 0; i < items.size(); i++){
            Set<Integer> set = new Set();
            set.add((Integer)items.get(i));
            Cands.add(set);
        }
        System.out.println("Initial copy of Cands");
        while(Cands.size() > 0){
            List<Set<Integer>> Hypo = new LinkedList<Set<Integer>>(Cands);
            list.addAll(Cands);
            List<Set<Integer>> temp = new LinkedList<Set<Integer>>(Hypo);
            for(Set<Integer> i: Hypo){
                temp.remove(i);
                for(Set<Integer> j: temp){
                    Set<Integer> toMerge = j.clone();
                    toMerge.union(i);
                    Cands.add(toMerge);
                }
            }
            List<Set<Integer>> listtemp = new LinkedList<Set<Integer>>();
            for(Set<Integer> i: list){
                for(Set<Integer> j: Cands){
                    boolean[] array = new boolean[j.size()];
                    for(int k = 0; k < j.size(); k++){
                        int item1 = j.get(k);
                        for(int l = 0; l < i.size(); l++){
                            int item2 = i.get(l);
                            if(item1 == item2){
                                array[k] = true;
                                break;
                            }
                        }
                    }
                    boolean full = true;
                    for(boolean k: array){
                        if(k == false){
                            full = false;
                        }
                    }
                    if(full){
                       listtemp.add(j); 
                    }
                }
            }
            
            for(Set<Integer> i: list){
                Cands.remove(i);
            }
        }
        return list;
    }
    
    //subset2
    public List<Set<Integer>> subset2(Set<Integer> items){
        List<Set<Integer>> list = new LinkedList<Set<Integer>>();
        int length = items.size();
        for(int i = 0; i < (1<<length); i++){
            Set<Integer> set = new Set<Integer>();
            for(int j = 0; j < length; j++){
                if((i & (1 << j)) >0){
                    set.add(items.get(j));
                }
            }
            list.add(set);
        }
        return list;
    }
    
    //visualizeItemsets
    public void visualizeItemsets(List<Set<Integer>> frequent){
        System.out.println("Visualizing Itemsets");
        for(Set<Integer> i: frequent){
            System.out.println(i);
        }
    }
    
    //visualizeRules
    public void visualizeRules(List<Pair<Set<Integer>, Set<Integer>>> rules){
        System.out.println("Visualizing Rules");
        for(Pair i: rules){
            System.out.println(i.getKey()+" == > "+i.getValue());
        }
    }
    
    //main
    public static void main(String[] args) throws Exception{
        Client3 c = new Client3();
        File file = new File("C:/Users/kingf/Desktop/backeryNew.txt");
        FileTearer2 tearer = new FileTearer2();
        String path = "C:/Users/kingf/Desktop/bakery2.txt";
        FileMaker maker = new FileMaker(path);
        Pair<Set<Set<Integer>>,List<Set<Integer>>> pair = tearer.tear(file);
        List<Set<Integer>> list = c.findFrequentItemsets(pair.getKey(),pair.getValue(),150);
        c.visualizeItemsets(list);
        List<Pair<Set<Integer>,Set<Integer>>> rules = c.findRules2(list,pair.getValue(),(float)0.6);
        c.visualizeRules(rules);
        maker.make(list,rules);
    }
    
}
