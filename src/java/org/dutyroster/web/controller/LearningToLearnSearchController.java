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
    private boolean renderDate;
    @Getter
    @Setter
    private boolean renderUserValue;
    UserServices us = new UserServices();
    StaffMemberServices sms = new StaffMemberServices();
    LearningtoLearnService learningService = new LearningtoLearnService();
    @Getter
    @Setter
    private List<LearningToLearnStaffDTO> staff;
    @Getter
    @Setter
    private LearningToLearnStaffDTO selectedDto;

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
        renderUserValue = false;
        renderDate = false;
    }

    public void processCriteriaValue() {
        if (renderDate) {
            System.out.println(filterDate);
            staff = learningService.getLearningStaff(staffMember, filterDate);
        } else if (renderUserValue) {
            System.out.println(userCriteriaValue);
            staff = learningService.getLearningStaffbyStaffId(staffMember, criteriaValue, userCriteriaValue);
        } else {
            System.out.println("Nothing is selcted");
        }
    }

    public void processFilterChange() {
        System.out.println(criteriaValue);
        if (criteriaValue.equals("Date")) {
            renderDate = true;
            userCriteriaValue = null;
            renderUserValue = false;
        } else if (!criteriaValue.equals("Select Criteria")) {
            System.out.println("No Select");
            renderDate = false;
            filterDate = null;
            renderUserValue = true;
        } else {
            renderDate = false;
            filterDate = null;
            userCriteriaValue = null;
            renderUserValue = false;
        }
    }

    public void check(LearningToLearnStaffDTO e) {
    }

    public String select() {
        //System.out.println("Select");
        for (LearningToLearnStaffDTO dTO : staff) {
            if (dTO.isChecked()) {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Map<String, Object> sessionMap = externalContext.getSessionMap();
                sessionMap.put(Constants.LEARNING_TO_LEARN_ID, dTO.getLearningStaff().getId().getLerId().getLerId());
                //preOrientationId = dTO.getPos().getId().getPreId().getPreId();
                System.out.println("Select Function : "+dTO.getLearningStaff().getId().getLerId().getLerId());
               // break;
            }
        }
        return "/pages/learning_to_learn/leaning_to_learn_user_participation?faces-redirect=true";
    }
    public String clearSessionValue(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.remove(Constants.LEARNING_TO_LEARN_ID);
        return "/pages/learning_to_learn/leaning_to_learn_user_participation?faces-redirect=true";
    }
}
