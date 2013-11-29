/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.web.services.AdminStaffMemberServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */

@ManagedBean(name = "staffmem")
@SessionScoped
public class AdminStaffMemberConntroller implements Serializable {
    
     private Short staffId;
     private String department;
     private String userRole;
     private String staffCategory;
     private String center;
     private String initials;
     private String surname;
     private Integer officePhone;
     private Integer mobile;
     private String officeEmail;
     
     
     

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public Integer getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(Integer officePhone) {
        this.officePhone = officePhone;
    }

    public String getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory) {
        this.staffCategory = staffCategory;
    }

    public Short getStaffId() {
        return staffId;
    }

    public void setStaffId(Short staffId) {
        this.staffId = staffId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
     
    
    public String newMember() throws Exception {
    
        return null;
    }
    
    public String delete() throws Exception {
    
        return null;
    }
    
    public String execute() throws Exception {
    
        return null;
    }
    
     public String edit() throws Exception {
    
        return null;
    }   
     
    
}
