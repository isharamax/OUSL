/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.hibernate.database;

/**
 *
 * @author mfh684
 */
public class RegistrationStaffId implements java.io.Serializable 
{
    private short regId;
     private short staffId;

    public RegistrationStaffId() {
    }

    public RegistrationStaffId(short regId, short staffId) {
       this.regId = regId;
       this.staffId = staffId;
    }
   
    public short getRegId() {
        return this.regId;
    }
    
    public void setRegId(short regId) {
        this.regId = regId;
    }
    public short getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(short staffId) {
        this.staffId = staffId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RegistrationStaffId) ) return false;
		 RegistrationStaffId castOther = ( RegistrationStaffId ) other; 
         
		 return (this.getRegId()==castOther.getRegId())
 && (this.getStaffId()==castOther.getStaffId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getRegId();
         result = 37 * result + this.getStaffId();
         return result;
   }   

}
