package keywords;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.nativerdf.NativeStore;
public class readingfromfile_areaofintrest {
    Set<String> stopword = new HashSet<String>();
    void addStopWordToSet () throws FileNotFoundException, IOException
    {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\stopword");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        while ((word = br.readLine()) != null) {
            stopword.add(word);
        }   
        System.out.println ( "complete");
    }
    private void filterStopWord() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\acm_areaofintrest");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String word;
        File file = new File("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\acm_areaofintres");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        while ((word = br.readLine()) != null) {
            word = word.toLowerCase();
            String[] result = word.split("\\s");
            for ( int i = 0; i < result.length; i++){
               if ( !stopword.contains(result[i])) {
                    bw.write(result[i] + " ");
                    System.out.println ( result[i]);
                }    
            }
            bw.write("\n");
        }
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException, RepositoryException, MalformedQueryException, QueryEvaluationException{
        Map<String,String> classification = new HashMap();
        FileInputStream fstream1 = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\classification");
        DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
        String word,key1 = "",value="";
        
        while ((word = br1.readLine()) != null) {
              int flag = 0,flag1 = 0;
             for ( int i = 0; i < word.length(); i++){
                 if ( word.charAt(i) == ':'){
                     flag = 1;
                     flag1 = 1;
                     i = i + 1;
                     continue;
                 }
                 if ( flag == 0){
                     key1 = key1 + word.charAt(i);
                 }
                 if ( flag1 == 1 ){
                     value += word.charAt(i);
                 }
             }
            System.out.println(key1 +  value);
             classification.put(key1,value);
             key1 = "";
             value = "";
        }
        File file = new File("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\acm_areaofintrest");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\acmtitle");	
	DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        File dataDir= new File("C:\\Users\\ANKI\\Desktop\\New folder\\acmbinary");
        Repository embededR = new SailRepository(new NativeStore(dataDir));
        embededR.initialize();  
        RepositoryConnection con = embededR.getConnection();
        String FromFile;
        int count = 0;
        int u = 1;
	while ((FromFile = br.readLine()) != null) 	{			
                    System.out.println(FromFile);
                    String sparqlQueryString1=                 
                    "PREFIX akt: <http://www.aktors.org/ontology/portal#> \nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \nSELECT ?keyword WHERE {?s akt:has-title \""+ FromFile+"\"; akt:addresses-generic-area-of-interest ?keyword}";
             //       System.out.println(sparqlQueryString1);
                    TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQueryString1);
                    TupleQueryResult result = tupleQuery.evaluate();
                    List<String> bindingNames = result.getBindingNames();
                    u++;
                    boolean flag = false;
                    boolean flag1 = false;
                    while (result.hasNext()) {
                        BindingSet bindingSet = result.next();
                        if ( !flag1) {
                            flag1 = true;
                            bw.write(FromFile + " ");
                        }
                        Value firstValue = bindingSet.getValue(bindingNames.get(0));
                    
                        String keyword = firstValue.toString();
                        String key = "";
                        int f = 0;
                        for ( int i = 0; i < keyword.length(); i++ ){
                            if ( f == 1 ){
                                    key = key + keyword.charAt(i);
                            }
                            if ( keyword.charAt(i) == '#'){
                                f = 1;
                            }
                        }
                        
                        System.out.println ( key);
                       
                            String value1 = classification.get(key);
                            value1 = value1.toString();
                            System.out.println ( value1);
                            String value2 = "";
                            for ( int i = 0; i < value1.length(); i++){
                               if ( value1.charAt(i) == ' '){
                                    value2 = value2 + '_';
                                    continue;
                                }
                                value2 = value2 + value1.charAt(i);
                            }
                            value2 = value2.toLowerCase();
                            bw.write(value2 + " ");
                            flag = true;
                           
                            }
                  
                    if ( flag ){
                        bw.write ( "\n");
                        
                    }
                }
                bw.close();
                readingfromfile_areaofintrest r = new readingfromfile_areaofintrest();
                r.addStopWordToSet();
                r.filterStopWord();
        }
    
    }
