package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * WorkstationCenterId generated by hbm2java
 */
public class WorkstationCenterId  implements java.io.Serializable {


     private short centerId;
     private short workstationId;

    public WorkstationCenterId() {
    }

    public WorkstationCenterId(short centerId, short workstationId) {
       this.centerId = centerId;
       this.workstationId = workstationId;
    }
   
    public short getCenterId() {
        return this.centerId;
    }
    
    public void setCenterId(short centerId) {
        this.centerId = centerId;
    }
    public short getWorkstationId() {
        return this.workstationId;
    }
    
    public void setWorkstationId(short workstationId) {
        this.workstationId = workstationId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof WorkstationCenterId) ) return false;
		 WorkstationCenterId castOther = ( WorkstationCenterId ) other; 
         
		 return (this.getCenterId()==castOther.getCenterId())
 && (this.getWorkstationId()==castOther.getWorkstationId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getCenterId();
         result = 37 * result + this.getWorkstationId();
         return result;
   }   


}

