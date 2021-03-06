package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * PreOrientationAlertHistory generated by hbm2java
 */
public class PreOrientationAlertHistory  implements java.io.Serializable {


     private PreOrientationAlertHistoryId id;
     private Date date;
     private Short sendingStatus;

    public PreOrientationAlertHistory() {
    }

	
    public PreOrientationAlertHistory(PreOrientationAlertHistoryId id, Date date) {
        this.id = id;
        this.date = date;
    }
    public PreOrientationAlertHistory(PreOrientationAlertHistoryId id, Date date, Short sendingStatus) {
       this.id = id;
       this.date = date;
       this.sendingStatus = sendingStatus;
    }
   
    public PreOrientationAlertHistoryId getId() {
        return this.id;
    }
    
    public void setId(PreOrientationAlertHistoryId id) {
        this.id = id;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public Short getSendingStatus() {
        return this.sendingStatus;
    }
    
    public void setSendingStatus(Short sendingStatus) {
        this.sendingStatus = sendingStatus;
    }




}


