package org.dutyroster.hibernate.database;
// Generated Oct 6, 2013 6:54:13 PM by Hibernate Tools 3.2.1.GA



/**
 * Language generated by hbm2java
 */
public class Language  implements java.io.Serializable {


     private Short lanId;
     private String lanDesc;

    public Language() {
    }

    public Language(String lanDesc) {
       this.lanDesc = lanDesc;
    }
   
    public Short getLanId() {
        return this.lanId;
    }
    
    public void setLanId(Short lanId) {
        this.lanId = lanId;
    }
    public String getLanDesc() {
        return this.lanDesc;
    }
    
    public void setLanDesc(String lanDesc) {
        this.lanDesc = lanDesc;
    }




}


