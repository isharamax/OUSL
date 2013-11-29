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

import org.dutyroster.hibernate.database.StaffCategory;
import org.dutyroster.web.services.AdminStaffCatService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "staffCategory")
@SessionScoped
public class AdminStaffCatController implements Serializable {

    private Short staffCatID;
    private String staffCatName;
    private AdminStaffCatService adminStaffCatService = new AdminStaffCatService();
    private StaffCategory[] staffCats = null;
    private StaffCategory selected;
    private String filterText;

    /** Creates a new instance of AdminStaffCatController */
    public AdminStaffCatController() {
                try {
            staffCats = adminStaffCatService.searchAllStaffCats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminStaffCatService getAdminStaffCatService() {
        return adminStaffCatService;
    }

    public void setAdminStaffCatService(AdminStaffCatService adminStaffCatService) {
        this.adminStaffCatService = adminStaffCatService;
    }

    public Short getStaffCatID() {
        return staffCatID;
    }

    public void setStaffCatID(Short staffCatID) {
        this.staffCatID = staffCatID;
    }

    public String getStaffCatName() {
        return staffCatName;
    }

    public void setStaffCatName(String staffCatName) {
        this.staffCatName = staffCatName;
    }  
    
    public StaffCategory[] getStaffCats() {
        return staffCats;
    }

    public void setStaffCats(StaffCategory[] staffCats) {
        this.staffCats = staffCats;
    }

    public StaffCategory getSelected() {
        return selected;
    }

    public void setSelected(StaffCategory selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void addStaffCatInfo() {
        String response = null;
        StaffCategory dtoStaffCat = new StaffCategory();
        dtoStaffCat.setStaffCatId(this.getStaffCatID());
        dtoStaffCat.setStaffCatName(this.getStaffCatName());
        this.setStaffCatID(null);
        this.setStaffCatName(null);

        if (dtoStaffCat.getStaffCatName() == null || dtoStaffCat.getStaffCatName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Staff Category Name left blank. Please Enter Staff Category Name."));
        } else {
            response = adminStaffCatService.addStaffCat(dtoStaffCat);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
                setStaffCats(adminStaffCatService.searchAllStaffCats());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editStaffCatInfo() {
        String response = null;
        StaffCategory dtoStaffCat = new StaffCategory();
        dtoStaffCat.setStaffCatId(this.getStaffCatID());
        dtoStaffCat.setStaffCatName(this.getStaffCatName());
        this.setStaffCatID(null);
        this.setStaffCatName(null);

        if (dtoStaffCat.getStaffCatId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Staff Category ID left blank. Please select a Staff Category ID to update."));
        } else if (dtoStaffCat.getStaffCatName() == null || dtoStaffCat.getStaffCatName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Staff Category Name left blank. Please Enter Staff Category Name to update."));
        } else {
            response = adminStaffCatService.editStaffCat(dtoStaffCat);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                setStaffCats(adminStaffCatService.searchAllStaffCats());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteStaffCatInfo() {
        String response = null;
        StaffCategory dtoStaffCat = new StaffCategory();
        dtoStaffCat.setStaffCatId(this.getStaffCatID());
        dtoStaffCat.setStaffCatName(this.getStaffCatName());
        this.setStaffCatID(null);
        this.setStaffCatName(null);

        if (dtoStaffCat.getStaffCatId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Staff Category ID left blank. Please select a Staff Category ID to delete."));
        } else {
            response = adminStaffCatService.deleteStaffCat(dtoStaffCat);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                setStaffCats(adminStaffCatService.searchAllStaffCats());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newStaffCatInfo() {
        Short lastStaffCatId = 0;
        int newStaffCatId = 0;
        lastStaffCatId = adminStaffCatService.getLastStaffCatID();
        newStaffCatId = lastStaffCatId + 1;
        this.setStaffCatID((short) newStaffCatId);
        this.setStaffCatName(null);
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

    public void resetStaffCatInfo() {
        selected = null;
        this.setStaffCatID(null);
        this.setStaffCatName(null);
    }

    public void selectedStaffCat() {
        if (selected != null) {
            this.setStaffCatID(getSelected().getStaffCatId());
            this.setStaffCatName(getSelected().getStaffCatName());
            setSelected(null);
        }
    }

    public void searchStaffCatInfo() {
        if (getFilterText() == null && getFilterText().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            setStaffCats(adminStaffCatService.searchStaffCatsByFilter(getFilterText()));
        }
    }
}
