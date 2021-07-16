package es.upm.oeg.pal.dm.model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author vroddon
 */
public class DatasetAuthResponse {
    
    private boolean authorized = false;
    private String explanation = "";
    
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(this);
             return jsonString;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return the authorized
     */
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * @param authorized the authorized to set
     */
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    /**
     * @return the explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * @param explanation the explanation to set
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
}
