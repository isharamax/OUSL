/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.dutyroster.web.dto.PreOrientationApprovalDTO;
import org.dutyroster.web.services.PreOrientationService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "preApproval", eager = true)
@ViewScoped
public class PreOrientationApprovalController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private Date fromDate = new Date();
    @Getter
    @Setter
    private Date toDate = new Date();
    @Getter
    @Setter
    private List<PreOrientationApprovalDTO> viewList;
    @Getter
    @Setter
    private boolean disableButtons;
   
    PreOrientationService pos = new PreOrientationService();

    @PostConstruct
    public void init(){
        disableButtons= true;
    }
    public void load() {
        System.out.println("K");
        List<PreOrientationApprovalDTO> results = pos.getUnApprovedPreorienatations(fromDate, toDate);
        if (results != null && !results.isEmpty()) {
            viewList = results;
            disableButtons = false;
        }
    }

    public void approve() {
        
        int approved = 0;
        if (viewList==null || viewList.isEmpty()) {
            return;// "selected";
        }
        for (PreOrientationApprovalDTO preOrientationApprovalDTO : viewList) {
                     
            if (preOrientationApprovalDTO.isChecked()) {
                if(pos.updatePreOrientation(preOrientationApprovalDTO.getPos(), "Approved")){
                System.out.println("Approved");
                    approved++;
                }else{
                    
                }
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        if (approved==0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Failed", "No Records were approved!!"));         
            
        } else if(approved>0) {
            String message = approved==1 ? "record was ": "records were ";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", String.format("%d %s approved!!",approved,message)));
        }
        viewList = new ArrayList<PreOrientationApprovalDTO>();
        disableButtons= true;
        //return "selected";
    }

    public Object reject() {
         if (viewList==null || viewList.isEmpty()) {
            return "selected";
        }
         int rejected = 0;
        for (PreOrientationApprovalDTO preOrientationApprovalDTO : viewList) {
            if (preOrientationApprovalDTO.isChecked()) {
                if(pos.updatePreOrientation(preOrientationApprovalDTO.getPos(), "Rejected")){
                  rejected++;   
                }
                //viewList.remove(preOrientationApprovalDTO);
            }
        }
        viewList = new ArrayList<PreOrientationApprovalDTO>();
        disableButtons = true;
        FacesContext context = FacesContext.getCurrentInstance();
        if (rejected==0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Failed", "No Records were rejected!!"));         
            
        } else if(rejected>0) {
            String message = rejected==1 ? "record was ": "records were ";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", String.format("%d %s rejected!!",rejected,message)));
        }
        return "selected";
    }
    public void check() {
    }
}
