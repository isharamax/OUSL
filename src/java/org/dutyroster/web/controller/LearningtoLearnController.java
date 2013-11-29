/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;

import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.hibernate.database.UserAccount;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.LearningStaff;
import org.dutyroster.hibernate.database.LearningToLearn;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.web.services.LearningtoLearnService;
import org.dutyroster.web.services.StaffMemberServices;
import org.dutyroster.web.services.UserServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "lerToLer", eager = true)
//@SessionScoped
@ViewScoped
public class LearningtoLearnController implements Serializable {

    private static final long serialVersionUID = 1L;
    UserServices us = new UserServices();
    StaffMemberServices sms = new StaffMemberServices();
    LearningtoLearnService lls = new LearningtoLearnService();
    @Getter
    @Setter
    private StaffMember staffMember;
    @Getter
    @Setter
    private Department department;
    @Getter
    @Setter
    private Program attendedProgram;
    @Getter
    @Setter
    private Center attendedCenter;
    @Getter
    @Setter
    private String attendedDate;
    @Getter
    @Setter
    private Session attendedSession;
    @Getter
    @Setter
    private List<Program> programList;
    @Getter
    @Setter
    private List<Center> centerList;
    @Getter
    @Setter
    private List<Session> sessionList;
    @Getter
    @Setter
    private List<String> dateList;
    @Getter
    @Setter
    private int currentLevel = 0;
    @Getter
    @Setter
    private String pointLevel;
    @Getter
    @Setter
    private boolean disableCenter;
    @Getter
    @Setter
    private boolean disableSession;
    @Getter
    @Setter
    private boolean disableDates;
    @Getter
    @Setter
    private boolean disablePrograms;
    @Getter
    @Setter
    private List<LearningStaff> learningStaff;
    private LearningStaff currentLearningStaff;
    @Getter
    @Setter
    private boolean disableEditButton;
    @Getter
    @Setter
    private boolean disableDeleteButton;
    @Getter@Setter
    private boolean disableCancelButton;
    private Date dateAttended;
    private boolean slotAvailable;
    @Getter@Setter
    private boolean disableCheckAvailbility;
    @Getter@Setter
    private boolean disableNewButton;
    @Getter@Setter
    private boolean disableSubmitButton;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String userName = (String) sessionMap.get(Constants.SESSION_USER);
        Short learningToLearnID = (Short) sessionMap.get(Constants.LEARNING_TO_LEARN_ID);
        sessionMap.remove(Constants.LEARNING_TO_LEARN_ID);
        System.out.println("ler ID is : " + learningToLearnID);
        UserAccount account = us.getUserByUserName(userName);
        account.setPassword(null);
        Short staffNumber = account.getStaffId();
        staffMember = sms.getStaffMemberById(staffNumber);
        currentLevel = lls.getUserAccumulatedPoints(staffMember);
        if (learningToLearnID != null) {
            currentLearningStaff = lls.getLearningToLearn(staffMember.getStaffId(), learningToLearnID);
            if (currentLearningStaff != null) {
                centerList = new ArrayList<Center>();
                sessionList = new ArrayList<Session>();
                dateList = new ArrayList<String>();
                programList = new ArrayList<Program>();
                attendedCenter = currentLearningStaff.getId().getLerId().getCenter();
                centerList.add(attendedCenter);
                System.out.println(attendedCenter);
                attendedDate = currentLearningStaff.getId().getLerId().getLerDate().toString();
                dateList.add(attendedDate);
                System.out.println(attendedDate);
                attendedProgram = currentLearningStaff.getId().getLerId().getProgram();
                programList.add(attendedProgram);
                attendedSession = currentLearningStaff.getId().getLerId().getSession();
                sessionList.add(attendedSession);
                System.out.println(attendedProgram);
                System.out.println(attendedSession);
                disableDeleteButton = false;
                disableEditButton = false;
                disableCancelButton = true;
            }
        } else {
            disableDeleteButton = true;
            disableEditButton = true;

        }

