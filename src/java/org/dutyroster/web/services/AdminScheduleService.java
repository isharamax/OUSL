/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.LearningToLearn;
import org.dutyroster.hibernate.database.PreOrientation;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author mfh684
 */
public class AdminScheduleService implements Serializable {

    private static final long serialVersionUID = 1L;
    private org.hibernate.Session session;

    public AdminScheduleService() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
     public List<Session> getSessions() {
        List<Session> sessionList = new ArrayList<Session>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Session");
            sessionList = q.list();
            tx.commit();
            sessionList = (sessionList == null) ? new ArrayList<Session>() : sessionList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return sessionList;
    }

    public List<Center> getCenters() {
        List<Center> centerList = new ArrayList<Center>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Center");
            centerList = q.list();
            tx.commit();
            centerList = (centerList == null) ? new ArrayList<Center>() : centerList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return centerList;
    }

    public List<Program> getPrograms() {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        List<Program> programList = new ArrayList<Program>();
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Program");
            programList = q.list();
            tx.commit();
            programList = (programList == null) ? new ArrayList<Program>() : programList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return programList;
    }
    
     public List<Department> getDepartments() {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        List<Department> deptList = new ArrayList<Department>();
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Department");
            deptList = q.list();
            tx.commit();
            deptList = (deptList == null) ? new ArrayList<Department>() : deptList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return deptList;
    }
     public void savePreOrientationEntry(Date date, Date year, Program program, Center center, Department dep, Session ses ){
       
        PreOrientation orientation = new PreOrientation();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            orientation.setCenter(center);
            orientation.setDepartment(dep);
            orientation.setPreDate(date);
            orientation.setPreYear(year);
            orientation.setProgram(program);
            orientation.setSession(ses);
            session.save(orientation);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
     }
     public void saveLearningtoLearn(Date date, Date year, Program program, Center center, Department dep, Session ses ){
         LearningToLearn learningToLearn = new LearningToLearn();
         if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            learningToLearn.setCenter(center);
            learningToLearn.setDepartment(dep);
            learningToLearn.setLerDate(date);
            learningToLearn.setLerYear(year);
            learningToLearn.setProgram(program);
            learningToLearn.setSession(ses);
            session.save(learningToLearn);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
         
     }

}
