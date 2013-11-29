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

import org.dutyroster.hibernate.database.RegistrationType;
import org.dutyroster.web.services.AdminRegTypeService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "regType")
@SessionScoped
public class AdminRegTypeConroller implements Serializable {

    private Short regTypeID;
    private String regTypeName;
    private AdminRegTypeService adminRegTypeService = new AdminRegTypeService();
    private RegistrationType[] regTypes = null;
    private RegistrationType selected;
    private String filterText;

    /** Creates a new instance of AdminRegTypeConroller */
    public AdminRegTypeConroller() {
        try {
            regTypes = adminRegTypeService.searchAllRegTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminRegTypeService getAdminRegTypeService() {
        return adminRegTypeService;
    }

    public void setAdminRegTypeService(AdminRegTypeService adminRegTypeService) {
        this.adminRegTypeService = adminRegTypeService;
    }

    public Short getRegTypeID() {
        return regTypeID;
    }

    public void setRegTypeID(Short regTypeID) {
        this.regTypeID = regTypeID;
    }

    public String getRegTypeName() {
        return regTypeName;
    }

    public void setRegTypeName(String regTypeName) {
        this.regTypeName = regTypeName;
    }

    public RegistrationType[] getRegTypes() {
        return regTypes;
    }

    public void setRegTypes(RegistrationType[] regTypes) {
        this.regTypes = regTypes;
    }

    public RegistrationType getSelected() {
        return selected;
    }

    public void setSelected(RegistrationType selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void addRegTypeInfo() {
        String response = null;
        RegistrationType dtoRegType = new RegistrationType();
        dtoRegType.setRegTypeId(this.getRegTypeID());
        dtoRegType.setRegTypeName(this.getRegTypeName());
        this.setRegTypeID(null);
        this.setRegTypeName(null);

        if (dtoRegType.getRegTypeName() == null || dtoRegType.getRegTypeName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Registration Type Name left blank. Please Enter Registration Type Name."));
        } else {
            response = adminRegTypeService.addRegType(dtoRegType);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }
    
    public void editRegTypeInfo() {
        String response = null;
        RegistrationType dtoRegType = new RegistrationType();
        dtoRegType.setRegTypeId(this.getRegTypeID());
        dtoRegType.setRegTypeName(this.getRegTypeName());
        this.setRegTypeID(null);
        this.setRegTypeName(null);

        if (dtoRegType.getRegTypeId()== (short)0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Registration Type ID left blank. Please select a Registration Type ID to update."));
        } else if (dtoRegType.getRegTypeName() == null || dtoRegType.getRegTypeName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Registration Type Name left blank. Please Enter Registration Type Name to update."));
        } else {
            response = adminRegTypeService.addRegType(dtoRegType);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }
    
    public void deleteRegTypeInfo() {
        String response = null;
        RegistrationType dtoRegType = new RegistrationType();
        dtoRegType.setRegTypeId(this.getRegTypeID());
        dtoRegType.setRegTypeName(this.getRegTypeName());
        this.setRegTypeID(null);
        this.setRegTypeName(null);

        if (dtoRegType.getRegTypeId()== (short)0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Registration Type ID left blank. Please select a Registration Type ID to delete."));
        } else {
            response = adminRegTypeService.addRegType(dtoRegType);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }
    
    public void newRegTypeInfo() {
        Short lastRegTypeId = 0;
        int newRegTypeId = 0;
        lastRegTypeId = adminRegTypeService.getLastRegTypeID();
        newRegTypeId = lastRegTypeId + 1;
        this.setRegTypeID((short) newRegTypeId);
        this.setRegTypeName(null);
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

    public void resetRegTypeInfo() {
        selected = null;
        this.setRegTypeID(null);
        this.setRegTypeName(null);
    }

    public void selectedRegType() {
        if (selected != null) {
            this.setRegTypeID(selected.getRegTypeId());
            this.setRegTypeName(selected.getRegTypeName());
            selected = null;
        }
    }

    public void searchRegTypeInfo() {
        if ( filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            regTypes = adminRegTypeService.searchRegTypesByFilter(filterText);
        }
    }
}
