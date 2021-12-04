package es.upm.oeg.pal.dm.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

/**
 * This class connect to an EXTERNAL fuseki server
 * @author Pablo
 */
public class FusekiConn {
    
    
    private static RDFConnection connection;
    private static String URL="http://localhost:3030/licenses";
    
    public static void createConnection(){
        
        if(connection==null){
           connection = RDFConnectionFactory.connect(URL);
        
        }
    
        
    }
    
    
    public static void closeConnection(){
        
        connection.close();
    
        
    }
    
    public static void deleteAll()
			throws IOException {

       createConnection();
       
       Dataset dataset = connection.fetchDataset(); //DatasetFactory.create() ;
       
       Iterator it=dataset.asDatasetGraph().listGraphNodes();
       while(it.hasNext()){
           Node n= (Node)it.next();
           System.out.println(n.toString());
           dataset.asDatasetGraph().removeGraph(n);
           connection.delete(n.toString());
           dataset.removeNamedModel(n.toString());
           
       }
      
      
        
        
        connection.commit();
      
       
	}
 
     public static void uploadGraph(File rdf) throws IOException {

       createConnection();
       
       Dataset dataset = connection.fetchDataset(); 
       Model m = ModelFactory.createDefaultModel();
		try (FileInputStream in = new FileInputStream(rdf)) {
			m.read(in, null, "TTL");
		}
              
       String Name= getGraphName(m);
         System.out.println("Name:"+Name);
       if(checkIfGraphExists(Name)){
           System.out.println("Already Exists");
           return;
       }
      
        dataset.addNamedModel(Name, m) ;
        connection.putDataset(dataset);
                
        //dataset.addNamedModel("http://licenses/"+rdf.getName(), m) ;
        
        //conn.loadDataset(dataset);
        connection.commit();
       
        
        
	}
     
     
     public static boolean checkIfGraphExists(String name){
     
         name= checkGraphName(name);
         List <String> graphs=getAllGraphs();
         Set set= new  HashSet<>(graphs);
         
         if(set.contains(name)){
             return true;
         }
         return false;
     }
     
     public static void uploadGraph(String rdf) throws IOException {

       createConnection();
       
       Dataset dataset = connection.fetchDataset(); 
       Model m = ModelFactory.createDefaultModel();
        
       m.read(rdf, null, "TTL");
		
       String Name= getGraphName(m);
       
       if(checkIfGraphExists(Name)){
           System.out.println("Already Exists");
           return;
       }
      
        dataset.addNamedModel(Name, m) ;
        
        //conn.loadDataset(dataset);
        connection.commit();
        
        
        
	}
     
     
     
    
     
     
    public static void deleteGraph(String GraphName)  {

        GraphName= checkGraphName(GraphName);
        /*final DatasetGraph dsg = DatasetGraphFactory.wrap(Graph.emptyGraph);
       final DatasetGraph dsgWrapped = new DatasetGraphWrapperStar(dsg);
       final Dataset ds = DatasetFactory.wrap(dsgWrapped);*/
        createConnection();

        Dataset dataset = connection.fetchDataset(); //DatasetFactory.create() ;

        Iterator it = dataset.asDatasetGraph().listGraphNodes();
        while (it.hasNext()) {

            Node n = (Node) it.next();
            if (n.toString().equals(GraphName)) {
                System.out.println("Deleting "+n.toString());

                connection.delete(n.toString());
                //dataset.asDatasetGraph().removeGraph(n); // no ha funcionado
                //dataset.removeNamedModel(n.toString());
                break;
            }
           
            

        }

        connection.commit();

    }

    /*
    public static void uploadRDF(File rdf, String serviceURI)
            throws IOException {

        // parse the file
        Model m = ModelFactory.createDefaultModel();
        try ( FileInputStream in = new FileInputStream(rdf)) {
            m.read(in, null, "TTL");
        }

        
        
        
        // upload the resulting model
        DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
        accessor.putModel(m);
    }

   */
    
    
    public static String checkGraphName(String uri){
    
        if(!uri.contains("http://purl.org/NET/rdflicense/")){
            //http://purl.org/NET/rdflicense/agpl3.0 
            uri="http://purl.org/NET/rdflicense/"+uri;
        }
        return uri;
    }

    public static Model getGraph(String uri) {

        createConnection();

        Dataset dataset = connection.fetchDataset(); //DatasetFactory.create() ;

        uri=checkGraphName(uri);
        
        
        Model model = dataset.getNamedModel(uri);
        
        return model;

    }

    public static String getGraphName(Model model) {

        String uri = "";
        Iterator it = model.getGraph().find();

        /// System.out.println("ssssssssss "+model.getGraph().find(Triple.createMatch(Node.ANY, NodeFactory.createLiteral("@rdf:type"), NodeFactory.createLiteral("http://www.w3.org/ns/odrl/2/Policy"))).next().getMatchSubject().toString());
        while (it.hasNext()) {

            Triple t = (Triple) it.next();

            String subject = (t.getMatchSubject().toString());
            if (subject.contains("http")) {
                return subject;
            }
        }

        return uri;
    }

    public static List<String> getAllGraphs() {
        FusekiConn.createConnection();

        List<String> graphs = new ArrayList();
        Dataset dataset = connection.fetchDataset(); //DatasetFactory.create() ;

        Iterator s = dataset.listNames();
        while (s.hasNext()) {

            String ss = (String) s.next();
            Model model = dataset.getNamedModel(ss);
            //System.out.println(ss);
            //System.out.println(getGraphName(model));
            graphs.add(ss);

        }
        return graphs;
    }

}
