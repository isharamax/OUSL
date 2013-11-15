/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import org.dutyroster.web.dto.PreOrientationApprovalDTO;
import org.dutyroster.web.services.PreOrientationService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "preApproval", eager = true)
@SessionScoped
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
    PreOrientationService pos = new PreOrientationService();

    public void load() {
        viewList = pos.getUnApprovedPreorienatations(fromDate, toDate);
    }

    public Object approve() {
        if (viewList==null || viewList.isEmpty()) {
            return null;
        }
        for (PreOrientationApprovalDTO preOrientationApprovalDTO : viewList) {
            
                    
            if (preOrientationApprovalDTO.isChecked()) {
                pos.updatePreOrientation(preOrientationApprovalDTO.getPos(), "Approved");
            }
        }
        
        viewList = new ArrayList<PreOrientationApprovalDTO>();
        return "selected";
    }

    public Object reject() {
         if (viewList==null || viewList.isEmpty()) {
            return null;
        }
        for (PreOrientationApprovalDTO preOrientationApprovalDTO : viewList) {
            if (preOrientationApprovalDTO.isChecked()) {
                pos.updatePreOrientation(preOrientationApprovalDTO.getPos(), "Rejected");
                //viewList.remove(preOrientationApprovalDTO);
            }
        }
        viewList = new ArrayList<PreOrientationApprovalDTO>();
        return "selected";
    }

    public void check() {
    }
}
