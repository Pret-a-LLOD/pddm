/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm;

import es.upm.oeg.pal.dm.model.DSAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.jena.rdf.model.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Pablo
 */
@Controller
@Api(tags = "Authorization", value = "Authorization")
public class AuthorizationController {
    
    
    
     
    
    @ApiOperation(value = "Queries if the license is can be used")
    @RequestMapping(
            value = "/processText",
            consumes = "application/json;charset=UTF-8",
            produces= "application/text;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String dspacesAuth(@RequestBody DSAuth DsAuth)  {

        
        return "I received the License  "+ DsAuth.getLicense() + " for "+ DsAuth.getPurpose();
      
    }
    
       
    
    
    
    
    
}
