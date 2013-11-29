/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.Center;
import org.dutyroster.web.services.AdminCenterService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "center")
//@SessionScoped
@RequestScoped
public class AdminCenterController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short centerID;
    private String centerName;
    private AdminCenterService adminCenterService = new AdminCenterService();
    private Center[] centers = null;
    private Center selected;
    private Short cIDSelected;
    private String filterText;

    /** Creates a new instance of AdminCenterController */
    public AdminCenterController() {
        try {
            centers = adminCenterService.searchAllCenters();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Short getcenterID() {
        return centerID;
    }

    public void setcenterID(Short centerID) {
        this.centerID = centerID;
    }

    public String getcenterName() {
        return centerName;
    }

    public void setcenterName(String centerName) {
        this.centerName = centerName;
    }

    public AdminCenterService getAdminCenterService() {
        return adminCenterService;
    }

    public void setAdminCenterService(AdminCenterService adminCenterService) {
        this.adminCenterService = adminCenterService;
    }

    public Center[] getCenters() {
        return centers;
    }

    public void setCenters(Center[] centers) {
        this.centers = centers;
    }

    public Center getSelected() {
        return selected;
    }

    public void setSelected(Center selected) {
        this.selected = selected;
    }

    public Short getcIDSelected() {
        return cIDSelected;
    }

    public void setcIDSelected(Short cIDSelected) {
        this.cIDSelected = cIDSelected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
    
    public void addCenterInfo() {
        String response = null;
        Center dtoCenter = new Center();
        dtoCenter.setCenterId(this.getcenterID());
        dtoCenter.setCenterName(this.getcenterName());
        this.setcenterID(null);
        this.setcenterName(null);

        if (dtoCenter.getCenterName() == null || dtoCenter.getCenterName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Center Name left blank. Please Enter Center Name."));
        } else {
            response = adminCenterService.addCenter(dtoCenter);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
                centers = adminCenterService.searchAllCenters();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editCenterInfo() {
        String response = null;
        Center dtoCenter = new Center();
        dtoCenter.setCenterId(this.getcenterID());
        dtoCenter.setCenterName(this.getcenterName());
        this.setcenterID(null);
        this.setcenterName(null);

        if (dtoCenter.getCenterId()== (short)0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Center ID left blank. Please select a Center ID to update."));
        } else if (dtoCenter.getCenterName() == null || dtoCenter.getCenterName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Center Name left blank. Please Enter Center Name to update."));
        } else {
            response = adminCenterService.editCenter(dtoCenter);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                centers = adminCenterService.searchAllCenters();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteCenterInfo() {
        String response = null;
        Center dtoCenter = new Center();
        dtoCenter.setCenterId(this.getcenterID());
        dtoCenter.setCenterName(this.getcenterName());
        this.setcenterID(null);
        this.setcenterName(null);

        if (dtoCenter.getCenterId()== (short)0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Center ID left blank. Please select a center ID to delete."));
        } else {
            response = adminCenterService.deleteCenter(dtoCenter);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                centers = adminCenterService.searchAllCenters();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newCenterInfo() {
        Short lastCenterId = 0;
        int newCenterId = 0;
        lastCenterId = adminCenterService.getLastCenterID();
        newCenterId = lastCenterId + 1;
        this.setcenterID((short) newCenterId);
        this.setcenterName(null);
    }

    public String logout() throws Exception {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //Removing the User from Session
        if (request.getSession().getAttribute(Constants.SESSION_USER) != null) {
            request.getSession().removeAttribute(Constants.SESSION_USER);
            request.getSession().invalidate();
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("../user/userLogin.jsf");
        return Constants.SUCCESS;
    }

    public void resetCenterInfo() {
        selected = null;
        this.setcenterID(null);
        this.setcenterName(null);
    }

    public void selectedCenter() {
        if (selected != null) {
            this.setcenterID(selected.getCenterId());
            this.setcenterName(selected.getCenterName());
            selected = null;
        }
    }

    public void searchCenterInfo() {
        if ( filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            centers = adminCenterService.searchCentersByFilter(filterText);
        }
    }

}

