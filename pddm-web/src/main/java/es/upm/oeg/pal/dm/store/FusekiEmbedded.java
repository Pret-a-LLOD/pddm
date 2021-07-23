package es.upm.oeg.pal.dm.store;

import java.io.File;
import java.io.StringWriter;
import org.apache.commons.io.FileUtils;
import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.lib.StoredConfig;

/**
 * Loads the collection of licenses in the repo
 *
 * @author vroddon
 */
public class FusekiEmbedded {

    public static void main(String args[]) {
//        startServer();
        startup();
    }

    public static void startup() {

        //We clone the git into a temporal folder
      //  clonegit();
/*
        //We clone the git into a temporal folder
        pullgit();

        //We load all the licenses
        loadlicenses("..\\..\\temporal\\data\\licenses");
        */
        //We start the server
        startServer();
    }

    public static void clonegit() {
        String repo = "https://github.com/Pret-a-LLOD/pddm/";
        String folder = "..\\..\\temporal";
        try {
            FileUtils.deleteDirectory(new File(folder));
            Git.cloneRepository().setURI(repo).setDirectory(new File(folder)).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pullgit() {
        try{
        String repo = "https://github.com/Pret-a-LLOD/pddm/";
        String folder = "..\\..\\temporal";
        Git git = Git.open(new File(folder));
       /* CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setURI(repo);
        cloneCommand.setDirectory(new File(folder));
        cloneCommand.call();*/

//        Git git = Git.open(new File(folder));
        /*
        StoredConfig config = git.getRepository().getConfig();
        config.setString("branch", "master", "merge", "refs/heads/master");
        config.setString("branch", "master", "remote", "origin");
        config.setString("remote", "origin", "fetch", "+refs/heads/*:refs/remotes/origin/*");
     //   config.setString("remote", "origin", "url", repo);
        config.save();
        */
        
        PullResult result = git.pull().setRemote("origin").setRemoteBranchName("master").call();        
        PullCommand pull = git.pull();
        pull.setRemote("origin");
        pull.setRemoteBranchName("master");        
        pull.call();
        git.close();
        
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    
    public static void loadlicenses(String folder) {
        File ffolder = new File(folder);
        Dataset ds = DatasetFactory.createTxnMem();
        for (final File fileEntry : ffolder.listFiles()) {
            System.out.println("Processing: " + fileEntry.getName());
            loadlicense(fileEntry.getAbsolutePath());
        }
    }

    public static void loadlicense(String file) {
        try {
            Dataset dtmp = RDFDataMgr.loadDataset(file);

        } catch (Exception e) {
            System.out.println("NOT loaded: " + file);
        }

    }

    public static void startServer() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
        Logger.getLogger("org.eclipse").setLevel(Level.OFF);
        Logger.getRootLogger().setLevel(Level.OFF);

        try {
            Dataset dataset = RDFDataMgr.loadDataset("http://rdflicense.appspot.com/rdflicense/ms-c-nored-ff.ttl");

            Model model = dataset.getDefaultModel();
            String syntax = "TURTLE"; // also try "N-TRIPLE" and "TURTLE"
            StringWriter out = new StringWriter();
            model.write(out, syntax);
            String result = out.toString();
           // System.out.println(result);

            FusekiServer server = FusekiServer.create().port(3330).enableCors(true).add("/ds", dataset, true).build();
            server.start();
        
            try (QueryExecution qExec = QueryExecutionFactory.create("SELECT * {?s ?p ?o}", dataset)) {
                ResultSet rs = qExec.execSelect();
                ResultSetFormatter.outputAsTSV(rs);
                int tot = rs.getRowNumber();
                System.out.println("triples " + tot);
                ResultSetFormatter.out(rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("sadfddddddddddddd");
try{System.in.read();}
catch(Exception e){e.printStackTrace();}

            server.stop();

        } catch (Exception e2) {

        }
    }
}
