package es.upm.oeg.pal.dm.model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Request for authorization from DSpaces. This should be represented with a
 * ODRL request. Temporary solution.
 *
 * @author Pablo, vroddon
 */
public class DSAuth {

    private String license;
    private String institution;
    private String purpose;
    private String initDate;
    private String endDate;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(this);
             return jsonString;
        } catch (Exception e) {
            return "";
        }
    }

}
