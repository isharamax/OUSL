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
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.web.services.AdminScheduleService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name="adminSchedule", eager=true)
@SessionScoped
public class AdministrationScheduleController implements Serializable {

    private static final long serialVersionUID = 1L;
    @Getter@Setter
    private Short scheduleId;
    @Getter@Setter
    private Date date;
    @Getter@Setter
    private Date academicYear;
    @Getter@Setter
    private Program programId;
    @Getter@Setter
    private Center centerId;
    @Getter@Setter
    private Department departmentId;
    @Getter@Setter
    private Session sessionId;
    @Getter@Setter
    private String distributionTypeId;
    @Getter@Setter
    private String scheduleTypeId;
    @Getter@Setter
    private List<Program> programList;
    @Getter@Setter
    private List<Center> centerList;
    @Getter@Setter
    private List<Department> departmentList;
    @Getter@Setter
    private List<Session> sessionList;
    @Getter@Setter
    private List<String> distributionTypeList;
    @Getter@Setter
    private List<String> scheduleTypeList;
    @Getter@Setter
    private boolean disableDistribution;
    private AdminScheduleService ass = new AdminScheduleService();
    
    @PostConstruct
    public void init(){
        programList = ass.getPrograms();
        centerList = ass.getCenters();
        departmentList = ass.getDepartments();
        sessionList = ass.getSessions();
        scheduleTypeList = new ArrayList<String>();
        scheduleTypeList.add("Pre Orientation");
        scheduleTypeList.add("Learning to Learn");
        scheduleTypeList.add("Registration");
        distributionTypeList = new ArrayList<String>();
        distributionTypeList.add("Distribution Type One");
        disableDistribution = true;
    }
    
    public void save(){
        if (scheduleTypeId.equals("Pre Orientation")) {
            ass.savePreOrientationEntry(date, academicYear, programId, centerId, departmentId, sessionId);
        }else if(scheduleTypeId.equals("Learning to Learn")){
            ass.saveLearningtoLearn(date, academicYear, programId, centerId, departmentId, sessionId);
        }else if(scheduleTypeId.equals("Registration")){
            
        }
//        System.out.println("program : "+ programId.getProgramName());
//        System.out.println("center : "+centerId.getCenterName());
//        System.out.println("department : "+ departmentId.getDeptName());
//        System.out.println("session : "+ sessionId.getSessionDesc());
//        System.out.println("scheduleType : "+scheduleTypeId);
//        System.out.println("distributionType : "+distributionTypeId);
//        System.out.println("date : "+ date);
//        System.out.println("year : "+academicYear);
//        System.out.println("schedule id :"+ scheduleId);
        
    }
    public void determineDitributionVisibility(){
        if (scheduleTypeId.equals("Registration")) {
            disableDistribution = false;
        }else{
            disableDistribution = true;
        }
        
    }
}
