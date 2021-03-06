package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * RegistrationType generated by hbm2java
 */
public class RegistrationType  implements java.io.Serializable {


     private Short regTypeId;
     private String regTypeName;
     private Set<Registration> registrations = new HashSet<Registration>(0);

    public RegistrationType() {
    }

	
    public RegistrationType(String regTypeName) {
        this.regTypeName = regTypeName;
    }
    public RegistrationType(String regTypeName, Set<Registration> registrations) {
       this.regTypeName = regTypeName;
       this.registrations = registrations;
    }
   
    public Short getRegTypeId() {
        return this.regTypeId;
    }
    
    public void setRegTypeId(Short regTypeId) {
        this.regTypeId = regTypeId;
    }
    public String getRegTypeName() {
        return this.regTypeName;
    }
    
    public void setRegTypeName(String regTypeName) {
        this.regTypeName = regTypeName;
    }
    public Set<Registration> getRegistrations() {
        return this.registrations;
    }
    
    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }




}


