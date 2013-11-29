/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.Language;
import org.dutyroster.web.services.AdminLanguageServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "language")
//@SessionScoped
@RequestScoped
public class AdminLanguageController implements Serializable {

    private Short lanId;
    private String lanDesc;
    private AdminLanguageServices adminLanguageService = new AdminLanguageServices();
    private Language[] languages = null;
    private Language selected;
    private Short lIDSelected;
    private String filterText;

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public Short getlIDSelected() {
        return lIDSelected;
    }

    public void setlIDSelected(Short lIDSelected) {
        this.lIDSelected = lIDSelected;
    }

    public Language[] getLanguages() {
        return languages;
    }

    public void setLanguages(Language[] languages) {
        this.languages = languages;
    }

    public Language getSelected() {
        return selected;
    }

    public void setSelected(Language selected) {
        this.selected = selected;
    }

    public String getLanDesc() {
        return lanDesc;
    }

    public void setLanDesc(String lanDesc) {
        this.lanDesc = lanDesc;
    }

    public Short getLanId() {
        return lanId;
    }

    public void setLanId(Short lanId) {
        this.lanId = lanId;
    }

    public AdminLanguageServices getAdminLanguageService() {
        return adminLanguageService;
    }

    public void setAdminLanguageService(AdminLanguageServices adminLanguageService) {
        this.adminLanguageService = adminLanguageService;
    }

    /** Creates a new instance of AdminCenterController */
    public AdminLanguageController() {
        try {
            languages = adminLanguageService.searchAllLanguages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLanguageInfo() {
        String response = null;
        Language dtoLanguage = new Language();
        dtoLanguage.setLanId(this.getLanId());
        dtoLanguage.setLanDesc(this.getLanDesc());
        this.setLanId(null);
        this.setLanDesc(null);

        if (dtoLanguage.getLanId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Language ID left blank. Please select a Language ID."));
        } else if (dtoLanguage.getLanDesc() == null || dtoLanguage.getLanDesc().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Language Description left blank. Please Enter Language Description."));
        } else {
            response = adminLanguageService.addLanguage(dtoLanguage);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editLanguageInfo() {
        String response = null;
        Language dtoLanguage = new Language();
        dtoLanguage.setLanId(this.getLanId());
        dtoLanguage.setLanDesc(this.getLanDesc());
        this.setLanId(null);
        this.setLanDesc(null);

        if (dtoLanguage.getLanId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Language ID left blank. Please select a Language ID to update."));
        } else if (dtoLanguage.getLanDesc() == null || dtoLanguage.getLanDesc().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Language Description left blank. Please Enter Language Description to update."));
        } else {
            response = adminLanguageService.editLanguage(dtoLanguage);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                languages = adminLanguageService.searchAllLanguages();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteLanguageInfo() {
        String response = null;
        Language dtoLanguage = new Language();
        dtoLanguage.setLanId(this.getLanId());
        dtoLanguage.setLanDesc(this.getLanDesc());
        this.setLanId(null);
        this.setLanDesc(null);

        if (dtoLanguage.getLanId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Language ID left blank. Please select a Language ID to delete."));
        } else {
            response = adminLanguageService.deleteLanguage(dtoLanguage);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                languages = adminLanguageService.searchAllLanguages();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newLanguageInfo() {
        Short lastLanguageId = 0;
        int newLanguageId = 0;
        lastLanguageId = adminLanguageService.getLastLanguageID();
        newLanguageId = lastLanguageId + 1;
        this.setLanId((short) newLanguageId);
        this.setLanDesc(null);
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

    public void resetLanguageInfo() {
        selected = null;
        this.setLanId(null);
        this.setLanDesc(null);
    }

    public void selectedLanguage() {
        if (selected != null) {
            this.setLanId(selected.getLanId());
            this.setLanDesc(selected.getLanDesc());
            selected = null;
        }
    }

    public void searchLanguageInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            languages = adminLanguageService.searchLanguagesByFilter(filterText);
        }
    }
}
