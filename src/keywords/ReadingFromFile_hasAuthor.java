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
public class ReadingFromFile_hasAuthor{
   public static void main(String args[]) throws FileNotFoundException, IOException, RepositoryException, QueryEvaluationException, MalformedQueryException{
           // Get the object of DataInputStream
          //DataInputStream in = new DataInputStream(fstream);
          // BufferedReader br = new BufferedReader(new InputStreamReader(in));
          File file = new File("C:\\Users\\ANKI\\Documents\\NetBeansProjects\\keywords\\src\\keywords\\IEEE_paper1");
          FileWriter fw = new FileWriter(file.getAbsoluteFile());
          BufferedWriter bw = new BufferedWriter(fw);
          String sesameServer = "http://localhost:8080/openrdf-sesame/";
          String repositoryID = "ieee";
          Repository repo = new HTTPRepository(sesameServer, repositoryID);
          repo.initialize();
          RepositoryConnection con = repo.getConnection();
          System.out.println(repo.getConnection());
		
        String sparqlQueryString1=                 
        "PREFIX akt: <http://www.aktors.org/ontology/portal#> \nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \nSELECT distinct ?title WHERE {?s akt:has-title ?title}";
        TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, sparqlQueryString1);
	  TupleQueryResult result = tupleQuery.evaluate();
          List<String> bindingNames = result.getBindingNames();
               while (result.hasNext()) {
                    BindingSet bindingSet = result.next();
                    Value firstValue = bindingSet.getValue(bindingNames.get(0));
                    System.out.println(firstValue);
                    
                    bw.write(firstValue+"\n");
                } 
   bw.close();} 
}
