package es.upm.oeg.pal.dm.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;

/**
 * Request for authorization from DSpaces. This should be represented with a
 * ODRL request. Temporary solution.
 *
 * @author Pablo, vroddon
 */
public class DatasetAuthRequest {

    @ApiModelProperty(value = "URI of a rdf license", example = "http://purl.org/NET/rdflicense/ms-c-nored-ff")
    private String license;

    @ApiModelProperty(value = "Examples: research, commercial", example = "research")
    private String institution;

    @ApiModelProperty(value = "Examples: research, commercial", example = "research")
    private String purpose;

    @ApiModelProperty(value = "Date in ISO format", example = "2020-06-21")
    private String initDate;

    @ApiModelProperty(value = "Date in ISO format", example = "2020-06-21")
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

    /**
     * @return the initDate
     */
    public String getInitDate() {
        return initDate;
    }

    /**
     * @param initDate the initDate to set
     */
    public void setInitDate(String initDate) {
        this.initDate = initDate;
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
