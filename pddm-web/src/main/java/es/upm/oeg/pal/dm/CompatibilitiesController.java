/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm;

//import static es.upm.oeg.pal.dm.LicenseController.logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Pablo
 */
@Controller
@Api(tags = "Compatibility", value = "Compatibility")
public class CompatibilitiesController {
    
    

    
    
    @ApiOperation(value = "Verifies the compatibility of two licenses")
    @RequestMapping(
            value = "/compatibility",
            //consumes = "application/json;charset=UTF-8",
            //produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public boolean compatibility(@RequestParam(name="license1", required = true, defaultValue = "L1") String License1, @RequestParam(name="License2", required = true, defaultValue = "L2") String License2) throws Exception {

        
     
            try {
          
              
            
          return  creativeCommonCompatibility(License1,License2);
         
            
          
            
            
        } catch (Exception e) {
          //  logger.error("Error in REST service",e);
          //  logger.error(e.getCause().toString());
           
            
        }

        return false;
    }
    
    
      
    
    
     
   // @ApiOperation(value = "Service to manage licenses")
    @ApiOperation(value = "Get the minimum requirements for the compatibility of two licenses")
    @RequestMapping(
            value = "/minimumCompatibilityLicense",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public String minimumCompatibilityLicense(@RequestParam(name="license1", required = true, defaultValue = "L1") String License1, @RequestParam(name="License2", required = true, defaultValue = "L2") String License2) throws Exception {

        
     
            try {
                
                
                
          
              
            
           
         
            
          
            
            
        } catch (Exception e) {
        //    logger.error("Error in REST service",e);
        //    logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
    
    
    
    public boolean creativeCommonCompatibility(String License1, String License2) throws UnsupportedEncodingException{
    
        // Encoded urls
        //License1 = URLDecoder.decode(License1, StandardCharsets.UTF_8.toString());
        //License2 = URLDecoder.decode(License2, StandardCharsets.UTF_8.toString());
        
        
        License1= License1.substring(0,License1.length()-4);
        System.out.println(License1);
        License2= License2.substring(0,License2.length()-4);
        System.out.println(License2);
        
        
        if(License1.equals("https://creativecommons.org/licenses/by/")){
        
            switch(License2) 
                { 
                    case "https://creativecommons.org/licenses/by/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-sa/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sa/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sd/": 
                        return false;
                        
                    default: 
                        return false; 
                } 

        
        }
        
        if(License1.equals("https://creativecommons.org/licenses/by-sa/")){
        
            switch(License2) 
                { 
                    case "https://creativecommons.org/licenses/by/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-sa/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sa/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-nc-sd/": 
                        return false;
                        
                    default: 
                        return false; 
                } 
        
        }
        
        
        if(License1.equals("https://creativecommons.org/licenses/by-nc/")){
        
            switch(License2) 
                { 
                    case "https://creativecommons.org/licenses/by/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-sa/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-nc/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sa/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sd/": 
                        return false;
                        
                    default: 
                        return false; 
                } 
        
        }
        
        
        if(License1.equals("https://creativecommons.org/licenses/by-nc-sa/")){
        
            switch(License2) 
                { 
                    case "https://creativecommons.org/licenses/by/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-sa/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-nc/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sa/": 
                        return true;
                    case "https://creativecommons.org/licenses/by-nc-sd/": 
                        return false;
                        
                    default: 
                        return false; 
                } 
            
        
        }
        
        
        if(License1.equals("https://creativecommons.org/licenses/by-nc-nd/")){
        
            switch(License2) 
                { 
                    case "https://creativecommons.org/licenses/by/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-sa/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-nc/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-nc-sa/": 
                        return false;
                    case "https://creativecommons.org/licenses/by-nc-sd/": 
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
