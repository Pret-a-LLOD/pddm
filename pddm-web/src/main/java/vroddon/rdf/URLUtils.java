package vroddon.rdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Victor
 */
public class URLUtils {

    /**
     * Tries to resolve a URL using content negotiation to get RDF.
     * - following redirects 
     * - throtling if the server is complaining about too many requests
     * - implementing the best content negotiation curl -I -L -H "Accept: application/rdf+xml"
     * @param url For example, http://datos.bne.es/resource/XX947766
     */
    public static String browseSemanticWeb(String url) {
        String document = "";
        String acceptHeaderValue = "application/turtle,application/x-turtle,application/rdf+xml;q=0.4,text/n3;q=0.8,text/turtle;q=0.8,text/rdf+n3;q=0.7,application/xml;q=0.5,text/xml;q=0.5,text/plain;q=0.4,application/json;q=0.3,*/*;q=0.2";
        boolean redirect = false;
        try {
            URL obj = new URL(url);

            URLConnection oc = obj.openConnection();
            HttpURLConnection conn = null;
            if (url.startsWith("https")) {
                conn = (HttpsURLConnection) oc;
            } else {
                conn = (HttpURLConnection) oc;
            }
            conn.setRequestProperty("Accept", acceptHeaderValue);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER) {
                    redirect = true;
                }
            }
            if (redirect) {
                String newUrl = conn.getHeaderField("Location");
                String cookies = conn.getHeaderField("Set-Cookie");
                conn.disconnect();
                if (newUrl.startsWith("https")) {
                    conn = (HttpsURLConnection) new URL(newUrl).openConnection();
                } else {
                    conn = (HttpURLConnection) new URL(newUrl).openConnection();
                }
                if (cookies != null) {
                    conn.setRequestProperty("Cookie", cookies);
                }
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer html = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
                html.append("\n");
            }
            in.close();
            document = html.toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error navegando la web semántica " + url +" con error "+ e.getMessage());
            String k = e.getMessage();
            if (k.contains("429"))
            {
                try{
                    System.err.println("Throtling. Let us wait 3 mins...");
                    Thread.sleep(3 *60 *1000);
                }catch(Exception e2)
                {
                    e.printStackTrace();
                    return document;
                }
            }
        }
        return document;
    }

    public static boolean checkIfURLExists(String targetUrl) {
        HttpURLConnection httpUrlConn;
        try {
            httpUrlConn = (HttpURLConnection) new URL(targetUrl).openConnection();

            // A HEAD request is just like a GET request, except that it asks
            // the server to return the response headers only, and not the
            // actual resource (i.e. no message body).
            // This is useful to check characteristics of a resource without
            // actually downloading it,thus saving bandwidth. Use HEAD when
            // you don't actually need a file's contents.
            httpUrlConn.setRequestMethod("HEAD");

            // Set timeouts in milliseconds
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);

            // Print HTTP status code/message for your information.
            System.out.println("Response Code: "
                    + httpUrlConn.getResponseCode());
            System.out.println("Response Message: "
                    + httpUrlConn.getResponseMessage());

            return (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * https://stackoverflow.com/questions/736556/binary-search-in-a-sorted-memory-mapped-file-in-java
     */
    public static List<String> binarySearch(String filename, String string) {
        List<String> result = new ArrayList<String>();
        try {
            File file = new File(filename);
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            long low = 0;
            long high = file.length();

            long p = -1;
            while (low < high) {
                long mid = (low + high) / 2;
                p = mid;
                while (p >= 0) {
                    raf.seek(p);

                    char c = (char) raf.readByte();
                    //System.out.println(p + "\t" + c);
                    if (c == '\n') {
                        break;
                    }
                    p--;
                }
                if (p < 0) {
                    raf.seek(0);
                }
                String line = raf.readLine();
                //System.out.println("-- " + mid + " " + line);
                if (line.compareTo(string) < 0) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }

            p = low;
            while (p >= 0) {
                raf.seek(p);
                if (((char) raf.readByte()) == '\n') {
                    break;
                }
                p--;
            }

            if (p < 0) {
                raf.seek(0);
            }

            while (true) {
                String line = raf.readLine();
                if (line == null || !line.startsWith(string)) {
                    break;
                }
                result.add(line);
            }

            raf.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }
        return result;
    }

    public static String getRedirectedURL(String url) {
        try {
            HttpURLConnection con2 = (HttpURLConnection)(new URL( url ).openConnection());
            con2.setInstanceFollowRedirects( false );
            con2.connect();
            int responseCode = con2.getResponseCode();
            System.out.println( responseCode );
            String location = con2.getHeaderField( "Location" );
            System.out.println( location );            
            
            URLConnection con = new URL(url).openConnection();
            System.out.println("orignal url: " + con.getURL());
            con.connect();
            System.out.println("connected url: " + con.getURL());
            InputStream is = con.getInputStream();
            System.out.println("redirected url: " + con.getURL());
            is.close();
            return con.getURL().toString();
        } catch (Exception e) {
        }
        return url;
    
    }
    
    public static String invoke(String urlString) {
        HttpGet get = new HttpGet(urlString);
        get.setHeader("Content-Type", "text/plain; charset=utf-8");
//        get.setHeader("api_key", apiKey);
        HttpResponse resp = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
     //       logger.debug("Invoking: " + get);
            resp = httpClient.execute(get);
            if (resp != null && resp.getStatusLine() != null) {
                if (resp.getStatusLine().getStatusCode() >= 200 && resp.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toString(resp.getEntity());
                }
            }
        } catch (Exception e) {

        }
        return "";
    }    
    
}

