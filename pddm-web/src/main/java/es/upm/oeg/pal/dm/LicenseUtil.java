/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    
    
    public static void main (String [] args) throws IOException{
    
        if(args.length==0){return;}
        
        if(args[0].toLowerCase().equals("delete")){
           FusekiConn.deleteAll();
        }
        
        else{
            populateAll(args[0]);
        
        }
        
        
        /*
    
        File dir = new File("C:\\Users\\Pablo\\Desktop\\Pret\\pddm\\data\\licenses");
        
        List<Policy> allPolicies =new ArrayList();
        for(File f:dir.listFiles()){
            System.out.println(f.getName());
            List<Policy> policies =ODRLRDF.load(f.getAbsolutePath());
            System.out.println(policies.size());
            allPolicies.addAll(policies);
            
        
        }
    
        System.out.println(allPolicies.get(0).toString());
       ^*/
    
    }
    
}
