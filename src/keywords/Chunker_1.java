package keywords;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.openrdf.model.Statement;
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
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.util.RDFInserter;
import org.openrdf.rio.ParserConfig;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.XMLParserSettings;
import org.openrdf.sail.nativerdf.NativeStore;

public class Chunker_1 {
    public static void main (String args[]) throws RepositoryException, FileNotFoundException, IOException, RDFParseException, RDFHandlerException, MalformedQueryException, QueryEvaluationException{
        File dataDir = new File("D:\\acm");
        String indexes = "spoc,posc,cosp";
        Repository nativeRep = new SailRepository(new NativeStore(dataDir, indexes));
        nativeRep.initialize();
        RepositoryConnection conn=nativeRep.getConnection();
        conn.setAutoCommit(false);
        String fileName="C:\\Users\\ANKI\\Desktop\\New folder\\acm-bookchapters.rdf.gz";
    
        boolean verifyData = true;
        boolean stopAtFirstError = true;
        boolean preserveBnodeIds= true;
        ParserConfig config = new ParserConfig(verifyData,stopAtFirstError, preserveBnodeIds, RDFParser.DatatypeHandling.VERIFY);
        conn.setParserConfig(config);
        RDFParser parser = Rio.createParser(RDFFormat.forFileName(fileName));
        parser.setRDFHandler(new ChunkCommitter(conn));
        parser.getParserConfig().set(XMLParserSettings.SECURE_PROCESSING, false);
        File file = new File(fileName);
        InputStream is = new GZIPInputStream(new FileInputStream(file));
        try
        {   
            parser.parse(is,"file://"+ file.getCanonicalPath());
            System.setProperty("entityExpansionLimit", "100000000");
            System.out.println("output");
            conn.commit();
        }
        finally 
        {   
            conn.close();
        }
}
private static class ChunkCommitter implements RDFHandler {
        private RDFInserter inserter;
        private RepositoryConnection conn;
        public long count = 0L;
        private long chunksize = 500000L;
        public ChunkCommitter(RepositoryConnection conn) {
            inserter = new RDFInserter(conn);
            this.conn = conn;
        
        }

        @Override
        public void startRDF() throws RDFHandlerException {
            inserter.startRDF();
        
        }

        @Override
        public void endRDF() throws RDFHandlerException {
            inserter.endRDF();
        }

        @Override
        public void handleNamespace(String prefix, String uri) throws RDFHandlerException {
         
            inserter.handleNamespace(prefix,uri);
        }

        @Override
        public void handleStatement(Statement st) throws RDFHandlerException {
            
            inserter.handleStatement(st);
            count++;
            if(count%chunksize==0){
                try{
                    conn.commit();
                }
                catch(RepositoryException e){
                    throw new RDFHandlerException(e); 
                }
            }
        }
        @Override
        public void handleComment(String comment) throws RDFHandlerException {
            inserter.handleComment(comment);
        }
    }
}