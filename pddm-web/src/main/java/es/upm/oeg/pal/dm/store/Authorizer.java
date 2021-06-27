package es.upm.oeg.pal.dm.store;

import es.upm.oeg.pal.dm.model.DatasetAuthRequest;
import es.upm.oeg.pal.dm.model.DatasetAuthResponse;
import java.net.URI;
import oeg.jodrlapi.helpers.ODRLRDF;
import oeg.jodrlapi.odrlmodel.Policy;
import org.apache.commons.io.IOUtils;
import vroddon.rdf.URLUtils;

/**
 *
 * @author vroddon
 */
public class Authorizer {

    public static void main(String args[]) {
        System.out.println("test de authorizer");
    }

    public static DatasetAuthResponse authorize(DatasetAuthRequest req) {
        String paramlicense = req.getLicense();
        URI urilicense = null;
        try {
            if (!paramlicense.contains("http"))
            {
                paramlicense = "https://raw.githubusercontent.com/Pret-a-LLOD/pddm/develop/data/licenses/"+paramlicense;
                urilicense = new URI(paramlicense+".ttl");
            }
            else
            {
                URI uri = new URI(paramlicense);
                String path = uri.getPath();
                String id = path.substring(path.lastIndexOf('/') + 1);
                paramlicense = "https://raw.githubusercontent.com/Pret-a-LLOD/pddm/develop/data/licenses/"+id;
                urilicense = new URI(paramlicense+".ttl");
            }
            String rdf = IOUtils.toString(urilicense, "UTF-8");
            System.out.println(rdf);
            Policy policy = ODRLRDF.getPolicy(rdf);
            System.out.println(policy.title);

        } catch (Exception e) {
            DatasetAuthResponse r = new DatasetAuthResponse();
            r.setIsAuth(false);
            r.setExplanation("Unknown license");
            return r;
        }

        DatasetAuthResponse r = new DatasetAuthResponse();
        r.setExplanation("ok");
//        String rdf = LicenseIO.getLicenseFromId(paramlicense);
        String explanation = "I received the License  " + req.getLicense() + " for " + req.getPurpose() + "";
        r.setExplanation(explanation);
        return r;
    }

}
