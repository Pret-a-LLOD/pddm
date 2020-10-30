
package es.upm.oeg.pal.dm;

import es.upm.oeg.pal.dm.store.FusekiConn;
import io.swagger.annotations.Api;
import java.util.List;
import javax.annotation.PostConstruct;
import oeg.jodrlapi.helpers.ODRLRDF;
import oeg.jodrlapi.odrlmodel.Policy;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
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
    
    //    static Logger logger = Logger.getLogger(Controller.class);
    LicenseService licenseService;
  
    
    
    
    @RequestMapping(
            value = "/license/",
            //consumes = "application/json;charset=UTF-8",
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLicenses()  {

      return ResponseEntity.ok( FusekiConn.getAllGraphs());
  
    }
    
    
    
    
    /*
    
    @POST
    @Path("/")
    @Consumes({"text/turtle", "application/rdf+xml","application/ld+json"}) 
    @Produces("application/json")
    @ApiOperation(value = "validator", notes = "Returns if a policy is valid, not valid or unknown. Checks the conformance of ODRL Policy expressions with respect to the ODRL Information Model validation requirements. ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ValidatorResponse.class)
        ,
                                @ApiResponse(code = 400, message = "Bad Request")
        ,
                                @ApiResponse(code = 415, message = "Unsupported Media Type")})
    public Response validator(@ApiParam(name = "policy", value = "ODRL policy serialized as RDF Turtle or RDF/XML", required = true) String rdf) {
        try {
            ODRLValidator validator = new ODRLValidator();
            ValidatorResponse vres = validator.validate(rdf);
            return Response.status(vres.status).entity(vres).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal error.").build();
        }
    }
    
    */
    
    
    @RequestMapping(
            value = "/license/{id}",
            //consumes = "application/json;charset=UTF-8",
            //consumes={"text/turtle", "application/rdf+xml","application/ld+json"},
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getLicense(@PathVariable String id)  {

        
        //BOOST1.0
        System.out.println(id);
       
        if(!FusekiConn.checkIfGraphExists(id)){
            System.out.println("not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
       
        Model model = FusekiConn.getGraph(id);
        return (ResponseEntity.ok(model.toString()));
       
          
    }
    
    
     /*
    
    @POST
    @Path("/")
    @Consumes({"text/turtle", "application/rdf+xml","application/ld+json"}) 
    @Produces("application/json")
    @ApiOperation(value = "validator",
    
    notes = "Returns if a policy is valid, not valid or unknown. Checks the conformance of ODRL Policy expressions with respect to the ODRL Information Model validation requirements. ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ValidatorResponse.class)
        ,
                                @ApiResponse(code = 400, message = "Bad Request")
        ,
                                @ApiResponse(code = 415, message = "Unsupported Media Type")})
    public Response validator(@ApiParam(name = "policy", value = "ODRL policy serialized as RDF Turtle or RDF/XML", required = true) String rdf) {
        try {
            ODRLValidator validator = new ODRLValidator();
            ValidatorResponse vres = validator.validate(rdf);
            return Response.status(vres.status).entity(vres).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal error.").build();
        }
    }
    
    */
    
     @RequestMapping(
            value = "/license",
            consumes = {"text/turtle;charset=UTF-8", "application/rdf+xml;charset=UTF-8","application/ld+json;charset=UTF-8"},
            produces= "application/json;charset=UTF-8",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postLicense(@RequestBody String License) throws Exception {

            //this.licenseService.addLicense(id);
            
            
            System.out.println("POST Service");
            LOGGER.info("Post Service");
            try{
            List<Policy> policies = ODRLRDF.load(License); 
            FusekiConn.uploadGraph(License);
            
            /*
            System.out.println("Policies Received:" + policies.size());
        
        
            for(Policy pol:policies){

                if(pol.uri == null){
                    System.out.println("nulo");
                    continue;

                }

                System.out.println(pol.uri);
                String rdf=ODRLRDF.getRDF(pol,Lang.TTL);
                System.out.println(rdf);
              }
            */
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
