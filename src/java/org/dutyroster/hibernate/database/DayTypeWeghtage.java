package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * DayTypeWeghtage generated by hbm2java
 */
public class DayTypeWeghtage  implements java.io.Serializable {


     private DayTypeWeghtageId id;
     private short points;

    public DayTypeWeghtage() {
    }

    public DayTypeWeghtage(DayTypeWeghtageId id, short points) {
       this.id = id;
       this.points = points;
    }
   
    public DayTypeWeghtageId getId() {
        return this.id;
    }
    
    public void setId(DayTypeWeghtageId id) {
        this.id = id;
    }
    public short getPoints() {
        return this.points;
    }
    
    public void setPoints(short points) {
        this.points = points;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final DayTypeWeghtage other = (DayTypeWeghtage) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }




}


