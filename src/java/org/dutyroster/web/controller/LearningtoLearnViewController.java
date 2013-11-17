/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.dutyroster.hibernate.database.LearningToLearn;
import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.hibernate.database.UserAccount;
import org.dutyroster.web.services.LearningtoLearnService;
import org.dutyroster.web.services.StaffMemberServices;
import org.dutyroster.web.services.UserServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author mfh684
 */
@ManagedBean(name="learningView")
@RequestScoped
public class LearningtoLearnViewController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Getter@Setter
    private Date fromDate = new Date();
    @Getter@Setter
    private Date toDate = new Date();
    @Getter@Setter
    private List<LearningToLearn> viewList;
    
    @Getter@Setter
    private StaffMember sm;
    LearningtoLearnService lls = new LearningtoLearnService();
    UserServices us = new UserServices();
    StaffMemberServices sms = new StaffMemberServices();
    public void load(){
        System.out.println(fromDate);
        System.out.println(toDate);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String userName = (String) sessionMap.get(Constants.SESSION_USER);
        UserAccount account = us.getUserByUserName(userName);
        account.setPassword(null);
        Short staffNumber = account.getStaffId();
        sm = sms.getStaffMemberById(staffNumber);
        viewList = lls.getLearningtoLearns(sm,fromDate,toDate);
        fromDate = new Date();
        toDate = new Date();
    }
}
