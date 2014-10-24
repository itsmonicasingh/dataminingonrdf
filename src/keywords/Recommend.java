package keywords;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Recommend {

    Vector<String> stopword = new Vector<String>();    Vector<String> tagword = new Vector<String>();
    Map<String, Integer> antecedent = new HashMap();
    Map<Integer, String> consequent = new HashMap();
    Map<String, Integer> consequent_reverse = new HashMap();
    Map<Integer, String> antecedent_reverse = new HashMap();
    double[][] matrix = new double[47][33];
    Set<String> antecedent_unique = new HashSet<String>();
    Map<String, Vector<String>> consequent_to_paper = new HashMap();
    Vector<String> paper = new Vector<String>();

    void addStopWordToSet() throws FileNotFoundException, IOException {
     
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\stopword");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        
        while ((word = br.readLine()) != null) {
        
            stopword.add(word);
            // System.out.println(word);
        }

    }

    void ante_unique() throws FileNotFoundException, IOException {
        
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\test");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        
        while ((word = br.readLine()) != null) {
        
            word = word.toLowerCase();
            String[] result = word.split("_");
            
            for (int i = 0; i <= result.length - 1; i++) {
                
                if (!stopword.contains(result[i])) {
                
                    if (!antecedent_unique.contains(result[i])) {
            
                        antecedent_unique.add(result[i]);
                    
                    }
                }
            }
        }
    }

    void read_paper() throws FileNotFoundException, IOException {
       
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\IEEE_paper");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        
        while ((word = br.readLine()) != null) {
        
            paper.add(word);
        
        }
        
        Iterator itr1 = antecedent_unique.iterator();
        
        while (itr1.hasNext()) {
        
            String antecedent_name = itr1.next().toString();
            Iterator itr = paper.iterator();
            while (itr.hasNext()) {
            
                String word1 = itr.next().toString();
                word1 = word1.toLowerCase();
                String result[] = word1.split("\\s");
                Vector<String> tagword1 = new Vector();
                for (int i = 0; i < result.length; i++) {
                    if (result[i].equals(antecedent_name)) {
                        if (!tagword1.contains(result[i])) {
                
                            tagword1.addElement(word1);
                        
                        }
                    }
                }
                if (!tagword1.isEmpty()) {
                   
                    consequent_to_paper.put(antecedent_name, tagword1);
                
                }
            }

        }
    }

    void filterTitleToTagWord(String title) {
        String[] result = title.split("\\s");
        //  System.out.println(result.length);
        for (int i = 0; i < result.length; i++) {
            if (!stopword.contains(result[i])) {
                tagword.add(result[i]);
                //   System.out.println(result[i]);
            }
        }
        // System.out.println(tagword.size());
    }

    void loadAnticedentInMap() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\antecedent1");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        while ((word = br.readLine()) != null) {
            String[] result = word.split("\\s");
            antecedent.put(result[0], Integer.parseInt(result[1]));
            antecedent_reverse.put(Integer.parseInt(result[1]), result[0]);
        }
    }

    void loadConsequentInMap() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\consequent1");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        while ((word = br.readLine()) != null) {
            String[] result = word.split("\\s");
            consequent.put(Integer.parseInt(result[1]), result[0]);
            consequent_reverse.put(result[0], Integer.parseInt(result[1]));
        }
    }

    void loadMatixInArray() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\matrix");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        int i = 0;
        while ((word = br.readLine()) != null) {
            String[] result = word.split("\\s");
            for (int l = 0; l < result.length; l++) {
                matrix[i][l] = Double.parseDouble(result[l]);
            }
            i++;
        }
    }

    String printRecommendedKeyword(int threshold) {
        int i;
        String result_new = "";
        double thres = (double) threshold / (double) 100;
        boolean flag = false;
        Iterator it = tagword.iterator();
        while (it.hasNext()) {
            String result = it.next().toString();
            if (antecedent.containsKey(result)) {
                i = antecedent.get(result);
                for (int j = 0; j < 32; j++) {
                    if (matrix[i][j] >= thres) {
                        if (flag == false) {
                            System.out.println("recommended keywords");
                            flag = true;
                        }
                        String keyword = consequent.get(j);
                        String output = "";
                        for (int k = 0; k < keyword.length(); k++) {
                            if (keyword.charAt(k) == '_') {
                                output += ' ';
                            } else {
                                output += keyword.charAt(k);
                            }
                        }
                        System.out.println(output);
                        result_new += output + "\n";
                    }
                }
            }
        }
        if (flag == false) {
            System.out.println("no keywords found");
            result_new = "no keywords found";

        }
        return result_new;
    }

    String printRecommendedKeyword_by_consequent(int threshold, String title) {
        double thres = (double) threshold / (double) 100;
        String result_new1 = "";
        int i;
        boolean flag = false;
        String title1 = "";
        for (int k = 0; k < title.length() - 1; k++) {
            if (title.charAt(k) == ' ') {
                title1 += '_';
            } else {
                title1 += title.charAt(k);
            }
        }
        System.out.println(title1);
        System.out.println(consequent_reverse.size());
        i = consequent_reverse.get(title1);
        for (int j = 0; j < 46; j++) {
            if (matrix[j][i] >= thres) {
                flag = true;
                String antecedent = antecedent_reverse.get(j);
                String result[] = antecedent.split("_");
                for (int k = 0; k < result.length; k++) {
                    if (consequent_to_paper.containsKey(result[k])) {
                        Vector<String> v = consequent_to_paper.get(result[k]);
                        for (int l = 0; l < consequent_to_paper.get(result[k]).size(); l++) {
                            System.out.println("valus: " + consequent_to_paper.get(result[k]).elementAt(l));
                            result_new1 += consequent_to_paper.get(result[k]).elementAt(l) + "\n";
                        }
                    }
                }
            }
        }
         if ( flag == false ){
            result_new1 = "no value";
         }
         return result_new1;
    }

    void clearVector() {
        tagword.clear();
    }

    /* public static void main(String[] args) throws FileNotFoundException, IOException {

     String title;
     int threshold;
     Recommend recommend = new Recommend();
     recommend.addStopWordToSet();
     recommend.loadAnticedentInMap();
     recommend.loadConsequentInMap();
     recommend.loadMatixInArray();
     recommend.ante_unique();
     recommend.read_paper();
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     String word;
     for (int i = 0;; i++) {
     System.out.println("enter book, article or paper name");
     title = br.readLine();
     title = title.toLowerCase();
     recommend.filterTitleToTagWord(title);
     System.out.println("enter threshold");
     threshold = Integer.parseInt(br.readLine());
     recommend.printRecommendedKeyword(threshold);
     System.out.println("now enter consequent");
     recommend.clearVector();
     System.out.println("enter book, article or paper name");
     title = br.readLine();
     title = title.toLowerCase();
     System.out.println("enter threshold");
     threshold = Integer.parseInt(br.readLine());
     recommend.printRecommendedKeyword_by_consequent(threshold, title);
     }
     }*/
}