        department = staffMember.getDepartment();
        if (department != null) {
            List<LearningToLearn> lerList = lls.getLearningToLearnProgramsByDept(department);
            learningStaff = lls.getLearningtoLearnStaffbyStaffId(staffMember);
            programList = new ArrayList<Program>();

            for (LearningToLearn llc : lerList) {
                programList.add(llc.getProgram());

            }
            programList = new ArrayList<Program>(new HashSet<Program>(programList));
            disableCenter = true;
            disableSession = true;
            disableDates = true;
            disablePrograms = true;
            slotAvailable = false;
            disableCheckAvailbility = true;
            disableCancelButton = true;
            disableSubmitButton = true;
        }
    }

    public void load() {
    }

    public String save() {
//        System.out.println("Selected Center is : " + attendedCenter.getCenterName());
//        System.out.println("Selected Program is : " + attendedProgram.getProgramName());
//        System.out.println("Selected Session is : " + attendedSession.getSessionDesc());
//        System.out.println("Selected Date is : " + attendedDate);
        boolean result = false;
        try {
            dateAttended = new SimpleDateFormat("y-M-d", Locale.ENGLISH).parse(attendedDate);
            System.out.println(dateAttended);
            LearningToLearn ler = lls.getLearningToLearn(department, attendedProgram, attendedSession, attendedCenter, dateAttended);
            if (currentLearningStaff != null) {
                if (ler != null) {
                    //lls.saveLearningToLearn(staffMember, ler);
                    result = lls.updateLearningStaff(currentLearningStaff, ler, staffMember);
                } else {
                    result = false;
                }
            } else {
                if (dateAttended != null) {
                    if (ler != null) {
                        result = lls.saveLearningToLearnStaff(staffMember, ler);
                    } else {
                        result = false;
                    }
                }
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
            Logger.getLogger(LearningtoLearnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        if (result) {
            context.addMessage(null, new FacesMessage("Successful", "Successfully Saved!"));
            learningStaff = lls.getLearningtoLearnStaffbyStaffId(staffMember);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unsuccessful", "Operation Unsuccessful!"));
        }
        resetValues();
        return "/pages/learning_to_learn/leaning_to_learn_user_participation?faces-redirect=true";
    }

    public void resetValues() {
        attendedCenter = null;
        attendedProgram = null;
        attendedSession = null;
        attendedDate = null;
        disableCenter = true;
        disableDates = true;
        disableSession = true;
        disableEditButton = true;
        disableDeleteButton = true;
        slotAvailable = false;
        disablePrograms=true;
        disableCheckAvailbility=true;
        disableCancelButton = true;
        disableNewButton = false;
        disableSubmitButton=true;
    }

    public void processProgramChange() {
        disableCenter = false;
        attendedDate = null;
        dateList = null;
        attendedSession = null;
        sessionList = null;
        disableDates = true;
        disableSession = true;
        attendedCenter = null;
        centerList = null;
        if (attendedProgram == null) {
            return;
        }
        centerList = lls.getCentersbyDeptPrg(department, attendedProgram);
        centerList = new ArrayList<Center>(new HashSet<Center>(centerList));
        slotAvailable = false;
    }

    public void processCenterChange() {
        disableSession = false;
        disableDates = true;
        attendedDate = null;
        dateList = null;
        attendedSession = null;
        sessionList = null;
        slotAvailable = false;
        if (attendedCenter == null) {
            return;
        }
        sessionList = lls.getSessbyDeptPrgCent(department, attendedProgram, attendedCenter);
        sessionList = new ArrayList<Session>(new HashSet<Session>(sessionList));
    }

    public void processSessionChange() {
        disableDates = false;
        //attendedDate = null;
        dateList = null;
        slotAvailable = false;
        if (attendedSession == null) {
            return;
        }
        List<Date> temp = lls.getDatesbyDeptPrgCentSess(department, attendedProgram, attendedCenter, attendedSession);
        dateList = new ArrayList<String>();
        for (Date date : temp) {
            System.out.println("Date Returned from DB : " + date);
            dateList.add(date.toString());
        }

        dateList = new ArrayList<String>(new HashSet<String>(dateList));
    }

    public void processDateChange() {
        System.out.println(attendedDate);
        disableCheckAvailbility=false;
        slotAvailable = false;
        disableSubmitButton = false;
    }

    public void enablePrograms() {
        disablePrograms = false;
         disableCancelButton = false;
    }

    public void enableEditFields() {
        disableCenter = false;
        disableDates = false;
        disablePrograms = false;
        disableSession = false;
        slotAvailable = false;
         disableDeleteButton=true;
        disableNewButton = true;
        disableCancelButton = false;
    }

    public String delete(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (currentLearningStaff != null) {
            lls.deleteLearningStaff(currentLearningStaff, staffMember);
            learningStaff = lls.getLearningtoLearnStaffbyStaffId(staffMember);
            context.addMessage(null, new FacesMessage("Success", "Delete Was Successful "));
        } else {
            context.addMessage(null, new FacesMessage("Failed", "Delete Was Not Successful "));
        }
        resetValues();
        return "/pages/learning_to_learn/leaning_to_learn_user_participation?faces-redirect=true";
    }

    public void checkAvailability() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            dateAttended = new SimpleDateFormat("y-M-d", Locale.ENGLISH).parse(attendedDate);
            System.out.println(dateAttended);
            LearningToLearn ler = lls.getLearningToLearn(department, attendedProgram, attendedSession, attendedCenter, dateAttended);
//            if (currentLearningStaff != null) {
            if (ler != null) {
                slotAvailable = lls.checkAvailability(staffMember, ler);
                if (slotAvailable) {
                    context.addMessage(null, new FacesMessage("Slot Status", "Slot Available for Enrolling, Please Submit your record!"));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Slot Unsuccessful", "Sorry No Slots Available for this Schedule or Possible Duplicate Entry!"));
                }
                System.out.println("Slot Available : " + slotAvailable);
            }

        } catch (ParseException ex) {
            Logger.getLogger(LearningtoLearnController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
