package es.upm.oeg.pal.dm.store;

import es.upm.oeg.pal.dm.model.DatasetAuthRequest;
import es.upm.oeg.pal.dm.model.DatasetAuthResponse;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import oeg.jodrlapi.helpers.ODRLRDF;
import oeg.jodrlapi.helpers.RDFUtils;
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
                if (ok==false)
                {
                    r.setAuthorized(false);
                    r.setExplanation("Not authorized because of the " + RDFUtils.getLastPartOfUri(constraint.rightOperand));
                }
            }
        }


   //     r.setExplanation("ok");
//        String rdf = LicenseIO.getLicenseFromId(paramlicense);
  //      String explanation = "I received the License  " + req.getLicense() + " for " + req.getPurpose() + "";

  //      r.setExplanation(explanation);
        return r;
    }
    
    public static boolean evaluate(DatasetAuthRequest req, Constraint c)
    {
        boolean ok = true;
        System.out.println(c.toString());
        if (c.rightOperand.contains("purpose") && c.leftOperand.contains("languageEngineeringResearch"))
        {
            ok = false;
            if (req.getPurpose().contains("research") || req.getPurpose().contains("languageEngineeringResearch"))
            {
                ok=true;
            }
        }
        return ok;
    }

    public static String getLicenseRDF(String paramlicense) {
        URI urilicense = null;
        String rdf = "error";
        try {
            if (paramlicense.contains("rdflicense/")) {
                if (!paramlicense.contains(".ttl"))
                    urilicense = new URI(paramlicense + ".ttl");
                urilicense = getFinalURL(urilicense.toURL()).toURI();
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

public static URL getFinalURL(URL url) {
    try {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setInstanceFollowRedirects(false);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        con.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        con.addRequestProperty("Referer", "https://www.google.com/");
        con.connect();
        //con.getInputStream();
        int resCode = con.getResponseCode();
        if (resCode == HttpURLConnection.HTTP_SEE_OTHER
                || resCode == HttpURLConnection.HTTP_MOVED_PERM
                || resCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            String Location = con.getHeaderField("Location");
            if (Location.startsWith("/")) {
                Location = url.getProtocol() + "://" + url.getHost() + Location;
            }
            return getFinalURL(new URL(Location));
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return url;
}

}