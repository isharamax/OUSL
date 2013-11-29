/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.WorkType;
import org.dutyroster.web.services.AdminWorkTypeServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */

@ManagedBean(name = "workType")
@SessionScoped
public class AdminWorkTypeController implements Serializable{
     private Short workTypeId;
     private String workTypeName;
     private Float workPercentage;

    
     
      private AdminWorkTypeServices adminWorkTypeService = new AdminWorkTypeServices();
      
      

    

    public Short getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Short workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }
    
    public Float getWorkPercentage() {
        return workPercentage;
    }

    public void setWorkPercentage(Float workPercentage) {
        this.workPercentage = workPercentage;
    }
     
     
    public AdminWorkTypeServices getAdminWorkTypeService() {
        return adminWorkTypeService;
    }

    public void setAdminWorkTypeService(AdminWorkTypeServices adminWorkTypeService) {
        this.adminWorkTypeService = adminWorkTypeService;
    }
    public AdminWorkTypeController() {
        
    }
    
    public void addWorkTypeInfo() {
        String response = null;
        WorkType dtoWorkType = new WorkType();
        dtoWorkType.setWorkTypeId(this.getWorkTypeId());
        dtoWorkType .setWorkTypeName(this.getWorkTypeName());
        dtoWorkType .setWorkPercentage(this.getWorkPercentage());
        this.setWorkTypeId(null);
        this.setWorkTypeName(null);
        this.setWorkPercentage(null);
        

        response = adminWorkTypeService.addWorkType(dtoWorkType);
        if (response.equalsIgnoreCase(Constants.SUCCESS)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
        }
    }
    
    public String newCenterInfo() throws Exception {
    
        return null;
    }
    
    public String deleteCenterInfo() throws Exception {
    
        return null;
    }
    
    public String updateCenterInfo() throws Exception {
    
        return null;
    }

}
