package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Center generated by hbm2java
 */
public class Center  implements java.io.Serializable {


     private Short centerId;
     private String centerName;
     private Set<StaffMember> staffMembers = new HashSet<StaffMember>(0);
     private Set<LearningToLearn> learningToLearns = new HashSet<LearningToLearn>(0);
     private Set<PreOrientation> preOrientations = new HashSet<PreOrientation>(0);
     private Set<Registration> registrations = new HashSet<Registration>(0);

    public Center() {
    }

	
    public Center(String centerName) {
        this.centerName = centerName;
    }
    public Center(String centerName, Set<StaffMember> staffMembers, Set<LearningToLearn> learningToLearns, Set<PreOrientation> preOrientations, Set<Registration> registrations) {
       this.centerName = centerName;
       this.staffMembers = staffMembers;
       this.learningToLearns = learningToLearns;
       this.preOrientations = preOrientations;
       this.registrations = registrations;
    }
   
    public Short getCenterId() {
        return this.centerId;
    }
    
    public void setCenterId(Short centerId) {
        this.centerId = centerId;
    }
    public String getCenterName() {
        return this.centerName;
    }
    
    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }
    public Set<StaffMember> getStaffMembers() {
        return this.staffMembers;
    }
    
    public void setStaffMembers(Set<StaffMember> staffMembers) {
        this.staffMembers = staffMembers;
    }
    public Set<LearningToLearn> getLearningToLearns() {
        return this.learningToLearns;
    }
    
    public void setLearningToLearns(Set<LearningToLearn> learningToLearns) {
        this.learningToLearns = learningToLearns;
    }
    public Set<PreOrientation> getPreOrientations() {
        return this.preOrientations;
    }
    
    public void setPreOrientations(Set<PreOrientation> preOrientations) {
        this.preOrientations = preOrientations;
    }
    public Set<Registration> getRegistrations() {
        return this.registrations;
    }
    
    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }




}

