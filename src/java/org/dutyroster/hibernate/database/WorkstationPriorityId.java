package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * WorkstationPriorityId generated by hbm2java
 */
public class WorkstationPriorityId  implements java.io.Serializable {
     private StaffMember staffId;
     private Workstation workstationId;
     private Department deptId;
     
    public WorkstationPriorityId() {
    }

    public WorkstationPriorityId(StaffMember staffId, Workstation workstationId, Department deptId) {
       this.staffId = staffId;
       this.workstationId = workstationId;
       this.deptId = deptId;
    }
   
    public StaffMember getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(StaffMember staffId) {
        this.staffId = staffId;
    }
    public Workstation getWorkstationId() {
        return this.workstationId;
    }
    
    public void setWorkstationId(Workstation workstationId) {
        this.workstationId = workstationId;
    }
    public Department getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(Department deptId) {
        this.deptId = deptId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof WorkstationPriorityId) ) return false;
		 WorkstationPriorityId castOther = ( WorkstationPriorityId ) other; 
         
		 return (this.getStaffId()==castOther.getStaffId())
 && (this.getWorkstationId()==castOther.getWorkstationId())
 && (this.getDeptId()==castOther.getDeptId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.staffId.getStaffId();
         result = 37 * result + this.workstationId.getWorkstationId();
         result = 37 * result + this.deptId.getDeptId();
         return result;
   }   


}


