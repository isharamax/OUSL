package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * RegistrationAlertHistoryId generated by hbm2java
 */
public class RegistrationAlertHistoryId  implements java.io.Serializable {


     private short staffId;
     private short regId;
     private short alertId;

    public RegistrationAlertHistoryId() {
    }

    public RegistrationAlertHistoryId(short staffId, short regId, short alertId) {
       this.staffId = staffId;
       this.regId = regId;
       this.alertId = alertId;
    }
   
    public short getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(short staffId) {
        this.staffId = staffId;
    }
    public short getRegId() {
        return this.regId;
    }
    
    public void setRegId(short regId) {
        this.regId = regId;
    }
    public short getAlertId() {
        return this.alertId;
    }
    
    public void setAlertId(short alertId) {
        this.alertId = alertId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RegistrationAlertHistoryId) ) return false;
		 RegistrationAlertHistoryId castOther = ( RegistrationAlertHistoryId ) other; 
         
		 return (this.getStaffId()==castOther.getStaffId())
 && (this.getRegId()==castOther.getRegId())
 && (this.getAlertId()==castOther.getAlertId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getStaffId();
         result = 37 * result + this.getRegId();
         result = 37 * result + this.getAlertId();
         return result;
   }   


}

