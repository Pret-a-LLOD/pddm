package es.upm.oeg.pal.dm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

/**
 * Makes easier the access to SPARQL. This class is NOT indended for big data.
 * Actually, will only work ok with small data ~10s Mb of size.
 * This should be enough for our purposes.
 * @author vroddon
 */
public class ManagerSPARQL {

    public static String ENDPOINT = "http://localhost:3330/rdflicense/query";
    public static String ENDPOINTUPDATE = "http://localhost:3330/rdflicense/update";
    public static String FILESTORE = "data.nq";

    private static Dataset ds = null;
    private static FusekiServer server = null;

    //Initialization is done at the beginning, only once
    static {
        System.out.println("Starting SPARQL server");
        init();
        System.out.println("Server is running");

    }

    public static void main(String[] args) {

        //watch in the root folder of the project. There will appear a data.nq file
        countEntities();
        insertTriplesInGraph("http://licencia1", "<http://licencia1> <http://prop> <http://obj> . <http://obj> <http://prop3> \"asdf\" .");
        insertTriplesInGraph("http://licencia2", "<http://licencia2> <http://prop> <http://obj> .");
        countEntities();
        List<String> namedGraphs = getNamedGraphs();
        for(String ng: namedGraphs)
            System.out.println(ng);
    }

    /**
     * This method has to be called once, just fo
     */
    public static void init() {
        try {
            File file = new File(FILESTORE);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ds = RDFDataMgr.loadDataset(FILESTORE);
                    server = FusekiServer.create().add("/rdflicense", ds).build();
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        

    }

    /**
     * This method is as slow as storing the whole database again. This is fine
     * if write operations are not often, and the file is not large (<100Mb)
     * @
     *
     * param graph Which graph? One graph per file
     * @param nt Triples in nt format
     */
    public static boolean insertTriplesInGraph(String graph, String nt) {
        
        if(nt.equals("")){
            nt="<http://licencia2> <http://prop> <http://obj> .";
    }
        try {
            OutputStream fos = new FileOutputStream(FILESTORE);
            String query = "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n";
            query += "INSERT DATA { GRAPH <" + graph + "> {";
            query += nt;
            query += "}}";
            UpdateRequest update = UpdateFactory.create(query);
            UpdateProcessor qexec = UpdateExecutionFactory.createRemote(update, ENDPOINTUPDATE);
            qexec.execute();
            RDFDataMgr.write(fos, ds, Lang.NQUADS);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * Counts the triples in all the graphs.
     */
    public static List<String> getNamedGraphs() {
        List<String> list = new ArrayList();
        try {
            String sparql = "SELECT DISTINCT ?g  WHERE {  GRAPH ?g { ?s ?p ?o }}";
            Query query = QueryFactory.create(sparql);
            QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                QuerySolution qs = results.next();
                String s = qs.get("?g").asResource().toString();
                try {
                    list.add(s);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            //    System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Counts the triples in all the graphs.
     */
    public static int countEntities() {
        int total = 0;
        try {
            String sparql = "SELECT (COUNT(?s) AS ?total) WHERE {  graph ?g { ?s ?p ?o } }";
            Query query = QueryFactory.create(sparql);
            QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                QuerySolution qs = results.next();
                String s = qs.get("?total").asLiteral().getLexicalForm();
                try {
                    total = Integer.parseInt(s);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    static String getNamedGraphs(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static boolean deleteGraph(String id) {
       try {
            OutputStream fos = new FileOutputStream(FILESTORE);
            String query = "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n";
            query += "CLEAR  GRAPH <" + id + "> ";
            UpdateRequest update = UpdateFactory.create(query);
            UpdateProcessor qexec = UpdateExecutionFactory.createRemote(update, ENDPOINTUPDATE);
            qexec.execute();
            RDFDataMgr.write(fos, ds, Lang.NQUADS);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

}
