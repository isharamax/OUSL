package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * WorkstationCenter generated by hbm2java
 */
public class WorkstationCenter  implements java.io.Serializable {


     private WorkstationCenterId id;
     private int distance;
     private short points;

    public WorkstationCenter() {
    }

    public WorkstationCenter(WorkstationCenterId id, int distance, short points) {
       this.id = id;
       this.distance = distance;
       this.points = points;
    }
   
    public WorkstationCenterId getId() {
        return this.id;
    }
    
    public void setId(WorkstationCenterId id) {
        this.id = id;
    }
    public int getDistance() {
        return this.distance;
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public short getPoints() {
        return this.points;
    }
    
    public void setPoints(short points) {
        this.points = points;
    }




}


