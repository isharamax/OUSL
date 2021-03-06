package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Registration generated by hbm2java
 */
public class Registration  implements java.io.Serializable {


     private Short regId;
     private Department department;
     private Program program;
     private Session session;
     private RegistrationType registrationType;
     private Center center;
     private Date regDate;
     private Date regYear;
     private String distribution;

    public Registration() {
    }

    public Registration(Department department, Program program, Session session, RegistrationType registrationType, Center center, Date regDate, Date regYear, String distribution) {
       this.department = department;
       this.program = program;
       this.session = session;
       this.registrationType = registrationType;
       this.center = center;
       this.regDate = regDate;
       this.regYear = regYear;
       this.distribution = distribution;
    }
   
    public Short getRegId() {
        return this.regId;
    }
    
    public void setRegId(Short regId) {
        this.regId = regId;
    }
    public Department getDepartment() {
        return this.department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Program getProgram() {
        return this.program;
    }
    
    public void setProgram(Program program) {
        this.program = program;
    }
    public Session getSession() {
        return this.session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    public RegistrationType getRegistrationType() {
        return this.registrationType;
    }
    
    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }
    public Center getCenter() {
        return this.center;
    }
    
    public void setCenter(Center center) {
        this.center = center;
    }
    public Date getRegDate() {
        return this.regDate;
    }
    
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
    public Date getRegYear() {
        return this.regYear;
    }
    
    public void setRegYear(Date regYear) {
        this.regYear = regYear;
    }
    public String getDistribution() {
        return this.distribution;
    }
    
    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }




}


