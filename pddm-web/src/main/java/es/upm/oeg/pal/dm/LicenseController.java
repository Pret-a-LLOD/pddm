
package es.upm.oeg.pal.dm;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.upm.oeg.pal.dm.store.FusekiConn;
import es.upm.oeg.pal.dm.store.LicenseIO;
import io.swagger.annotations.Api;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
   


        

@Controller
@Api(tags = "License", value = "License")
public class LicenseController {

    
    
    
  
private static final Logger LOGGER = LoggerFactory.getLogger(LicenseController.class);
    

    
    
    
    @RequestMapping(
            value = "/license/",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLicenses()  {

      return ResponseEntity.ok( FusekiConn.getAllGraphs());
  
    }
    
    
    
    
   
    
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            //consumes={"text/turtle", "application/rdf+xml","application/ld+json"},
            produces= "text/turtle;charset=utf-8", //application/json
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLicense(@PathVariable String id) throws JsonProcessingException  {

        System.out.println(id);
        
        try {
            id = java.net.URLDecoder.decode(id, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // not going to happen - value came from JDK's own StandardCharsets
        }
        System.out.println("transformed: "+id);
              

        String data = LicenseIO.getLicenseFromId(id);
       

        
        return new ResponseEntity<>(data,HttpStatus.OK);
       
          
    }
    
    
    
    
     @RequestMapping(
            value = "/license",
            consumes = {"text/turtle;charset=UTF-8", "application/rdf+xml;charset=UTF-8","application/ld+json;charset=UTF-8"},
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postLicense(@RequestBody String License) throws Exception {

        
            
            
            System.out.println("POST Service");
            LOGGER.info("Post Service");
            try{
            //List<Policy> policies = ODRLRDF.load(License); 
            FusekiConn.uploadGraph(License);
            
           
            }catch(Exception e){
                System.out.println("Policy Error");
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        

            return new ResponseEntity(HttpStatus.OK);
     
    }
    
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity  deleteLicense(@PathVariable String id)  {

         System.out.println(id);
         FusekiConn.deleteGraph(id);
         return new ResponseEntity(HttpStatus.OK);
           
            
    
    }
    
    
    @RequestMapping(
            value = "/loadDefaultLicenses",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity  loadDefaults()  {

         LicenseUtil.populateAll(".."+File.separator+ "data"+File.separator+"licenses");
         return new ResponseEntity(HttpStatus.OK);
           
            
    
    }
    
   
  
    
    @PostConstruct
    public void initIt() {
	//  logger.info("Init " );
          try{
              
              
           
          }catch(Exception e){
         // logger.error("Unable to start service at deploy time. "+e);
          }
    }
    
    
    

  
}
