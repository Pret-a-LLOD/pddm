package es.upm.oeg.pal.dm.model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author vroddon
 */
public class DatasetAuthResponse {
    
    private boolean isAuth = false;
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
     * @return the isAuth
     */
    public boolean isIsAuth() {
        return isAuth;
    }

    /**
     * @param isAuth the isAuth to set
     */
    public void setIsAuth(boolean isAuth) {
        this.isAuth = isAuth;
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
