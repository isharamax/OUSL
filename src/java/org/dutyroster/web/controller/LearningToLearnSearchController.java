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
import org.dutyroster.web.dto.LearningToLearnStaffDTO;
import org.dutyroster.web.services.LearningtoLearnService;
import org.dutyroster.web.services.StaffMemberServices;
import org.dutyroster.web.services.UserServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "learningSearchBean", eager = true)
@ViewScoped
public class LearningToLearnSearchController implements Serializable {

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
    LearningtoLearnService learningService = new LearningtoLearnService();
    @Getter
    @Setter
    private List<LearningToLearnStaffDTO> staff;
    @Getter
    @Setter
    private LearningToLearnStaffDTO selectedDto;
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
        List<LearningToLearnStaffDTO> results;
        if (!disableDate) {
            System.out.println(filterDate);
            results = learningService.getLearningStaff(staffMember, filterDate);
            if (results!= null && !results.isEmpty()) {
            staff = results;
            renderSelectButton = true;                  
            }else{
                staff = new ArrayList<LearningToLearnStaffDTO>();
            }
        } else if (!disableUserValue) {
            System.out.println(userCriteriaValue);
            results =learningService.getLearningStaffbyStaffId(staffMember, criteriaValue, userCriteriaValue);
            if (results!= null && !results.isEmpty()) {
                staff = results;
                renderSelectButton = true;
            }else{
                staff = new ArrayList<LearningToLearnStaffDTO>();
            }
        } else {
            System.out.println("Nothing is selcted");
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

    public void check(LearningToLearnStaffDTO e) {
    }

    public String select() {
        //System.out.println("Select");
        boolean selected = false;
        if (staff==null) {
            return "selected";
        }
        for (LearningToLearnStaffDTO dTO : staff) {
            if (dTO.isChecked()) {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Map<String, Object> sessionMap = externalContext.getSessionMap();
                sessionMap.put(Constants.LEARNING_TO_LEARN_ID, dTO.getLearningStaff().getId().getLerId().getLerId());
                //preOrientationId = dTO.getPos().getId().getPreId().getPreId();
                System.out.println("Select Function : "+dTO.getLearningStaff().getId().getLerId().getLerId());
                selected = true;
                break;
            }
        }
        if (selected) {
        
        return "/pages/learning_to_learn/leaning_to_learn_user_participation?faces-redirect=true";
            
        }else{
            return "selected";
        }
    }
    public String clearSessionValue(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.remove(Constants.LEARNING_TO_LEARN_ID);
        return "/pages/learning_to_learn/leaning_to_learn_user_participation?faces-redirect=true";
    }
}
