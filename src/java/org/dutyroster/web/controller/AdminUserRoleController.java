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

import org.dutyroster.hibernate.database.UserRole;
import org.dutyroster.web.services.AdminUserRoleService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "userRole")
@SessionScoped
public class AdminUserRoleController implements Serializable {

    private Short userRoleID;
    private String userRoleName;
    private AdminUserRoleService adminUserRoleService = new AdminUserRoleService();
    private UserRole[] userRoles = null;
    private UserRole selected;
    private String filterText;

    /** Creates a new instance of AdminUserRoleController */
    public AdminUserRoleController() {
          try {
            userRoles = adminUserRoleService.searchAllUserRoles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminUserRoleService getAdminUserRoleService() {
        return adminUserRoleService;
    }

    public void setAdminUserRoleService(AdminUserRoleService adminUserRoleService) {
        this.adminUserRoleService = adminUserRoleService;
    }

    public Short getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(Short userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public UserRole[] getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRole[] UserRoles) {
        this.userRoles = UserRoles;
    }

    public UserRole getSelected() {
        return selected;
    }

    public void setSelected(UserRole selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public void addUserRoleInfo() {
        String response = null;
        UserRole dtoUserRole = new UserRole();
        dtoUserRole.setRoleId(this.getUserRoleID());
        dtoUserRole.setRoleName(this.getUserRoleName());
        this.setUserRoleID(null);
        this.setUserRoleName(null);

        if (dtoUserRole.getRoleName() == null || dtoUserRole.getRoleName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Role Name left blank. Please Enter Role Name."));
        } else {
            response = adminUserRoleService.addUserRole(dtoUserRole);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
                userRoles = adminUserRoleService.searchAllUserRoles();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editUserRoleInfo() {
        String response = null;
        UserRole dtoUserRole = new UserRole();
        dtoUserRole.setRoleId(this.getUserRoleID());
        dtoUserRole.setRoleName(this.getUserRoleName());
        this.setUserRoleID(null);
        this.setUserRoleName(null);

        if (dtoUserRole.getRoleId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Role ID left blank. Please select a Role ID to update."));
        } else if (dtoUserRole.getRoleName() == null || dtoUserRole.getRoleName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Role Name left blank. Please Enter Role Name to update."));
        } else {
            response = adminUserRoleService.editUserRole(dtoUserRole);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                userRoles = adminUserRoleService.searchAllUserRoles();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteUserRoleInfo() {
        String response = null;
        UserRole dtoUserRole = new UserRole();
        dtoUserRole.setRoleId(this.getUserRoleID());
        dtoUserRole.setRoleName(this.getUserRoleName());
        this.setUserRoleID(null);
        this.setUserRoleName(null);

        if (dtoUserRole.getRoleId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Role ID left blank. Please select a Role ID to delete."));
        } else {
            response = adminUserRoleService.deleteUserRole(dtoUserRole);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                userRoles = adminUserRoleService.searchAllUserRoles();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newUserRoleInfo() {
        Short lastUserRoleId = 0;
        int newUserRoleId = 0;
        lastUserRoleId = adminUserRoleService.getLastUserRoleID();
        newUserRoleId = lastUserRoleId + 1;
        this.setUserRoleID((short) newUserRoleId);
        this.setUserRoleName(null);
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

    public void resetUserRoleInfo() {
        selected = null;
        this.setUserRoleID(null);
        this.setUserRoleName(null);
    }

    public void selectedUserRole() {
        if (selected != null) {
            this.setUserRoleID(selected.getRoleId());
            this.setUserRoleName(selected.getRoleName());
            selected = null;
        }
    }

    public void searchUserRoleInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            userRoles = adminUserRoleService.searchUserRolesByFilter(filterText);
        }
    }
}
