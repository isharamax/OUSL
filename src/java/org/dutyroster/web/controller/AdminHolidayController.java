/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.Holiday;
import org.dutyroster.web.services.AdminHolidayService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "holiday")
@SessionScoped
public class AdminHolidayController implements Serializable {

    private Short holidayID;
    private Date holidayDate;
    private String holidayType;
    private AdminHolidayService adminHolidayService = new AdminHolidayService();
    private Holiday[] holidays = null;
    private Holiday selected;
    private String filterText;

    /** Creates a new instance of AdminHolidayController */
    public AdminHolidayController() {
        try {
            holidays = adminHolidayService.searchAllHolidays();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminHolidayService getAdminHolidayService() {
        return adminHolidayService;
    }

    public void setAdminHolidayService(AdminHolidayService adminHolidayService) {
        this.adminHolidayService = adminHolidayService;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Short getHolidayID() {
        return holidayID;
    }

    public void setHolidayID(Short holidayID) {
        this.holidayID = holidayID;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public Holiday[] getHolidays() {
        return holidays;
    }

    public void setHolidays(Holiday[] holidays) {
        this.holidays = holidays;
    }

    public Holiday getSelected() {
        return selected;
    }

    public void setSelected(Holiday selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void addHolidayInfo() {
        String response = null;
        Holiday dtoHoliday = new Holiday();
        dtoHoliday.setHolidayId(this.getHolidayID());
        dtoHoliday.setHoliday(this.getHolidayDate());
        dtoHoliday.setHolidayType(this.getHolidayType());
        this.setHolidayID(null);
        this.setHolidayDate(null);
        this.setHolidayType(null);

        if (dtoHoliday.getHoliday()== null || dtoHoliday.getHoliday().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Holiday Date left blank. Please select a Date."));
        } else if (dtoHoliday.getHolidayType() == null || dtoHoliday.getHolidayType().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Holiday Type left blank. Please Enter Holiday Type."));
        } else {
            response = adminHolidayService.addHoliday(dtoHoliday);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
                setHolidays(adminHolidayService.searchAllHolidays());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editHolidayInfo() {
        String response = null;
        Holiday dtoHoliday = new Holiday();
        dtoHoliday.setHolidayId(this.getHolidayID());
        dtoHoliday.setHoliday(this.getHolidayDate());
        dtoHoliday.setHolidayType(this.getHolidayType());
        this.setHolidayID(null);
        this.setHolidayDate(null);
        this.setHolidayType(null);

        if (dtoHoliday.getHolidayId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Holiday ID left blank. Please select a Holiday ID to update."));
        } else if (dtoHoliday.getHoliday()== null || dtoHoliday.getHoliday().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Holiday Date left blank. Please select a Date to update."));
        } else if (dtoHoliday.getHolidayType() == null || dtoHoliday.getHolidayType().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Holiday Type left blank. Please Enter Holiday Type to update."));
        } else {
            response = adminHolidayService.editHoliday(dtoHoliday);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                setHolidays(adminHolidayService.searchAllHolidays());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteHolidayInfo() {
        String response = null;
        Holiday dtoHoliday = new Holiday();
        dtoHoliday.setHolidayId(this.getHolidayID());
        dtoHoliday.setHoliday(this.getHolidayDate());
        dtoHoliday.setHolidayType(this.getHolidayType());
        this.setHolidayID(null);
        this.setHolidayDate(null);
        this.setHolidayType(null);

        if (dtoHoliday.getHolidayId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Holiday ID left blank. Please select a Holiday ID to delete."));
        } else {
            response = adminHolidayService.deleteHoliday(dtoHoliday);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                setHolidays(adminHolidayService.searchAllHolidays());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newHolidayInfo() {
        Short lastHolidayId = 0;
        int newHolidayId = 0;
        lastHolidayId = adminHolidayService.getLastHolidayID();
        newHolidayId = lastHolidayId + 1;
        this.setHolidayID((short) newHolidayId);
        this.setHolidayDate(null);
        this.setHolidayType(null);
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

    public void resetHolidayInfo() {
        selected = null;;
        this.setHolidayID(null);
        this.setHolidayDate(null);
        this.setHolidayType(null);
    }

    public void selectedHoliday() {
        if (selected != null) {
            this.setHolidayID(getSelected().getHolidayId());
            this.setHolidayDate(getSelected().getHoliday());
            this.setHolidayType(getSelected().getHolidayType());
            setSelected(null);
        }
    }

    public void searchHolidayInfo() {
        if (getFilterText() == null && getFilterText().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            setHolidays(adminHolidayService.searchHolidaysByFilter(getFilterText()));
        }
    }
}
