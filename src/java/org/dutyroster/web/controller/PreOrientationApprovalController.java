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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
        System.out.println("K");
        if (viewList==null || viewList.isEmpty()) {
            return;// "selected";
        }
        for (PreOrientationApprovalDTO preOrientationApprovalDTO : viewList) {
                     
            if (preOrientationApprovalDTO.isChecked()) {
                pos.updatePreOrientation(preOrientationApprovalDTO.getPos(), "Approved");
                System.out.println("Approved");
            }
        }
        
        viewList = new ArrayList<PreOrientationApprovalDTO>();
        disableButtons= true;
        //return "selected";
    }

    public Object reject() {
         if (viewList==null || viewList.isEmpty()) {
            return "selected";
        }
        for (PreOrientationApprovalDTO preOrientationApprovalDTO : viewList) {
            if (preOrientationApprovalDTO.isChecked()) {
                pos.updatePreOrientation(preOrientationApprovalDTO.getPos(), "Rejected");
                //viewList.remove(preOrientationApprovalDTO);
            }
        }
        viewList = new ArrayList<PreOrientationApprovalDTO>();
        disableButtons = true;
        return "selected";
    }
    public void check() {
    }
}
