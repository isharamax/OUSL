/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Registration;
import org.dutyroster.hibernate.database.RegistrationType;
import org.dutyroster.hibernate.database.Sessioninfo;
import org.dutyroster.web.services.AdminScheduleService;
import org.dutyroster.web.services.AdminProgramServices;
import org.dutyroster.web.services.AdminCenterService;
import org.dutyroster.web.services.AdminDepartmentServices;
import org.dutyroster.web.services.AdminSessionServices;
import org.dutyroster.web.services.AdminRegTypeService;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "schedule")
@SessionScoped
public class AdminScheduleController implements Serializable {

    private Short scheduleId;
    private String scheduleType;
    private Date date;
    private Short academicYear;
    private Short programID;
    private Short centerID;
    private Short departmentID;
    private Short sessionID;
    private Short registrationTypeID;
    private Program program;
    private Center center;
    private Department department;
    private Sessioninfo sessionDesc;
    private RegistrationType registrationType;
    private String distributionType;
    private AdminScheduleService adminScheduleService = new AdminScheduleService();
    private AdminProgramServices adminProgramServices = new AdminProgramServices();
    private AdminCenterService adminCenterService = new AdminCenterService();
    private AdminDepartmentServices adminDepartmentServices = new AdminDepartmentServices();
    private AdminSessionServices adminSessionServices = new AdminSessionServices();
    private AdminRegTypeService adminRegTypeService = new AdminRegTypeService();
    private Registration[] registrations = null;
    private Registration selected;
    private String filterText;
    private String[] scheduleTypes = new String[3];
    private Program[] programList;
    private Center[] centerList;
    private Department[] departmentList;
    private Sessioninfo[] sessionList;
    private RegistrationType[] registrationTypeList;
    private String[] distributionTypes = new String[2];

