import org.junit.Test;
import static org.junit.Assert.*;
import es.upm.oeg.pal.dm.model.DatasetAuthRequest;
import es.upm.oeg.pal.dm.model.DatasetAuthResponse;
import es.upm.oeg.pal.dm.store.Authorizer;


/**
 * Authorization tests.
 * @author vroddon
 */
public class AuthorizerTest {
    
    @Test
    public void testSimple() {    
        System.out.println("Simple auth rquest");
        
        DatasetAuthRequest req = new DatasetAuthRequest();         
        req.setLicense("http://purl.org/NET/rdflicense/cc-by-nc4.0");
        req.setPurpose("commercial");
        req.setInstitution("research");
        DatasetAuthResponse resp = Authorizer.authorize(req);
        assertEquals(20, 20);          
    }    
    
}
