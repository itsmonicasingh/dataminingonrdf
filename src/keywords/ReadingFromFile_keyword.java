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
import java.util.List;
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

public class ReadingFromFile_keyword {
   public static void main(String args[]) throws FileNotFoundException, IOException, RepositoryException, MalformedQueryException, QueryEvaluationException{
        File file = new File("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\IEEE_keyword1");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        FileInputStream fstream = new FileInputStream("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\IEEE_paper1");	
		DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String FromFile;
                int count = 0;
                String sesameServer = "http://localhost:8080/openrdf-sesame/";
                String repositoryID = "ieee";
                Repository repo = new HTTPRepository(sesameServer, repositoryID);
                repo.initialize();
                RepositoryConnection con = repo.getConnection();
                int u = 1;
		while ((FromFile = br.readLine()) != null) 	{			
                    System.out.println(FromFile);
                    String sparqlQueryString1=                 
                    "PREFIX akt: <http://www.aktors.org/ontology/portal#> \nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \nSELECT ?keyword WHERE {?s akt:has-title \""+ FromFile+"\"; akt:has-ieee-keyword ?keyword}";
                    System.out.println(sparqlQueryString1);
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
                        System.out.println(firstValue);
                        String keyword = firstValue.toString();
                        String key = "";
                        for ( int i = 0; i < keyword.length(); i++ ){
                                if ( keyword.charAt(i) != ' '){
                                    key = key + keyword.charAt(i);
                                }else {
                                    key = key + '_';
                                }
                        }
                        bw.write(key+" ");
                        flag = true;
                    }
                    if ( flag ){
                        bw.write ( "\n");
                    }
                }
                bw.close();
    }     
}
