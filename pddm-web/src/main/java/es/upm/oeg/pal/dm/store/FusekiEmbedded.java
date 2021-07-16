package es.upm.oeg.pal.dm.store;

import java.io.File;
import java.io.StringWriter;
import org.apache.commons.io.FileUtils;
import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;


/**
 * Loads the collection of licenses in the repo
 * @author vroddon
 */
public class FusekiEmbedded {

    public static void main(String args[]) {
//        startServer();
        startup();
    }
    
    public static void startup()
    {
        clonegit();
    }
    
    public static void clonegit()
    {
        String repo = "https://github.com/Pret-a-LLOD/pddm/";
        String folder = "licenses";
        try{
        FileUtils.deleteDirectory(new File(folder));
            Git.cloneRepository().setURI(repo).setDirectory(new File(folder)).call();        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        }
    
    public static void startServer()
    {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");        
        Logger.getLogger("org.eclipse").setLevel(Level.OFF);
        Logger.getRootLogger().setLevel(Level.OFF);

        try {
            Dataset dataset = RDFDataMgr.loadDataset("http://rdflicense.appspot.com/rdflicense/ms-c-nored-ff.ttl");
            Model model = dataset.getDefaultModel();
            String syntax = "TURTLE"; // also try "N-TRIPLE" and "TURTLE"
            StringWriter out = new StringWriter();
            model.write(out, syntax);
            String result = out.toString();
            System.out.println(result);
            
            FusekiServer server = FusekiServer.create().add("/ds", dataset).build() ;
            server.start() ;
            
        try (QueryExecution qExec = QueryExecutionFactory.create("SELECT * {?s ?p ?o}", dataset) ) {
            ResultSet rs = qExec.execSelect() ;
            int tot = rs.getRowNumber();
            System.out.println("triples "+tot   );
            ResultSetFormatter.out(rs) ;
          }catch(Exception e)
          {
              e.printStackTrace();
          }
            
            server.stop() ;            
            
        } catch (Exception e2) {

        }
    }
}
