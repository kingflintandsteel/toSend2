import java.util.List;
import java.util.LinkedList;
import java.io.*;
public class FileTearer2 {
    //variables
    private BufferedReader in;
    
    //constructor
    public FileTearer2(){
        //nothing
    }
    
    //tear
    public Pair<Set<Set<Integer>>, List<Set<Integer>>> tear(File file) throws Exception{
        in = new BufferedReader(new FileReader(file));
        Set<Set<Integer>> items = new Set<Set<Integer>>();
        List<Set<Integer>> database = new LinkedList<Set<Integer>>();
        
        String line;
        while((line = in.readLine()) != null){
            System.out.println(line);
            Set<Integer> set = new Set<Integer>();
            String[] Asemi = line.split(";");
            line = Asemi[0];
            String[] array = line.split(",");
            for(String i: array){
                char[] CHAR = i.toCharArray();
                int number = 0;
                for(int j = 0; j < CHAR.length; j++){
                    int temp = Character.getNumericValue(CHAR[j]);
                    temp *= Math.pow(10,CHAR.length-j-1);
                    number += temp;
                }
                set.add(number);
                boolean here = false;
                for(Set<Integer> s: items.getList()){
                    if(s.get(0) == number){
                        here = true;
                        break;
                    }
                }
                if(!here){
                    Set<Integer> TEMP = new Set<Integer>();
                    TEMP.add(number);
                    items.add(TEMP);
                }
            }
            database.add(set);
        }
        System.out.print("Database is: ");
        for(Set<Integer> i: database){
            System.out.print(i);
        }
        System.out.println("");
        
        Pair<Set<Set<Integer>>, List<Set<Integer>>> pair = new Pair<Set<Set<Integer>>,List<Set<Integer>>>(items,database);
        return pair;
    }
    
}
