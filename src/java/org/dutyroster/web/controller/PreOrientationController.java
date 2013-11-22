/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.PreOrientation;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.PreOrientationStaff;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.hibernate.database.UserAccount;
import org.dutyroster.web.services.PreOrientationService;
import org.dutyroster.web.services.StaffMemberServices;
import org.dutyroster.web.services.UserServices;
import org.dutyroster.web.util.Constants;
import org.dutyroster.web.util.MailClient;
import org.dutyroster.web.util.MailSender;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "preOrientation", eager = true)
@ViewScoped
public class PreOrientationController implements Serializable {

    private static final long serialVersionUID = 1L;
    private PreOrientationService orientationService = new PreOrientationService();
    UserServices us = new UserServices();
    StaffMemberServices sms = new StaffMemberServices();
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
    private Date dateAttended;
    private PreOrientationStaff currentPreorientation;
    @Getter
    @Setter
    private boolean disableEditButton;
    @Getter
    @Setter
    private boolean disableDeleteButton;
    @Getter
    @Setter
    private boolean disableCancelButton;
    @Getter
    @Setter
    private boolean disableNewButton;
    @Getter
    @Setter
    private boolean disableSubmitButton;
    @Getter@Setter 
    private int currentLevel = 0;

    public PreOrientationController() {
    }

    @PostConstruct
    public void init() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String userName = (String) sessionMap.get(Constants.SESSION_USER);
        Short preOrientataionID = (Short) sessionMap.get(Constants.PREORIENTATION_ID);
        sessionMap.remove(Constants.PREORIENTATION_ID);
        UserAccount account = us.getUserByUserName(userName);
        account.setPassword(null);
        Short staffNumber = account.getStaffId();
        staffMember = sms.getStaffMemberById(staffNumber);
        if (preOrientataionID != null) {
            currentPreorientation = orientationService.getPreorientation(staffMember.getStaffId(), preOrientataionID);
            if (currentPreorientation != null) {
                centerList = new ArrayList<Center>();
                sessionList = new ArrayList<Session>();
                dateList = new ArrayList<String>();
                programList = new ArrayList<Program>();
                attendedCenter = currentPreorientation.getId().getPreId().getCenter();
                centerList.add(attendedCenter);
                System.out.println(attendedCenter);
                attendedDate = currentPreorientation.getId().getPreId().getPreDate().toString();
                dateList.add(attendedDate);
                System.out.println(attendedDate);
                attendedProgram = currentPreorientation.getId().getPreId().getProgram();
                programList.add(attendedProgram);
                attendedSession = currentPreorientation.getId().getPreId().getSession();
                sessionList.add(attendedSession);
                System.out.println(attendedProgram);
                System.out.println(attendedSession);
                disableDeleteButton = false;
                disableEditButton = false;
                disableCancelButton = true;
                currentLevel = orientationService.getUserAccumulatedPoints(staffMember);
            }
        } else {
            disableDeleteButton = true;
            disableEditButton = true;

        }
        department = staffMember.getDepartment();
        if (department != null) {
            List<PreOrientation> preList = orientationService.getProgramsByDept(department);
            programList = new ArrayList<Program>();

            for (PreOrientation preOrientation : preList) {
                programList.add(preOrientation.getProgram());

            }
            programList = new ArrayList<Program>(new HashSet<Program>(programList));
            disableCenter = true;
            disableSession = true;
            disableDates = true;
            disablePrograms = true;
            //disableDeleteButton = true;
            disableCancelButton = true;
            //disableEditButton = true;
            //disableNewButton = false;
        }
        disableSubmitButton = true;
    }

    public void load() {
    }

    public String save() {

        boolean result = false;
        try {
            dateAttended = new SimpleDateFormat("y-M-d", Locale.ENGLISH).parse(attendedDate);
            PreOrientation pre = orientationService.getPre(department, attendedProgram, attendedSession, attendedCenter, dateAttended);
            if (currentPreorientation != null) {
                if (pre != null) {
                    //currentPreorientation.getId().setPreId(pre);
                    result = orientationService.updatePreorientationStaff(currentPreorientation, pre, staffMember);
                }
            } else {
                if (dateAttended != null) {
                    if (pre != null) {
                        result = orientationService.savePreOrientation(staffMember, pre);
                    }
                }

            }
        } catch (ParseException ex) {
            Logger.getLogger(PreOrientationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println(Date.parse(attendedDate));

        FacesContext context = FacesContext.getCurrentInstance();
        if (result) {
            context.addMessage(null, new FacesMessage("Success", "Record Was Successfully Saved "));
            String msg = "A Pre Orientation Record has been created by %s with Staff Number %s on %s ";
            //orientationService.sendEmail(staffmember,)
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed", "Possible Duplicate Record!"));
        }
        resetValues();
        return "/pages/pre_orientation/pre_orientation_details?faces-redirect=true";
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
        disablePrograms = true;
        disableCancelButton = true;
        disableNewButton = false;
        disableSubmitButton = true;
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
        centerList = orientationService.getCentersbyDeptPrg(department, attendedProgram);
        centerList = new ArrayList<Center>(new HashSet<Center>(centerList));
    }

    public void processCenterChange() {
        disableSession = false;
        disableDates = true;
        attendedDate = null;
        dateList = null;
        attendedSession = null;
        sessionList = null;
        if (attendedCenter == null) {
            return;
        }
        sessionList = orientationService.getSessbyDeptPrgCent(department, attendedProgram, attendedCenter);
        sessionList = new ArrayList<Session>(new HashSet<Session>(sessionList));
    }

    public void processSessionChange() {
        disableDates = false;
        dateList = null;
        if (attendedSession == null) {
            return;
        }
        System.out.println(department.getDeptId() + " " + department.getDeptName());
        System.out.println(attendedProgram.getProgramId() + " " + attendedProgram.getProgramName());
        System.out.println(attendedCenter.getCenterId() + " " + attendedCenter.getCenterName());
        System.out.println(attendedSession.getSessionId() + " " + attendedSession.getSessionDesc());

        dateList = orientationService.getDatesbyDeptPrgCentSess(department, attendedProgram, attendedCenter, attendedSession);
        dateList = new ArrayList<String>(new HashSet<String>(dateList));
    }

    public void processDateChange() {
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
        disableDeleteButton = true;
        disableNewButton = true;
        disableCancelButton = false;
    }

    public String delete(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (currentPreorientation != null) {
            orientationService.deletePreOrientationStaff(currentPreorientation);
            context.addMessage(null, new FacesMessage("Success", "Delete Was Successful "));
        } else {
            context.addMessage(null, new FacesMessage("Failed", "Delete Was Not Successful "));
        }
        resetValues();
        return "/pages/pre_orientation/pre_orientation_details?faces-redirect=true";
    }
}
