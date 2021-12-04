package es.upm.oeg.pal.dm;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.upm.oeg.pal.dm.model.DatasetAuthRequest;
import es.upm.oeg.pal.dm.model.DatasetAuthResponse;
import es.upm.oeg.pal.dm.store.Authorizer;
import es.upm.oeg.pal.dm.store.LicenseIO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vroddon.rdf.URLUtils;

/**
 * @todo testing with the new cors...
 * @author vroddon, pablo
 */
@Controller
@Api(tags = "Authorization", value = "Authorization")
public class AuthorizationController {
    
    
    
     
    @CrossOrigin
    @ApiOperation(value = "Queries if the license is can be used")
    @RequestMapping(
            value = "/authorizeDS",
            consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public String dspacesAuth(@RequestBody DatasetAuthRequest areq)  {
        System.out.println("We are about to authorize!");
        DatasetAuthResponse r = Authorizer.authorize(areq);
        return r.toString();
    }
    
    
    
    
}
