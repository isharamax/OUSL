/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.Sessioninfo;
import org.dutyroster.web.services.AdminSessionServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "sessionDetails")
//@SessionScoped
@RequestScoped
public class AdminSessionController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short sessionId;
    private String sessionDesc;
    private Float fromTime;
    private Float toTime;
    private AdminSessionServices adminSessionService = new AdminSessionServices();
    private Sessioninfo[] sessioninfos = null;
    private Sessioninfo selected;
    private Short seIDSelected;
    private String filterText;

    public Sessioninfo getSelected() {
        return selected;
    }

    public void setSelected(Sessioninfo selected) {
        this.selected = selected;
    }

    public Sessioninfo[] getSessioninfos() {
        return sessioninfos;
    }

    public void setSessioninfos(Sessioninfo[] sessioninfos) {
        this.sessioninfos = sessioninfos;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public Short getSeIDSelected() {
        return seIDSelected;
    }

    public void setSeIDSelected(Short seIDSelected) {
        this.seIDSelected = seIDSelected;
    }

    public String getSessionDesc() {
        return sessionDesc;
    }

    public void setSessionDesc(String sessionDesc) {
        this.sessionDesc = sessionDesc;
    }

    public Short getSessionId() {
        return sessionId;
    }

    public void setSessionId(Short sessionId) {
        this.sessionId = sessionId;
    }

    public Float getFromTime() {
        return fromTime;
    }

    public void setFromTime(Float fromTime) {
        this.fromTime = fromTime;
    }

    public Float getToTime() {
        return toTime;
    }

    public void setToTime(Float toTime) {
        this.toTime = toTime;
    }

    public AdminSessionServices getAdminSessionService() {
        return adminSessionService;
    }

    public void setAdminSessionService(AdminSessionServices adminSessionService) {
        this.adminSessionService = adminSessionService;
    }

    public AdminSessionController() {
        try {
            sessioninfos = adminSessionService.searchAllSessioninfos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSessionInfo() {
        String response = null;
        Sessioninfo dtoSessioninfo = new Sessioninfo();
        dtoSessioninfo.setSessionId(this.getSessionId());
        dtoSessioninfo.setSessionDesc(this.getSessionDesc());
        dtoSessioninfo.setFromTime(this.getFromTime());
        dtoSessioninfo.setToTime(this.getToTime());
        this.setSessionId(null);
        this.setSessionDesc(null);
        this.setFromTime(null);
        this.setToTime(null);


        if (dtoSessioninfo.getSessionId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Session ID left blank. Please select a Session ID."));
        } else if (dtoSessioninfo.getSessionDesc() == null || dtoSessioninfo.getSessionDesc().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Session Description left blank. Please Enter Session Description."));
        } else if (dtoSessioninfo.getFromTime() == null || dtoSessioninfo.getFromTime().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "From Time left blank. Please Enter From Time."));
        } else if (dtoSessioninfo.getToTime() == null || dtoSessioninfo.getToTime().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "To Time left blank. Please Enter To Time to."));
        } else {
            response = adminSessionService.addSession(dtoSessioninfo);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editSessionInfo() {
        String response = null;
        Sessioninfo dtoSessioninfo = new Sessioninfo();
        dtoSessioninfo.setSessionId(this.getSessionId());
        dtoSessioninfo.setSessionDesc(this.getSessionDesc());
        dtoSessioninfo.setFromTime(this.getFromTime());
        dtoSessioninfo.setToTime(this.getToTime());

        this.setSessionId(null);
        this.setSessionDesc(null);
        this.setFromTime(null);
        this.setToTime(null);

        if (dtoSessioninfo.getSessionId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Session ID left blank. Please select a Session ID to update."));
        } else if (dtoSessioninfo.getSessionDesc() == null || dtoSessioninfo.getSessionDesc().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Session Description left blank. Please Enter Session Description to update."));
        } else if (dtoSessioninfo.getFromTime() == null || dtoSessioninfo.getFromTime().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "From Time left blank. Please Enter From Time to update."));
        } else if (dtoSessioninfo.getToTime() == null || dtoSessioninfo.getToTime().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "To Time left blank. Please Enter To Time to update."));
        } else {
            response = adminSessionService.editSessioninfos(dtoSessioninfo);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                sessioninfos = adminSessionService.searchAllSessioninfos();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteSessionInfo() {
        String response = null;
        Sessioninfo dtoSessioninfo = new Sessioninfo();
        dtoSessioninfo.setSessionId(this.getSessionId());
        dtoSessioninfo.setSessionDesc(this.getSessionDesc());
        dtoSessioninfo.setFromTime(this.getFromTime());
        dtoSessioninfo.setToTime(this.getToTime());
        this.setSessionId(null);
        this.setSessionDesc(null);
        this.setFromTime(null);
        this.setToTime(null);

        if (dtoSessioninfo.getSessionId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Session ID left blank. Please select a Session ID to delete."));
        } else {
            response = adminSessionService.deleteSessioninfos(dtoSessioninfo);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                sessioninfos = adminSessionService.searchAllSessioninfos();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newSessionInfo() {
        Short lastSessionId = 0;
        int newSessionId = 0;
        lastSessionId = adminSessionService.getLastSessionId();
        newSessionId = lastSessionId + 1;
        this.setSessionId((short) newSessionId);
        this.setSessionDesc(null);
        this.setFromTime(null);
        this.setToTime(null);
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

    public void resetSessionInfo() {
        selected = null;
        this.setSessionId(null);
        this.setSessionDesc(null);
        this.setFromTime(null);
        this.setToTime(null);
    }

    public void selectedSessionInfo() {
        if (selected != null) {
            this.setSessionId(selected.getSessionId());
            this.setSessionDesc(selected.getSessionDesc());
            this.setFromTime(selected.getFromTime());
            this.setToTime(selected.getToTime());

            selected = null;
        }
    }

    public void searchSessionInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            sessioninfos = adminSessionService.searchSessionsByFilter(filterText);
        }
    }
}