    /** Creates a new instance of AdminScheduleController */
    public AdminScheduleController() {
        try {
            scheduleTypes[0] = "Registration";
            scheduleTypes[1] = "Pre Orientation";
            scheduleTypes[2] = "Learning to Learn";
            registrations = adminScheduleService.searchAllRegistrations();
            programList = adminProgramServices.searchAllPrograms();
            centerList = adminCenterService.searchAllCenters();
            departmentList = adminDepartmentServices.searchAllDepartments();
            sessionList = adminSessionServices.searchAllSessioninfos();
            registrationTypeList = adminRegTypeService.searchAllRegTypes();
            distributionTypes[0] = "Even Distribution";
            distributionTypes[1] = "Special Distribution";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Short getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Short scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Short getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Short academicYear) {
        this.academicYear = academicYear;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Sessioninfo getSessionDesc() {
        return sessionDesc;
    }

    public void setSessionDesc(Sessioninfo sessionDesc) {
        this.sessionDesc = sessionDesc;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    public AdminScheduleService getAdminScheduleService() {
        return adminScheduleService;
    }

    public void setAdminScheduleService(AdminScheduleService adminScheduleService) {
        this.adminScheduleService = adminScheduleService;
    }

    public AdminProgramServices getAdminProgramServices() {
        return adminProgramServices;
    }

    public void setAdminProgramServices(AdminProgramServices adminProgramServices) {
        this.adminProgramServices = adminProgramServices;
    }

    public AdminCenterService getAdminCenterService() {
        return adminCenterService;
    }

    public void setAdminCenterService(AdminCenterService adminCenterService) {
        this.adminCenterService = adminCenterService;
    }

    public AdminDepartmentServices getAdminDepartmentServices() {
        return adminDepartmentServices;
    }

    public void setAdminDepartmentServices(AdminDepartmentServices adminDepartmentServices) {
        this.adminDepartmentServices = adminDepartmentServices;
    }

    public AdminSessionServices getAdminSessionServices() {
        return adminSessionServices;
    }

    public void setAdminSessionServices(AdminSessionServices adminSessionServices) {
        this.adminSessionServices = adminSessionServices;
    }

    public AdminRegTypeService getAdminRegTypeService() {
        return adminRegTypeService;
    }

    public void setAdminRegTypeService(AdminRegTypeService adminRegTypeService) {
        this.adminRegTypeService = adminRegTypeService;
    }

    public Registration[] getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Registration[] registrations) {
        this.registrations = registrations;
    }

    public Registration getSelected() {
        return selected;
    }

    public void setSelected(Registration selected) {
        this.selected = selected;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    public String[] getScheduleTypes() {
        return scheduleTypes;
    }

    public void setScheduleTypes(String[] scheduleTypes) {
        this.scheduleTypes = scheduleTypes;
    }

    public Program[] getProgramList() {
        return programList;
    }

    public void setProgramList(Program[] programList) {
        this.programList = programList;
    }

    public Center[] getCenterList() {
        return centerList;
    }

    public void setCenterList(Center[] centerList) {
        this.centerList = centerList;
    }

    public Department[] getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(Department[] departmentList) {
        this.departmentList = departmentList;
    }

    public Sessioninfo[] getSessionList() {
        return sessionList;
    }

    public void setSessionList(Sessioninfo[] sessionList) {
        this.sessionList = sessionList;
    }

    public RegistrationType[] getRegistrationTypeList() {
        return registrationTypeList;
    }

    public void setRegistrationTypeList(RegistrationType[] registrationTypeList) {
        this.registrationTypeList = registrationTypeList;
    }

    public String[] getDistributionTypes() {
        return distributionTypes;
    }

    public void setDistributionTypes(String[] distributionTypes) {
        this.distributionTypes = distributionTypes;
    }

    public Short getProgramID() {
        return programID;
    }

    public void setProgramID(Short programID) {
        this.programID = programID;
    }

    public Short getCenterID() {
        return centerID;
    }

    public void setCenterID(Short centerID) {
        this.centerID = centerID;
    }

    public Short getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Short departmentID) {
        this.departmentID = departmentID;
    }

    public Short getSessionID() {
        return sessionID;
    }

    public void setSessionID(Short sessionID) {
        this.sessionID = sessionID;
    }

    public Short getRegistrationTypeID() {
        return registrationTypeID;
    }

    public void setRegistrationTypeID(Short registrationTypeID) {
        this.registrationTypeID = registrationTypeID;
    }

    public void addScheduleInfo() {
        if (scheduleType == "Registration") {
            if (this.getScheduleId().equals((short) 0)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Schedule ID left blank. Please select a Schedule ID."));
            } else if (this.getScheduleType().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Schedule Type left blank. Please select a Schdule Type."));
            } else if (this.getDate() == null || this.getDate().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Schedule Date left blank. Please select a Schedule Date."));
            } else if (this.getAcademicYear() == null || this.getAcademicYear().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Academic Year left blank. Please select a Academic Year."));
            } else if (this.getProgramID() == null || this.getProgramID().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Program left blank. Please select a Program Name."));
            } else if (this.getCenterID() == null || this.getCenterID().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Center left blank. Please select a Center Name."));
            } else if (this.getDepartmentID() == null || this.getDepartmentID().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Department left blank. Please select a Department Name."));
            } else if (this.getSessionID() == null || this.getSessionID().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Session left blank. Please select a Session."));
            } else if (this.getRegistrationTypeID() == null || this.getRegistrationTypeID().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Registratio Type left blank. Please select a Registration Type."));
            } else if (this.getDistributionType().equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Distribution Type left blank. Please select a Distribution Type."));
            } else {
                String response = null;
                Registration dtoRegistration = new Registration();
                dtoRegistration.setRegId(this.getScheduleId());
                dtoRegistration.setDepartment(this.getDepartment());
                dtoRegistration.setProgram(this.getProgram());
                dtoRegistration.setSessioninfo(this.getSessionDesc());
                dtoRegistration.setRegistrationType(this.getRegistrationType());
                dtoRegistration.setCenter(this.getCenter());
                dtoRegistration.setRegDate(this.getDate());
                dtoRegistration.setRegYear(this.getAcademicYear());
                dtoRegistration.setDistribution(this.getDistributionType());
                this.setScheduleId(null);
                this.setScheduleType(null);
                this.setDate(null);
                this.setAcademicYear(null);
                //this.setProgramID(null);
                //this.setCenterID(null);
                //this.setDepartmentID(null);
                //this.setSessionID(null);
                //this.setRegistrationTypeID(null);
                //this.setDistributionType(null);

                response = adminScheduleService.addRegistration(dtoRegistration);
                if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                    registrations = adminScheduleService.searchAllRegistrations();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
                }
            }
        }
    }

    public void newScheduleInfo() {
        Short lastScheduleId = 0;
        int newScheduleId = 0;
        lastScheduleId = adminScheduleService.getLastRegistrationID();
        newScheduleId = lastScheduleId + 1;
        this.setScheduleId((short) newScheduleId);
        //this.setScheduleType(null);
        //this.setDate(null);
        this.setAcademicYear(null);
        //this.setProgramID(null);
        //this.setCenterID(null);
        //this.setDepartmentID(null);
        //this.setSessionID(null);
        //this.setRegistrationTypeID(null);
        //this.setDistributionType(null);
    }

    public void searchScheduleInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            registrations = adminScheduleService.searchRegistrationsByFilter(filterText);
        }
    }
}
