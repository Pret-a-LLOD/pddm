/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm;

import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Pablo
 */
public class LicenseService {
    
    
    
    
    public List <String> licenses;
    
    
    public LicenseService(){
     this.licenses=new ArrayList();
    }
    
    public void addLicense(String license) throws UnsupportedEncodingException{
        
       license= URLEncoder.encode(license, StandardCharsets.UTF_8.toString());
        this.licenses.add(license);
    }
    
    public String getLicense(String id){
        
        for(String l: licenses){
            if(id.equals(l)){return id;}
        }
        return null;
    }
    
    public List<String> getAllLicenses(){
        
        return licenses;
    }
    
    public boolean deleteLicense(String id){
        
        if(getLicense(id).equals(null)){return false;}
        
        licenses.remove(id);
        
        return true;
    }
}
