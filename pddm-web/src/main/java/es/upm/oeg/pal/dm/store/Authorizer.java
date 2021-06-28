package es.upm.oeg.pal.dm.store;

import es.upm.oeg.pal.dm.model.DatasetAuthRequest;
import es.upm.oeg.pal.dm.model.DatasetAuthResponse;
import java.net.URI;
import java.util.List;
import oeg.jodrlapi.helpers.ODRLRDF;
import oeg.jodrlapi.odrlmodel.Action;
import oeg.jodrlapi.odrlmodel.Constraint;
import oeg.jodrlapi.odrlmodel.Policy;
import oeg.jodrlapi.odrlmodel.Rule;
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
        DatasetAuthResponse r = new DatasetAuthResponse();
        r.setAuthorized(false);
        r.setExplanation("No reproduction of the work is permitted");
        
        String paramlicense = req.getLicense();

        String rdf = getLicenseRDF(paramlicense);
        if (rdf.equals("error")) {
            r.setAuthorized(false);
            r.setExplanation("Unknown license");
            return r;
        }

        System.out.println(rdf);
        Policy policy = ODRLRDF.getPolicy(rdf);
        System.out.println(policy.title);
        List<Rule> rules = policy.getPermissions();
        for(Rule rule : rules)
        {
            List<Action> actions  = rule.getActions();
            for(Action action : actions)
            {
                System.out.println(action);
                if (action.toString().contains("Reproduction"))
                {
                    r.setAuthorized(true);
                    r.setExplanation("");
                }
            }
            List<Constraint> constraints = rule.getConstraint();
            for(Constraint constraint : constraints)
            {
                boolean ok = evaluate(req, constraint);
            }
        }


        r.setExplanation("ok");
//        String rdf = LicenseIO.getLicenseFromId(paramlicense);
        String explanation = "I received the License  " + req.getLicense() + " for " + req.getPurpose() + "";

        r.setExplanation(explanation);
        return r;
    }
    
    public static boolean evaluate(DatasetAuthRequest req, Constraint c)
    {
        System.out.println(c.toString());
        
        
        return true;
    }

    public static String getLicenseRDF(String paramlicense) {
        URI urilicense = null;
        String rdf = "error";
        try {
            if (paramlicense.contains("rdflicense/")) {
                urilicense = new URI(paramlicense + ".ttl");
                rdf = IOUtils.toString(urilicense, "UTF-8");
            } else {
                paramlicense = "https://raw.githubusercontent.com/Pret-a-LLOD/pddm/develop/data/licenses/" + paramlicense;
                urilicense = new URI(paramlicense + ".ttl");
                rdf = IOUtils.toString(urilicense, "UTF-8");
            }
        } catch (Exception e1) {
            try {
                if (!paramlicense.contains("http")) {
                    paramlicense = "https://raw.githubusercontent.com/Pret-a-LLOD/pddm/develop/data/licenses/" + paramlicense;
                    urilicense = new URI(paramlicense + ".ttl");
                } else {
                    URI uri = new URI(paramlicense);
                    String path = uri.getPath();
                    String id = path.substring(path.lastIndexOf('/') + 1);
                    paramlicense = "https://raw.githubusercontent.com/Pret-a-LLOD/pddm/develop/data/licenses/" + id;
                    urilicense = new URI(paramlicense + ".ttl");
                }
                rdf = IOUtils.toString(urilicense, "UTF-8");
            } catch (Exception e2) {
            }
        }
            return rdf;

        }
}