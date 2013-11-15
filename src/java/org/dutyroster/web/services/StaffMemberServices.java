/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.io.Serializable;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.StaffMember;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author mfh684
 */
public class StaffMemberServices implements Serializable {

    private static final long serialVersionUID = 1L;
    private org.hibernate.Session session;

    public StaffMemberServices() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
     public StaffMember getStaffMemberById(Short id) {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        StaffMember staffMember = new StaffMember();
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from StaffMember s join fetch s.department join fetch s.staffCategory where s.staffId=:staffId ");
            q.setShort("staffId", id);
            staffMember = (StaffMember) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
        return staffMember;

    }
    
}
