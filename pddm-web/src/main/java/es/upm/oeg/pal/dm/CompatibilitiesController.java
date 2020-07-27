/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm;

import static es.upm.oeg.pal.dm.LicenseManagerController.logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    
    
    
     //GET compatibility?license1={lincenseid1}&license2={lincenseid2}
     //GET minimumCompatibilityLicense?license1={lincenseid1}&license2={lincenseid2}
    
    
    @ApiOperation(value = "Verifies the compatibility of two licenses")
    @RequestMapping(
            value = "/compatibility",
            consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public String compatibility(@RequestParam(name="license1", required = true, defaultValue = "L1") String License1, @RequestParam(name="License2", required = true, defaultValue = "L2") String name) throws Exception {

        
     
            try {
          
              
            
           
         
            
          
            
            
        } catch (Exception e) {
            logger.error("Error in REST service",e);
            logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
    
      
    
    
     
   // @ApiOperation(value = "Service to manage licenses")
    @ApiOperation(value = "Get the minimum requirements for the compatibility of two licenses")
    @RequestMapping(
            value = "/minimumCompatibilityLicense",
            consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public String minimumCompatibilityLicense(@RequestParam(name="license1", required = true, defaultValue = "L1") String License1, @RequestParam(name="License2", required = true, defaultValue = "L2") String name) throws Exception {

        
     
            try {
          
              
            
           
         
            
          
            
            
        } catch (Exception e) {
            logger.error("Error in REST service",e);
            logger.error(e.getCause().toString());
           
            
        }

        return "";
    }
    
    
    
    
}
