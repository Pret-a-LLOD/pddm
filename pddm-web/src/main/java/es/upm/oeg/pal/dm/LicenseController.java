
package es.upm.oeg.pal.dm;

import io.swagger.annotations.Api;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ExampleProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
   


        

@Controller
@Api(tags = "License", value = "License")
public class LicenseController {

  

//    static Logger logger = Logger.getLogger(Controller.class);

    LicenseService licenseService;
  
    
    
    
    @RequestMapping(
            value = "/license/",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLicenses() throws Exception {

      return ResponseEntity.ok( this.licenseService.getAllLicenses());
  
    }
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLicense(@PathVariable String id) throws Exception {

       String res= this.licenseService.getLicense(id);
       if(res!=null){
           return (ResponseEntity.ok(res));
       }
          
       return new ResponseEntity(HttpStatus.NOT_FOUND);
    
    }
    
    
     @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            //produces= "application/json;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postLicense(@RequestBody String id) throws Exception {

            this.licenseService.addLicense(id);
            return new ResponseEntity(HttpStatus.OK);
     
    }
    
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity  deleteLicense(@PathVariable String id) throws Exception {

    
         if(this.licenseService.deleteLicense(id))
             return new ResponseEntity(HttpStatus.OK);
           
            
    

        return  new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
   
    
    
    
    @PostConstruct
    public void initIt() {
	//  logger.info("Init " );
          try{
              
              licenseService = new LicenseService();
           
          }catch(Exception e){
         // logger.error("Unable to start service at deploy time. "+e);
          }
    }
    
    
    /*
    @RequestMapping(value="/reInit", method = RequestMethod.GET)
    @ResponseBody
    public void reInit() {
         try {
             
         } catch (Exception ex) {
             logger.error(ex);
         }
    }
    
    
    @RequestMapping(value ="/status", method = RequestMethod.GET)
    @ResponseBody
    public String status() {
        return "UP";
    }
    */
    

  
}
