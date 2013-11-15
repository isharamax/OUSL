/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.io.Serializable;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.PreOrientation;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author mfh684
 */
public class ConverterService implements Serializable {

    private static final long serialVersionUID = 1L;

    private org.hibernate.Session session;

    public ConverterService() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    public Center getCenterById(Short id) {
        Center c = new Center();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {

            Query q = session.createQuery("from Center c where c.centerId=:cId");
            q.setShort("cId", id);
            c = (Center) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return c;
    }
    public Program getProgramById(Short id) {
        Program p = new Program();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {

            Query q = session.createQuery("from Program p where p.programId=:pId");
            q.setShort("pId", id);
            p = (Program) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return p;
    }
     public Session getSessionById(Short id) {
        Session s = new Session();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {

            Query q = session.createQuery("from Session s where s.sessionId=:sId");
            q.setShort("sId", id);
            s = (Session) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return s;
    }
     public Department getDeptById(Short deptId) {
        Department department = new Department();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Department d where d.deptId=:deptId");
            q.setShort("deptId", deptId);
            department = (Department) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return department;
    }
     public PreOrientation getPreOrientationById(Short preId){
         PreOrientation orientation = new PreOrientation();
         if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p where p.preId=:preId");
            q.setShort("preId", preId);
            orientation = (PreOrientation) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return orientation;
     }
}
