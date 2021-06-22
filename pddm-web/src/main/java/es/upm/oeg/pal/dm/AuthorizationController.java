package es.upm.oeg.pal.dm;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.upm.oeg.pal.dm.model.DSAuth;
import es.upm.oeg.pal.dm.store.LicenseIO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
            value = "/authorizeDS",
            consumes = "application/json;charset=UTF-8",
            produces= "application/text;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String dspacesAuth(@RequestBody DSAuth DsAuth)  {
        String licenseid = DsAuth.getLicense();
        String rdf = LicenseIO.getLicenseFromId(licenseid);
        return "I received the License  "+ DsAuth.getLicense() + " for "+ DsAuth.getPurpose();
    }
    
       
    
    
    
    
    
}
