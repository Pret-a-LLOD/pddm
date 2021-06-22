/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm.store;

import java.io.StringWriter;
import java.io.Writer;
import org.apache.jena.rdf.model.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Some methods related to CRUD operations over license.
 * @author vroddon
 */
public class LicenseIO {

    public static String getLicenseFromId(String id) {
        if (!FusekiConn.checkIfGraphExists(id)) {
            System.out.println("not found");
            return "";
        }

        Model model = FusekiConn.getGraph(id);

        Writer writer = new StringWriter();

        model.write(writer, "TTL");

        String data = writer.toString();
        //RDFDataMgr.write(s, model, Lang.TTL) ;                
        return data;
    }
}
