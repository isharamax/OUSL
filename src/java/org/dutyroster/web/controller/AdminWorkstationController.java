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

import org.dutyroster.hibernate.database.Workstation;
import org.dutyroster.web.services.AdminWorkstationService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "workstation")
@SessionScoped
public class AdminWorkstationController implements Serializable {

    private Short workstationID;
    private String workstationName;
    private AdminWorkstationService adminWorkstationService = new AdminWorkstationService();
    private Workstation[] workstations = null;
    private Workstation selected;
    private String filterText;

    /** Creates a new instance of AdminWorkstationController */
    public AdminWorkstationController() {
          try {
            workstations = adminWorkstationService.searchAllWorkstations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminWorkstationService getAdminWorkstationService() {
        return adminWorkstationService;
    }

    public void setAdminWorkstationService(AdminWorkstationService adminWorkstationService) {
        this.adminWorkstationService = adminWorkstationService;
    }

    public Short getWorkstationID() {
        return workstationID;
    }

    public void setWorkstationID(Short workstationID) {
        this.workstationID = workstationID;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public Workstation[] getWorkstations() {
        return workstations;
    }

    public void setWorkstations(Workstation[] workstations) {
        this.workstations = workstations;
    }

    public Workstation getSelected() {
        return selected;
    }

    public void setSelected(Workstation selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void addWorkstationInfo() {
        String response = null;
        Workstation dtoWorkstation = new Workstation();
        dtoWorkstation.setWorkstationId(this.getWorkstationID());
        dtoWorkstation.setWorkstationName(this.getWorkstationName());
        this.setWorkstationID(null);
        this.setWorkstationName(null);

        if (dtoWorkstation.getWorkstationName() == null || dtoWorkstation.getWorkstationName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Workstation Name left blank. Please Enter Workstation Name."));
        } else {
            response = adminWorkstationService.addWorkstation(dtoWorkstation);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
                workstations = adminWorkstationService.searchAllWorkstations();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editWorkstationInfo() {
        String response = null;
        Workstation dtoWorkstation = new Workstation();
        dtoWorkstation.setWorkstationId(this.getWorkstationID());
        dtoWorkstation.setWorkstationName(this.getWorkstationName());
        this.setWorkstationID(null);
        this.setWorkstationName(null);

        if (dtoWorkstation.getWorkstationId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Workstation ID left blank. Please select a Workstation ID to update."));
        } else if (dtoWorkstation.getWorkstationName() == null || dtoWorkstation.getWorkstationName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Workstation Name left blank. Please Enter Workstation Name to update."));
        } else {
            response = adminWorkstationService.editWorkstation(dtoWorkstation);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                workstations = adminWorkstationService.searchAllWorkstations();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteWorkstationInfo() {
        String response = null;
        Workstation dtoWorkstation = new Workstation();
        dtoWorkstation.setWorkstationId(this.getWorkstationID());
        dtoWorkstation.setWorkstationName(this.getWorkstationName());
        this.setWorkstationID(null);
        this.setWorkstationName(null);

        if (dtoWorkstation.getWorkstationId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Workstation ID left blank. Please select a Workstation ID to delete."));
        } else {
            response = adminWorkstationService.deleteWorkstation(dtoWorkstation);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                workstations = adminWorkstationService.searchAllWorkstations();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newWorkstationInfo() {
        Short lastWorkstationId = 0;
        int newWorkstationId = 0;
        lastWorkstationId = adminWorkstationService.getLastWorkstationID();
        newWorkstationId = lastWorkstationId + 1;
        this.setWorkstationID((short) newWorkstationId);
        this.setWorkstationName(null);
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

    public void resetWorkstationInfo() {
        selected = null;
        this.setWorkstationID(null);
        this.setWorkstationName(null);
    }

    public void selectedWorkstation() {
        if (selected != null) {
            this.setWorkstationID(selected.getWorkstationId());
            this.setWorkstationName(selected.getWorkstationName());
            selected = null;
        }
    }

    public void searchWorkstationInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            workstations = adminWorkstationService.searchWorkstationsByFilter(filterText);
        }
    }
}
