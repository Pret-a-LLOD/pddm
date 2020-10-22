/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

/**
 *
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
                
      
        dataset.addNamedModel("http://licenses/"+rdf.getName(), m) ;
        
        //conn.loadDataset(dataset);
        connection.commit();
        
        
        
	}
    
     
     
     public static void deleteGraph(String Graph)throws IOException {

     
       
       /*final DatasetGraph dsg = DatasetGraphFactory.wrap(Graph.emptyGraph);
       final DatasetGraph dsgWrapped = new DatasetGraphWrapperStar(dsg);
       final Dataset ds = DatasetFactory.wrap(dsgWrapped);*/
     
       
       createConnection();
       
       Dataset dataset = connection.fetchDataset(); //DatasetFactory.create() ;
       
       Iterator it=dataset.asDatasetGraph().listGraphNodes();
       while(it.hasNext()){
           
           
           Node n= (Node)it.next();
           if(!n.toString().equals(Graph)){continue;}
           System.out.println("entro");
           System.out.println(n.toString());
           
           connection.delete(n.toString());
           //dataset.asDatasetGraph().removeGraph(n); // no ha funcionado
           //dataset.removeNamedModel(n.toString());
           
       }
       
        connection.commit();
    
      
      
       
  
	}
     
     
    public static void uploadRDF(File rdf, String serviceURI)
			throws IOException {

        
        
		// parse the file
		Model m = ModelFactory.createDefaultModel();
		try (FileInputStream in = new FileInputStream(rdf)) {
			m.read(in, null, "TTL");
		}

		// upload the resulting model
		DatasetAccessor accessor = DatasetAccessorFactory
				.createHTTP(serviceURI);
		accessor.putModel(m);
	}

	public static void execSelectAndPrint(String serviceURI, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
				query);
		ResultSet results = (ResultSet) q.execSelect();

		ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results);

                /*
		while (results..hasNext()) {
			QuerySolution soln = results.nextSolution();
			RDFNode x = soln.get("x");
			System.out.println(x);
		}
                */
	}

	public static void execSelectAndProcess(String serviceURI, String query) {
	/*	
            QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI,
				query);
		ResultSet results = q.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			// assumes that you have an "?x" in your query
			RDFNode x = soln.get("x");
			System.out.println(x);
		}
            */
	}

	
    
    
    
}
