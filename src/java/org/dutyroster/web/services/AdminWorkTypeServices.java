/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.WorkType;
import org.dutyroster.web.util.Constants;


/**
 *
 * @author Administrator
 */
public class AdminWorkTypeServices {
     Session session = null;
     private Short workTypeId;
     private String workTypeName;
     private Float WorkPercentage;

    public Float getWorkPercentage() {
        return WorkPercentage;
    }

    public void setWorkPercentage(Float WorkPercentage) {
        this.WorkPercentage = WorkPercentage;
    }
     
     

    

    public Short getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Short workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }
     
    public AdminWorkTypeServices() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    public String addWorkType(WorkType workType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            WorkType add = new WorkType();
            add.setWorkTypeId(workType.getWorkTypeId());
            add.setWorkTypeName(workType.getWorkTypeName());
            add.setWorkPercentage(workType.getWorkPercentage());
            
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;    
        }
        return Constants.SUCCESS;
    }
    
}
