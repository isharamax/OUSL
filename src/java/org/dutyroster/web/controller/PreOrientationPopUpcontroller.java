/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.hibernate.database.UserAccount;
import org.dutyroster.web.dto.PreOrienatationStaffDTO;
import org.dutyroster.web.services.PreOrientationService;
import org.dutyroster.web.services.StaffMemberServices;
import org.dutyroster.web.services.UserServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "prePopUp", eager = true)
@ViewScoped
public class PreOrientationPopUpcontroller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private StaffMember staffMember;
    @Getter
    @Setter
    private List<String> filterCriteria;
    @Getter
    @Setter
    private String criteriaValue;
    @Getter
    @Setter
    private String userCriteriaValue;
    @Getter
    @Setter
    private Date filterDate;
    @Getter
    @Setter
    private boolean disableDate;
    @Getter
    @Setter
    private boolean disableUserValue;
    UserServices us = new UserServices();
    StaffMemberServices sms = new StaffMemberServices();
    PreOrientationService pos = new PreOrientationService();
    @Getter
    @Setter
    private List<PreOrienatationStaffDTO> staff;
    @Getter
    @Setter
    private PreOrienatationStaffDTO dTO;
    @Getter
    @Setter
    private Short preOrientationId;
    @Getter@Setter
    private boolean renderSelectButton;
    @PostConstruct
    public void init() {
        filterCriteria = new ArrayList<String>();
        filterCriteria.add("Program");
        filterCriteria.add("Center");
        filterCriteria.add("Session");
        filterCriteria.add("Date");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String userName = (String) sessionMap.get(Constants.SESSION_USER);
        UserAccount account = us.getUserByUserName(userName);
        account.setPassword(null);
        Short staffNumber = account.getStaffId();
        staffMember = sms.getStaffMemberById(staffNumber);
        System.out.println(staffMember.getSurname());
        disableUserValue = true;
        disableDate = true;
        renderSelectButton = false;
    }

    public void processCriteriaValue() {
        List<PreOrienatationStaffDTO> results;
        if (!disableDate) {
            System.out.println(filterDate);
            results = pos.getPreorientationStaff(staffMember, filterDate);
            if (results!= null && !results.isEmpty()) {
            staff = results;
            renderSelectButton = true;                  
            }else{
                staff = new ArrayList<PreOrienatationStaffDTO>();
            }
        } else if (!disableUserValue) {
            System.out.println(userCriteriaValue);
            results = pos.getPreorientationStaffbyStaffId(staffMember, criteriaValue, userCriteriaValue);
            if (results!= null && !results.isEmpty()) {
                staff = results;
                renderSelectButton = true;
            }else{
                staff = new ArrayList<PreOrienatationStaffDTO>();
            }
        } else {
            System.out.println("Nothing is selected");
        }
    }

    public void processFilterChange() {
        System.out.println(criteriaValue);
        if (criteriaValue.equals("Date")) {
            disableDate = false;
            userCriteriaValue = null;
            disableUserValue = true;
        } else if (!criteriaValue.equals("Select Criteria")) {
            System.out.println("No Select");
            disableDate = true;
            filterDate = null;
            disableUserValue = false;
        } else {
            disableDate = true;
            filterDate = null;
            userCriteriaValue = null;
            disableUserValue = true;
        }
    }

    public void check(PreOrienatationStaffDTO e) {
        //System.out.println("one "+e.getPos().getId().getPreId().getPreId());
        preOrientationId =  e.getPos().getId().getPreId().getPreId();
    
    
    }

    public String what() {
        System.out.println("Reder What");
        return "";
    }

    public String select() {
        //System.out.println("Select");
        boolean selected = false;
        if (staff==null) {
            return "selected";
        }
        for (PreOrienatationStaffDTO dTO : staff) {
            if (dTO.isChecked()) {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Map<String, Object> sessionMap = externalContext.getSessionMap();
                sessionMap.put(Constants.PREORIENTATION_ID, dTO.getPos().getId().getPreId().getPreId());
                //preOrientationId = dTO.getPos().getId().getPreId().getPreId();
                System.out.println("Select Function : "+dTO.getPos().getId().getPreId().getPreId());
                selected = true;
                break;
            }
        }
        if (selected) {
        return "/pages/pre_orientation/pre_orientation_details?faces-redirect=true";
            
        }else{
            return "selected";
        }
    }
    public String clearSessionValue(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.remove(Constants.PREORIENTATION_ID);
        return "/pages/pre_orientation/pre_orientation_details?faces-redirect=true";
    }
}
