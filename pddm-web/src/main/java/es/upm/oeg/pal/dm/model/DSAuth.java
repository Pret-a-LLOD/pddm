/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.oeg.pal.dm.model;

/**
 *
 * @author Pablo
 */
public class DSAuth {
    
    private String Purpose;
    private String InitDate;
    private String EndDate;
    private Boolean Academic;
    private String License;

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    public String getInitDate() {
        return InitDate;
    }

    public void setInitDate(String InitDate) {
        this.InitDate = InitDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public Boolean getAcademic() {
        return Academic;
    }

    public void setAcademic(Boolean Academic) {
        this.Academic = Academic;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String License) {
        this.License = License;
    }
    
    
}
