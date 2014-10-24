/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package keywords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.max;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ANKI
 */
public class uniqueword {
    Map<String,Integer> antecedent = new HashMap();
    Map<String,Integer> consequent = new HashMap();
    double[][] matrix = new double[46][32];
    
    private void uniqueinmap() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\rules1");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        int u = 0;
        int an = 0,co = 0;
        while ((word = br.readLine()) != null) {
            if ( u == 0 ){
                String[] result = word.split("\\s");
                for ( int i = 0; i < result.length; i++){
                    if ( !antecedent.containsKey(result[i])){
                        antecedent.put(result[i], an++);
                        
                        //System.out.println ( result[i] + " " + an);
                    }
                }
                u = 1;
            }else if ( u == 1 ){
                String[] result = word.split("\\s");
                for ( int i = 0; i < result.length; i++){
                    if ( !consequent.containsKey(result[i])){
                        consequent.put(result[i], co++);
                        //System.out.println ( result[i] + " " + co);
                    }
                }
                u = 2;
            }else if ( u == 2){
                u = 0;
            }
        }
    }
    private void printmap()
    {
        Iterator iterator = antecedent.keySet().iterator(); 
        while (iterator.hasNext()) {  
            String key = iterator.next().toString();  
            String value = antecedent.get(key).toString();   
            System.out.println(key + " " + value);  
        } 
        System.out.println ( "consuent");
        for ( int i = 0; i < 1000000000; i++ ){}
        iterator = consequent.keySet().iterator();
        while (iterator.hasNext()) {  
            String key = iterator.next().toString();  
            String value = consequent.get(key).toString();   
            System.out.println(key + " " + value);  
        }
    }
    private void generatematrix() throws FileNotFoundException, IOException
    {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\rules1");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        String[] result_ante = null;
        String[] result_cons = null;
        int u = 0;
        while ((word = br.readLine()) != null) {
            if ( u == 0 ){
                result_ante = word.split("\\s");
                u = 1;
            }else if ( u == 1 ){
                result_cons = word.split("\\s");
                u = 2;
            }else if ( u == 2){
                for ( int i = 0; i < result_ante.length; i++){
                    for ( int j = 0; j < result_cons.length; j++){
                        matrix[antecedent.get(result_ante[i])][consequent.get(result_cons[j])] = max (matrix[antecedent.get(result_ante[i])][consequent.get(result_cons[j])], Double.parseDouble(word));
                    }
                }
                u = 0;
            }
        }
    }
    
    private void printmatrix() throws IOException
    {
        for ( int i = 0; i < 46; i++){
            for ( int j = 0; j < 32; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        uniqueword u = new uniqueword();
        u.uniqueinmap();
        u.printmap();
        u.generatematrix();
        u.printmatrix();
    }
}
