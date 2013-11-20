package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * LearningStaff generated by hbm2java
 */
public class LearningStaff  implements java.io.Serializable {


     private LearningStaffId id;
     private String applyStatus;
     private short points;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LearningStaff other = (LearningStaff) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public LearningStaff() {
    }

    public LearningStaff(LearningStaffId id, String applyStatus, short points) {
       this.id = id;
       this.applyStatus = applyStatus;
       this.points = points;
    }
   
    public LearningStaffId getId() {
        return this.id;
    }
    
    public void setId(LearningStaffId id) {
        this.id = id;
    }
    public String getApplyStatus() {
        return this.applyStatus;
    }
    
    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
    public short getPoints() {
        return this.points;
    }
    
    public void setPoints(short points) {
        this.points = points;
    }




}


