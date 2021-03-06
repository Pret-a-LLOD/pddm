package es.upm.oeg.pal.dm;

import es.upm.oeg.pal.dm.store.FusekiConn;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.jodrlapi.helpers.ODRLRDF;
import oeg.jodrlapi.odrlmodel.Policy;

/**
 *
 * @author Pablo
 */
public class LicenseUtil {
    
    
    
    
    
    public static void populateAll(String Path){
    
    File dir = new File(Path);
        
        System.out.println(dir.getAbsolutePath());
        
        for(File f:dir.listFiles()){
            
            try {
                FusekiConn.uploadGraph(f);
            } catch (Exception ex) {
                System.out.println("error in "+f.getName());
                Logger.getLogger(LicenseUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        FusekiConn.closeConnection();
    
    
    }
    
    
    public static void reinitDatabase(String [] args) throws IOException{
    
    if(args.length==0){return;}
        
        if(args[0].toLowerCase().equals("delete")){
           FusekiConn.deleteAll();
        }
        
        else{
            populateAll(args[0]);
        
        }
    
    }
    
    
    
    public static void main (String [] args) throws IOException{
    
        
        String [] as= {"C:\\Users\\Pablo\\Desktop\\Pret\\pddm\\data\\licenses"};
        
        reinitDatabase(as);
        
      
    
    }
    
}
