package es.upm.oeg.pal.dm;

import es.upm.oeg.pal.dm.store.FusekiConn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.jena.rdf.model.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Pablo
 */
@Controller
@Api(tags = "Compatibility", value = "Compatibility")
public class CompatibilitiesController {
    

   
    
    
   
 
    
    @CrossOrigin
    @ApiOperation(value = "Verifies the compatibility of two licenses")
    @RequestMapping(
            value = "/compatibilityPair",
            //consumes = "application/json;charset=UTF-8",
            //produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity compatibility(@RequestParam(name="License1", required = true, defaultValue = "L1") String License1, @RequestParam(name="License2", required = true, defaultValue = "L2") String License2) throws Exception {

        
        Model LicenseModel1;
        Model LicenseModel2;
        
        try {
             LicenseModel1= getLicense(License1);
        } catch (Exception e) {
            return new ResponseEntity<>("License 1 not found",HttpStatus.NOT_FOUND);

        }
        
        
         try {
              LicenseModel2= getLicense(License2);
        } catch (Exception e) {
            return new ResponseEntity<>("License 2 not found",HttpStatus.NOT_FOUND);
        }
         
      
            
              
            
          boolean result= false;
          
          // creative commons 
          if ( (License1.contains("cc-by"))&& (License2.contains("cc-by"))  ){
            result= creativeCommonCompatibility(License1,License2);
          }
          
          
         
          if(result) {
              return new ResponseEntity<>("Compatible Licenses",HttpStatus.OK);
          }
          return new ResponseEntity<>("Not Compatible Licenses",HttpStatus.NOT_FOUND);
            
          
            
            
        
        
        

      
    }
    
    
    public Model getLicense(String License) throws Exception{
    
        System.out.println(License);
      
        try {
            License = java.net.URLDecoder.decode(License, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }
        System.out.println("License to search: "+License);
              
        if(!FusekiConn.checkIfGraphExists(License)){
            System.out.println("Not Found");
            throw new Exception();
        }
        
       
        Model model = FusekiConn.getGraph(License);

        return model;
    }
    
    
      
    
    
     
   // @ApiOperation(value = "Service to manage licenses")
    @ApiOperation(value = "Get the minimum requirements for the compatibility of two licenses")
    @RequestMapping(
            value = "/minimumCompatibilityPairLicenses",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity minimumCompatibilityLicense(@RequestParam(name="license1", required = true, defaultValue = "L1") String License1, @RequestParam(name="License2", required = true, defaultValue = "L2") String License2) throws Exception {

        
        Model LicenseModel1;
        Model LicenseModel2;
        
        try {
             LicenseModel1= getLicense(License1);
        } catch (Exception e) {
            return new ResponseEntity<>("License 1 not found",HttpStatus.NOT_FOUND);

        }
        
        
         try {
              LicenseModel2= getLicense(License2);
        } catch (Exception e) {
            return new ResponseEntity<>("License 2 not found",HttpStatus.NOT_FOUND);
        }
         
 
           // TODO 
        
          return new ResponseEntity<>("Not Compatible Licenses",HttpStatus.NOT_FOUND);
            
    }
    
    
    
    public String cleanCCLicense(String CCLicense){
    
        
        /*
        cc-by-sa3.0ro
        cc-by-sa3.0ve
        cc-by-sa4.0
        cc-by1.0
                to cc-by
        */
    
        String [] lis= CCLicense.split("\\d");
        
        return lis[0];
    
    }
    
    
    public boolean creativeCommonCompatibility(String License1, String License2) throws UnsupportedEncodingException{
    
        License1= cleanCCLicense(License1);
        License2= cleanCCLicense(License2);
        
               if(License1.equals("cc-by")){
        
            switch(License2) 
                { 
                    case "cc-by": 
                        return true;
                    case "cc-by-sa": 
                        return true;
                    case "cc-by-nc": 
                        return true;
                    case "cc-by-nc-sa": 
                        return true;
                    case "cc-by-nc-sd": 
                        return false;
                        
                    default: 
                        return false; 
                } 

        
        }
        
        if(License1.equals("cc-by-sa")){
        
            switch(License2) 
                { 
                    case "cc-by": 
                        return true;
                    case "cc-by-sa": 
                        return true;
                    case "cc-by-nc": 
                        return true;
                    case "cc-by-nc-sa": 
                        return false;
                    case "cc-by-nc-sd": 
                        return false;
                        
                    default: 
                        return false; 
                } 
        
        }
        
        
        if(License1.equals("cc-by-nc")){
        
            switch(License2) 
                { 
                    case "cc-by": 
                        return true;
                    case "cc-by-sa": 
                        return false;
                    case "cc-by-nc": 
                        return true;
                    case "cc-by-nc-sa": 
                        return true;
                    case "cc-by-nc-sd": 
                        return false;
                        
                    default: 
                        return false; 
                } 
        
        }
        
        
        if(License1.equals("cc-by-nc-sa")){
        
            switch(License2) 
                { 
                    case "cc-by": 
                        return true;
                    case "cc-by-sa": 
                        return false;
                    case "cc-by-nc": 
                        return true;
                    case "cc-by-nc-sa": 
                        return true;
                    case "cc-by-nc-sd": 
                        return false;
                        
                    default: 
                        return false; 
                } 
            
        
        }
        
        
        if(License1.equals("cc-by-nc-nd")){
        
            switch(License2) 
                { 
                    case "cc-by": 
                        return false;
                    case "cc-by-sa": 
                        return false;
                    case "cc-by-nc": 
                        return false;
                    case "cc-by-nc-sa": 
                        return false;
                    case "cc-by-nc-sd": 
                        return false;
                        
                    default: 
                        return false; 
                } 
        
        }
        
        
        
        
        /*
        https://creativecommons.org/licenses/by/4.0/
        
        https://creativecommons.org/licenses/by-sa/4.0/
    
        https://creativecommons.org/licenses/by-nc/4.0/
        
        https://creativecommons.org/licenses/by-nc-sa/4.0/
        
        https://creativecommons.org/licenses/by-nc-nd/4.0/
        
        */
        
        return false;
    }
    
}
