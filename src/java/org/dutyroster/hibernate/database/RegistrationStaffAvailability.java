package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * RegistrationStaffAvailability generated by hbm2java
 */
public class RegistrationStaffAvailability  implements java.io.Serializable {


     private RegistrationStaffAvailabilityId id;
     private short availability;

    public RegistrationStaffAvailability() {
    }

    public RegistrationStaffAvailability(RegistrationStaffAvailabilityId id, short availability) {
       this.id = id;
       this.availability = availability;
    }
   
    public RegistrationStaffAvailabilityId getId() {
        return this.id;
    }
    
    public void setId(RegistrationStaffAvailabilityId id) {
        this.id = id;
    }
    public short getAvailability() {
        return this.availability;
    }
    
    public void setAvailability(short availability) {
        this.availability = availability;
    }




}


