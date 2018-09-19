import java.util.List;
import java.util.LinkedList;
import java.io.*;
public class FileMaker {
    //variables
    private BufferedWriter out;
    private File file;
    
    //constructor
    public FileMaker(String location){
        file = new File(location);
    }
    
    //make
    public void make(List<Set<Integer>> frequent, List<Pair<Set<Integer>,Set<Integer>>> rules) throws Exception{
        file.createNewFile();
        out = new BufferedWriter(new FileWriter(file));
        String Itemsets = "Visualizing Frequent Itemsets";
        String Rules = "Visualizing Rules";
        out.write(Itemsets);
        for(Set<Integer> set: frequent){
            out.write(set.toString());
            out.newLine();
            out.flush();
        }
        out.write(Rules);
        for(Pair<Set<Integer>,Set<Integer>> pair: rules){
            Set<Integer> key = pair.getKey();
            Set<Integer> value = pair.getValue();
            String temp = key.toString()+" ==> "+value.toString();
            out.write(temp);
            out.newLine();
            out.flush();
        }
        out.write("----------END----------");
        out.close();
    }
    
}
