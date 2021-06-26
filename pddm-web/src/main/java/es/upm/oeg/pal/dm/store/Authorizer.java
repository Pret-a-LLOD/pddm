package es.upm.oeg.pal.dm.store;

import es.upm.oeg.pal.dm.model.DatasetAuthRequest;
import es.upm.oeg.pal.dm.model.DatasetAuthResponse;
import vroddon.rdf.URLUtils;

/**
 *
 * @author vroddon
 */
public class Authorizer {
    
    public static void main(String args[])
    {
        System.out.println("test de authorizer");
    }
    
    
    public static DatasetAuthResponse authorize(DatasetAuthRequest req)
    {
        String licenseid = req.getLicense();
        String body = URLUtils.browseSemanticWeb(licenseid);
        DatasetAuthResponse r = new DatasetAuthResponse();
        r.setExplanation("ok");
//        String rdf = LicenseIO.getLicenseFromId(licenseid);
        String explanation =  "I received the License  "+ req.getLicense() + " for "+ req.getPurpose()+"";
        r.setExplanation(explanation);
        return r;
    }
    
}
