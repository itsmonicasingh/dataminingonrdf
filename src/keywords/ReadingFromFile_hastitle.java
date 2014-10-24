/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keywords;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author nidhi
 */
public class ReadingFromFile_hastitle {
       public static void main(String args[]) throws FileNotFoundException, IOException{
       int flag;System.setProperty("socksProxyPort", "8080");
System.setProperty("http.proxyHost", "172.31.1.6");
System.setProperty("http.proxyPort", "8080");
final String authUser = "rs96";
final String authPassword = "mintjai";
Authenticator.setDefault(
new Authenticator() {
public PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(authUser, authPassword.toCharArray());
}});
     FileInputStream fstream = new FileInputStream("C:\\Users\\nidhi\\Documents\\NetBeansProjects\\readingRDF\\src\\readingrdf\\IEEE_paper");
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String FromFile;
		//Read File Line By Line
                
		while ((FromFile = br.readLine()) != null) 	{			// Print the content on the console
	flag=0;		
                    System.out.println (FromFile);
        String sparqlQueryString1=                 
        "PREFIX akt: <http://www.aktors.org/ontology/portal#> \nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n PREFIX iai:  <http://www.iai.uni-sb.de/resist#> \nSELECT ?relatedPaper_title WHERE { ?s akt:has-title \""+FromFile+"\"; akt:paper-in-proceedings  ?paperProceeding.?related_papers akt:paper-in-proceedings ?paperProceeding; akt:has-title ?relatedPaper_title}";
         QueryExecution qexec = QueryExecutionFactory.sparqlService("http://ieee.rkbexplorer.com/sparql/", sparqlQueryString1);
              try {       ResultSet results = qexec.execSelect();
                     for ( ; results.hasNext() ; )
                        {   QuerySolution soln = results.nextSolution() ;
                             String x = soln.get("?relatedPaper_title").toString();
                             flag++;
                             System.out.println(x);
                        }
               System.out.println("  "+flag+ "\n--------------------------over------------------------\n");
              }
               finally { qexec.close() ; 
  

 }}} 
}
