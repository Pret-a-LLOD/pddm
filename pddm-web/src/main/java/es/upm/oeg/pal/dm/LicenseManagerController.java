/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
   


        

@Controller
@Api(tags = "License", value = "License")
public class LicenseManagerController {

  

    static Logger logger = Logger.getLogger(Controller.class);

  
   /*
    GET /license/   → returns complete list of ids (URLs)
GET /license/{licensid}
POST /license/{licensid}
DELTE /license/{licensid}
*/
    
    
    @RequestMapping(
            value = "/license/",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public String getLicenses() throws Exception {

        try {
          
     } catch (Exception e) {
            logger.error("Error in REST service",e);
            logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public String getLicense(@PathVariable String id) throws Exception {

        try {
          
     } catch (Exception e) {
            logger.error("Error in REST service",e);
            logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
    
     @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String postLicense(@RequestBody String product) throws Exception {

        try {
          
     } catch (Exception e) {
            logger.error("Error in REST service",e);
            logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    @ResponseBody
    public String deletetLicense(@PathVariable String id) throws Exception {

        try {
          
     } catch (Exception e) {
            logger.error("Error in REST service",e);
            logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
   
    
    
    
    @PostConstruct
    public void initIt() {
	  logger.info("Init Models" );
          try{
           
          }catch(Exception e){
          logger.error("Unable to start service at deploy time. "+e);
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
