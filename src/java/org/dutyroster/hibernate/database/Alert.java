package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * Alert generated by hbm2java
 */
public class Alert  implements java.io.Serializable {


     private Short alertId;
     private String alertName;
     private String alertBody;

    public Alert() {
    }

    public Alert(String alertName, String alertBody) {
       this.alertName = alertName;
       this.alertBody = alertBody;
    }
   
    public Short getAlertId() {
        return this.alertId;
    }
    
    public void setAlertId(Short alertId) {
        this.alertId = alertId;
    }
    public String getAlertName() {
        return this.alertName;
    }
    
    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }
    public String getAlertBody() {
        return this.alertBody;
    }
    
    public void setAlertBody(String alertBody) {
        this.alertBody = alertBody;
    }




}

