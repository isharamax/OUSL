package org.dutyroster.hibernate.database;
// Generated Oct 22, 2013 12:18:18 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Sessioninfo generated by hbm2java
 */
public class Sessioninfo  implements java.io.Serializable {


     private short sessionId;
     private String sessionDesc;
     private Float fromTime;
     private Float toTime;
     private Set<Registration> registrations = new HashSet<Registration>(0);
     private Set<PreOrientation> preOrientations = new HashSet<PreOrientation>(0);
     private Set<LearningToLearn> learningToLearns = new HashSet<LearningToLearn>(0);

    public Sessioninfo() {
    }

	
    public Sessioninfo(short sessionId, String sessionDesc) {
        this.sessionId = sessionId;
        this.sessionDesc = sessionDesc;
    }
    public Sessioninfo(short sessionId, String sessionDesc, Float fromTime, Float toTime, Set<Registration> registrations, Set<PreOrientation> preOrientations, Set<LearningToLearn> learningToLearns) {
       this.sessionId = sessionId;
       this.sessionDesc = sessionDesc;
       this.fromTime = fromTime;
       this.toTime = toTime;
       this.registrations = registrations;
       this.preOrientations = preOrientations;
       this.learningToLearns = learningToLearns;
    }
   
    public short getSessionId() {
        return this.sessionId;
    }
    
    public void setSessionId(short sessionId) {
        this.sessionId = sessionId;
    }
    public String getSessionDesc() {
        return this.sessionDesc;
    }
    
    public void setSessionDesc(String sessionDesc) {
        this.sessionDesc = sessionDesc;
    }
    public Float getFromTime() {
        return this.fromTime;
    }
    
    public void setFromTime(Float fromTime) {
        this.fromTime = fromTime;
    }
    public Float getToTime() {
        return this.toTime;
    }
    
    public void setToTime(Float toTime) {
        this.toTime = toTime;
    }
    public Set<Registration> getRegistrations() {
        return this.registrations;
    }
    
    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }
    public Set<PreOrientation> getPreOrientations() {
        return this.preOrientations;
    }
    
    public void setPreOrientations(Set<PreOrientation> preOrientations) {
        this.preOrientations = preOrientations;
    }
    public Set<LearningToLearn> getLearningToLearns() {
        return this.learningToLearns;
    }
    
    public void setLearningToLearns(Set<LearningToLearn> learningToLearns) {
        this.learningToLearns = learningToLearns;
    }




}

