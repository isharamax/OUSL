/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;


import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.Alert;
import org.dutyroster.web.services.AdminAlertService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "alert")
@SessionScoped
public class AdminAlertController implements Serializable {

    private Short alertID;
    private String alertName;
    private String alertContent;
    private AdminAlertService adminAlertService = new AdminAlertService();
    private Alert[] alerts = null;
    private Alert selected;
    private String filterText;

    /** Creates a new instance of AdminAlertController */
    public AdminAlertController() {
        try {
            alerts = adminAlertService.searchAllAlerts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Short getAlertID() {
        return alertID;
    }

    public void setAlertID(Short alertID) {
        this.alertID = alertID;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public AdminAlertService getAdminAlertService() {
        return adminAlertService;
    }

    public void setAdminAlertService(AdminAlertService adminAlertService) {
        this.adminAlertService = adminAlertService;
    }

    public Alert[] getAlerts() {
        return alerts;
    }

    public void setAlerts(Alert[] alerts) {
        this.alerts = alerts;
    }

    public Alert getSelected() {
        return selected;
    }

    public void setSelected(Alert selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void addAlertInfo() {
        String response = null;
        Alert dtoAlert = new Alert();
        dtoAlert.setAlertId(this.getAlertID());
        dtoAlert.setAlertName(this.getAlertName());
        dtoAlert.setAlertBody(this.getAlertContent());
        this.setAlertID(null);
        this.setAlertName(null);
        this.setAlertContent(null);

        if (dtoAlert.getAlertName() == null || dtoAlert.getAlertName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Alert Name left blank. Please Enter Alert Name."));
        } else if (dtoAlert.getAlertBody() == null || dtoAlert.getAlertBody().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Alert Content left blank. Please Enter Alert Content."));
        } else {
            response = adminAlertService.addAlert(dtoAlert);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
                alerts = adminAlertService.searchAllAlerts();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void newAlertInfo() {
        Short lastAlertId = 0;
        int newAlertId = 0;
        lastAlertId = adminAlertService.getLastalertID();
        newAlertId = lastAlertId +  1;
        this.setAlertID((short) newAlertId);
        this.setAlertName(null);
        this.setAlertContent(null);
    }

    public void editAlertInfo() {
        String response = null;
        Alert dtoAlert = new Alert();
        dtoAlert.setAlertId(this.getAlertID());
        dtoAlert.setAlertName(this.getAlertName());
        dtoAlert.setAlertBody(this.getAlertContent());
        this.setAlertID(null);
        this.setAlertName(null);
        this.setAlertContent(null);

        if (dtoAlert.getAlertId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Alert ID left blank. Please select a Alert ID to update."));
        } else if (dtoAlert.getAlertName() == null || dtoAlert.getAlertName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Alert Name left blank. Please Enter Alert Name to update."));
        } else if (dtoAlert.getAlertBody() == null || dtoAlert.getAlertBody().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Alert Content left blank. Please Enter Alert Content to update."));
        } else {
            response = adminAlertService.editAlert(dtoAlert);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                setAlerts(adminAlertService.searchAllAlerts());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteAlertInfo() {
        String response = null;
        Alert dtoAlert = new Alert();
        dtoAlert.setAlertId(this.getAlertID());
        dtoAlert.setAlertName(this.getAlertName());
        dtoAlert.setAlertBody(this.getAlertContent());
        this.setAlertID(null);
        this.setAlertName(null);
        this.setAlertContent(null);

        if (dtoAlert.getAlertId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Alert ID left blank. Please select a Alert ID to delete."));
        } else {
            response = adminAlertService.deleteAlert(dtoAlert);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                setAlerts(adminAlertService.searchAllAlerts());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
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

    public void resetAlertInfo() {
        selected = null;
        this.setAlertID(null);
        this.setAlertName(null);
        this.setAlertContent(null);
    }

    public void selectedAlert() {
        if (selected != null) {
            this.setAlertID(getSelected().getAlertId());
            this.setAlertName(getSelected().getAlertName());
            this.setAlertContent(getSelected().getAlertBody());
            setSelected(null);
        }
    }

    public void searchAlertInfo() {
        if (getFilterText() == null && getFilterText().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            setAlerts(adminAlertService.searchAlertsByFilter(getFilterText()));
        }
    }
}
