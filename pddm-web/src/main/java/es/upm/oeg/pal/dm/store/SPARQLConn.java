package es.upm.oeg.pal.dm.store;

import static io.swagger.annotations.SwaggerDefinition.Scheme.HTTP;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * This is just a test of SPARQL Connection to a digest-authorized Virtuoso SPARQL Endpoint.
 *
 * @author vroddon
 */
public class SPARQLConn {

    public static void main(String arg[]) {
            String clave = ""; //clave de maria navas y mía
            String usuario = "dba";
            String endpoint = "http://127.0.0.1:8890/sparql-auth";
            String graph = "http://test.com";
            String query = "PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT { <http://example/egbook> dc:title  \"maria navas\" . } WHERE {}";
            testQuery(endpoint, usuario, clave, graph, query);
    }
    
    /**
     * Makes a SPARQL query in a Virtuoso SPARQL endpoint with digest authorization.
     */
    public static void testQuery(String endpoint, String usuario, String clave, String graph, String query)
    {
        try {
            
            
            DefaultHttpClient client = new DefaultHttpClient();
            client.getCredentialsProvider().setCredentials(new AuthScope(null, -1, null), new UsernamePasswordCredentials("dba", clave));

            HttpPost post = new HttpPost(URI.create(endpoint));
//            post.setHeader("Content-Type","application/x-www-form-urlencoded");
//            post.setHeader("Accept:", "application/sparql-results+json");
            
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("domain", "dba"));
            nvps.add(new BasicNameValuePair("default-graph-uri", graph));
            nvps.add(new BasicNameValuePair("query", query));
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            DigestScheme digestAuth = new DigestScheme();
            digestAuth.overrideParamter("algorithm", "MD5");
            digestAuth.overrideParamter("realm", endpoint);
            digestAuth.overrideParamter("nonce", Long.toString(new Random().nextLong(), 36));
            digestAuth.overrideParamter("qop", "auth");
            digestAuth.overrideParamter("nc", "0");
            digestAuth.overrideParamter("cnonce", DigestScheme.createCnonce());

            Header auth = digestAuth.authenticate(new UsernamePasswordCredentials("dba", clave), post);
            System.out.println(auth.getName());
            System.out.println(auth.getValue());
            post.setHeader(auth);

            HttpResponse ret = client.execute(post);
            ByteArrayOutputStream v2 = new ByteArrayOutputStream();
            ret.getEntity().writeTo(v2);
            System.out.println("----------------------------------------");
            System.out.println(v2.toString());
            System.out.println("----------------------------------------");
            System.out.println(ret.getStatusLine().getReasonPhrase());
            System.out.println(ret.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
