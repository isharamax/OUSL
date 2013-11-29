/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.DayType;
import org.dutyroster.web.services.AdminDayTypeServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "dayType")
@RequestScoped
//@SessionScoped
public class AdminDayTypeController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short dayTypeId;
    private String dayTypeName;
    private AdminDayTypeServices adminDayTypeService = new AdminDayTypeServices();
    private DayType[] dayTypes = null;
    private DayType selected;
    private Short dIDSelected;
    private String filterText;

    public Short getdIDSelected() {
        return dIDSelected;
    }

    public void setdIDSelected(Short dIDSelected) {
        this.dIDSelected = dIDSelected;
    }

    public DayType[] getDayTypes() {
        return dayTypes;
    }

    public void setDayTypes(DayType[] dayTypes) {
        this.dayTypes = dayTypes;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public DayType getSelected() {
        return selected;
    }

    public void setSelected(DayType selected) {
        this.selected = selected;
    }

    public Short getDayTypeId() {
        return dayTypeId;
    }

    public void setDayTypeId(Short dayTypeId) {
        this.dayTypeId = dayTypeId;
    }

    public String getDayTypeName() {
        return dayTypeName;
    }

    public void setDayTypeName(String dayTypeName) {
        this.dayTypeName = dayTypeName;
    }

    public AdminDayTypeServices getAdminDayTypeServices() {
        return adminDayTypeService;
    }

    public void setAdminDayTypeServices(AdminDayTypeServices adminDayTypeService) {
        this.adminDayTypeService = adminDayTypeService;
    }

    public AdminDayTypeController() {
        try {
            dayTypes = adminDayTypeService.searchAllDayTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDayTypeInfo() {
        String response = null;
        DayType dtoDayType = new DayType();
        dtoDayType.setDayTypeId(this.getDayTypeId());
        dtoDayType.setDayTypeName(this.getDayTypeName());
        this.setDayTypeId(null);
        this.setDayTypeName(null);

        if (dtoDayType.getDayTypeId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Day Type ID left blank. Please select a Day Type ID to update."));
        } else if (dtoDayType.getDayTypeName() == null || dtoDayType.getDayTypeName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Day Type Name left blank. Please Enter Day Type Name to update."));
        } else {
            response = adminDayTypeService.addDayType(dtoDayType);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void newDayTypeInfo() {
        Short lastDayTypeId = 0;
        int newDayTypeId = 0;
        lastDayTypeId = adminDayTypeService.getLastDayTypeId();
        newDayTypeId = lastDayTypeId + 1;
        this.setDayTypeId((short) newDayTypeId);
        this.setDayTypeName(null);
    }

    public void deleteDayTypeInfo() {

        String response = null;
        DayType dtoDayType = new DayType();
        dtoDayType.setDayTypeId(this.getDayTypeId());
        dtoDayType.setDayTypeName(this.getDayTypeName());
        this.setDayTypeId(null);
        this.setDayTypeName(null);

        if (dtoDayType.getDayTypeId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Day Type ID left blank. Please select a Day Type ID to delete."));
        } else {
            response = adminDayTypeService.deleteDayType(dtoDayType);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                dayTypes = adminDayTypeService.searchAllDayTypes();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void editDayTypeInfo() {

        String response = null;
        DayType dtoDayType = new DayType();
        dtoDayType.setDayTypeId(this.getDayTypeId());
        dtoDayType.setDayTypeName(this.getDayTypeName());
        this.setDayTypeId(null);
        this.setDayTypeName(null);

        if (dtoDayType.getDayTypeId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Day Type ID left blank. Please select a Day Type ID to update."));
        } else if (dtoDayType.getDayTypeName() == null || dtoDayType.getDayTypeName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Day Type Name left blank. Please Enter Day Type Name to update."));
        } else {
            response = adminDayTypeService.editDayType(dtoDayType);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                dayTypes = adminDayTypeService.searchAllDayTypes();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
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

    public void resetDayTypeInfo() {
        selected = null;
        this.setDayTypeId(null);
        this.setDayTypeName(null);
    }

    public void selectedDayType() {
        if (selected != null) {
            this.setDayTypeId(selected.getDayTypeId());
            this.setDayTypeName(selected.getDayTypeName());
            selected = null;
        }
    }

    public void searchDayTypeInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            dayTypes = adminDayTypeService.searchDayTypesByFilter(filterText);
        }
    }
}
