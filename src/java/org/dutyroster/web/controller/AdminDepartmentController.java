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

import org.dutyroster.hibernate.database.Department;
import org.dutyroster.web.services.AdminDepartmentServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "department")
//@SessionScoped
@RequestScoped
public class AdminDepartmentController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short deptId;
    private String deptName;
    private String deptHead;
    private AdminDepartmentServices adminDepartmentService = new AdminDepartmentServices();
    private Department[] departments = null;
    private Department selected;
    private Short deptIDSelected;

    public Department[] getDepartments() {
        return departments;
    }

    public void setDepartments(Department[] departments) {
        this.departments = departments;
    }

    public Short getDeptIDSelected() {
        return deptIDSelected;
    }

    public void setDeptIDSelected(Short deptIDSelected) {
        this.deptIDSelected = deptIDSelected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public Department getSelected() {
        return selected;
    }

    public void setSelected(Department selected) {
        this.selected = selected;
    }
    private String filterText;

    public String getDeptHead() {
        return deptHead;
    }

    public void setDeptHead(String deptHead) {
        this.deptHead = deptHead;
    }

    public Short getDeptId() {
        return deptId;
    }

    public void setDeptId(Short deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public AdminDepartmentServices getAdminDepartmentService() {
        return adminDepartmentService;
    }

    public void setAdminDepartmentServvice(AdminDepartmentServices adminDepartmentService) {
        this.adminDepartmentService = adminDepartmentService;
    }

    public AdminDepartmentController() {
        try {
            departments = adminDepartmentService.searchAllDepartments();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDepartmentInfo() {
        String response = null;
        Department dtoDepartment = new Department();
        dtoDepartment.setDeptId(this.getDeptId());
        dtoDepartment.setDeptName(this.getDeptName());
        dtoDepartment.setDeptHead(this.getDeptHead());
        this.setDeptId(null);
        this.setDeptName(null);
        this.setDeptHead(null);

        if (dtoDepartment.getDeptId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Department ID left blank. Please select a Department ID."));
        } else if (dtoDepartment.getDeptName() == null || dtoDepartment.getDeptName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Department Name left blank. Please Enter Department Name."));
        } else {
            response = adminDepartmentService.addDepartment(dtoDepartment);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void editDepartmentInfo() {
        String response = null;
        Department dtoDepartment = new Department();
        dtoDepartment.setDeptId(this.getDeptId());
        dtoDepartment.setDeptName(this.getDeptName());
        dtoDepartment.setDeptHead(this.getDeptHead());
        this.setDeptId(null);
        this.setDeptName(null);
        this.setDeptHead(null);


        if (dtoDepartment.getDeptId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Department ID left blank. Please select a Department ID to update."));
        } else if (dtoDepartment.getDeptName() == null || dtoDepartment.getDeptName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Department Name left blank. Please Enter Department Name to update."));
        } else {
            response = adminDepartmentService.editDepartment(dtoDepartment);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                departments = adminDepartmentService.searchAllDepartments();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteDepartmentInfo() {
        String response = null;
        Department dtoDepartment = new Department();
        dtoDepartment.setDeptId(this.getDeptId());
        dtoDepartment.setDeptName(this.getDeptName());
        dtoDepartment.setDeptHead(this.getDeptHead());
        this.setDeptId(null);
        this.setDeptName(null);
        this.setDeptHead(null);

        if (dtoDepartment.getDeptId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Department ID left blank. Please select a Department ID to delete."));
        } else {
            response = adminDepartmentService.deleteDepartment(dtoDepartment);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                departments = adminDepartmentService.searchAllDepartments();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newDepartmentInfo() {
        Short lastDepartmentId = 0;
        int newDepartmentId = 0;
        lastDepartmentId = adminDepartmentService.getLastDepartmentID();
        newDepartmentId = lastDepartmentId + 1;
        this.setDeptId((short) newDepartmentId);
        this.setDeptName(null);
        this.setDeptHead(null);
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

    public void resetDepartmentInfo() {
        selected = null;
        this.setDeptId(null);
        this.setDeptName(null);
        this.setDeptHead(null);

    }

    public void selectedDepartment() {
        if (selected != null) {
            this.setDeptId(selected.getDeptId());
            this.setDeptName(selected.getDeptName());
            this.setDeptHead(selected.getDeptHead());
            selected = null;
        }
    }

    public void searchDepartmentInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            departments = adminDepartmentService.searchDepartmentsByFilter(filterText);
        }
    }
}
