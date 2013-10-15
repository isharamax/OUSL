package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * StaffCategory generated by hbm2java
 */
public class StaffCategory  implements java.io.Serializable {


     private Short staffCatId;
     private String staffCatName;
     private Set<StaffMember> staffMembers = new HashSet<StaffMember>(0);

    public StaffCategory() {
    }

	
    public StaffCategory(String staffCatName) {
        this.staffCatName = staffCatName;
    }
    public StaffCategory(String staffCatName, Set<StaffMember> staffMembers) {
       this.staffCatName = staffCatName;
       this.staffMembers = staffMembers;
    }
   
    public Short getStaffCatId() {
        return this.staffCatId;
    }
    
    public void setStaffCatId(Short staffCatId) {
        this.staffCatId = staffCatId;
    }
    public String getStaffCatName() {
        return this.staffCatName;
    }
    
    public void setStaffCatName(String staffCatName) {
        this.staffCatName = staffCatName;
    }
    public Set<StaffMember> getStaffMembers() {
        return this.staffMembers;
    }
    
    public void setStaffMembers(Set<StaffMember> staffMembers) {
        this.staffMembers = staffMembers;
    }




}

