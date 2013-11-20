/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.hibernate.database;

/**
 *
 * @author mfh684
 */
public class StaffDistribution implements java.io.Serializable {
//     CREATE TABLE STAFF_DISTRIBUTION(
//  ACADEMIC_YEAR SMALLINT(4) NOT NULL,
//  SCHEDULE_TYPE VARCHAR(5) NOT NULL,
//  STAFF_CAT_ID SMALLINT(devil) NOT NULL,
//  NO_OF_MEMBERS SMALLINT(4) NOT NULL,
//  PRIMARY KEY(ACADEMIC_YEAR,SCHEDULE_TYPE,STAFF_CAT_ID),
//  FOREIGN KEY (STAFF_CAT_ID) REFERENCES STAFF_CATEGORY(STAFF_CAT_ID)
//);

    private Short academicYear;
    private String scheduleType;
    private StaffCategory staffCategory;
    private Short noOfMembers;

    public Short getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Short academicYear) {
        this.academicYear = academicYear;
    }

   

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public StaffCategory getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(StaffCategory staffCategory) {
        this.staffCategory = staffCategory;
    }

    
    public Short getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(Short noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.academicYear != null ? this.academicYear.hashCode() : 0);
        hash = 97 * hash + (this.scheduleType != null ? this.scheduleType.hashCode() : 0);
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
        final StaffDistribution other = (StaffDistribution) obj;
        if (this.academicYear != other.academicYear && (this.academicYear == null || !this.academicYear.equals(other.academicYear))) {
            return false;
        }
        if ((this.scheduleType == null) ? (other.scheduleType != null) : !this.scheduleType.equals(other.scheduleType)) {
            return false;
        }
        if (this.staffCategory != other.staffCategory && (this.staffCategory == null || !this.staffCategory.equals(other.staffCategory))) {
            return false;
        }
        return true;
    }
}
