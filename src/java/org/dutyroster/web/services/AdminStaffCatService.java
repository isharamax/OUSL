/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.StaffCategory;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
public class AdminStaffCatService {

    Session session = null;
    private Short staffCatID;
    private String staffCatName;
    StaffCategory[] staffCatlist = null;
    int i;

    public AdminStaffCatService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Short getStaffCatID() {
        return staffCatID;
    }

    public void setStaffCatID(Short staffCatID) {
        this.staffCatID = staffCatID;
    }

    public String getStaffCatName() {
        return staffCatName;
    }

    public void setStaffCatName(String staffCatName) {
        this.staffCatName = staffCatName;
    }

    public String addStaffCat(StaffCategory staffCat) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            StaffCategory add = new StaffCategory();
            add.setStaffCatId(staffCat.getStaffCatId());
            add.setStaffCatName(staffCat.getStaffCatName());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editStaffCat(StaffCategory staffCat) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            StaffCategory update = new StaffCategory();
            update.setStaffCatId(staffCat.getStaffCatId());
            update.setStaffCatName(staffCat.getStaffCatName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteStaffCat(StaffCategory staffCat) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            StaffCategory del = new StaffCategory();
            del.setStaffCatId(staffCat.getStaffCatId());
            del.setStaffCatName(staffCat.getStaffCatName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastStaffCatID() {
        Short lastCId = 0;
        List<StaffCategory> staffCats = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(staffCatId) FROM StaffCategory");
            staffCats = (List<StaffCategory>) q.list();
            session.disconnect();
            if (staffCats != null && staffCats.size() > 0) {
                lastCId = staffCats.get(staffCats.size() - 1).getStaffCatId();
            }
            return lastCId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastCId;
        }
    }

    public StaffCategory[] searchAllStaffCats() {
        List<StaffCategory> staffCats = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM StaffCategory");
            staffCats = (List<StaffCategory>) q.list();
            session.disconnect();
            if (staffCats != null && staffCats.size() > 0) {
                staffCatlist = new StaffCategory[staffCats.size()];
                i = 0;
                for (StaffCategory staffcat : staffCats) {
                    StaffCategory obj = new StaffCategory();
                    obj.setStaffCatId(staffcat.getStaffCatId());
                    obj.setStaffCatName(staffcat.getStaffCatName());
                    staffCatlist[i] = obj;
                    i++;
                }
            }
            return staffCatlist;

        } catch (Exception e) {
            e.printStackTrace();
            staffCatlist = new StaffCategory[1];
            return staffCatlist;
        }
    }

    public StaffCategory[] searchStaffCatsByFilter(String Filter) {
        List<StaffCategory> staffCats = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM StaffCategory S where S.staffCatName like '%"+Filter+"%'");
            staffCats = (List<StaffCategory>) q.list();
            session.disconnect();
            if (staffCats != null && staffCats.size() > 0) {
                staffCatlist = new StaffCategory[staffCats.size()];
                i = 0;
                for (StaffCategory staffcat : staffCats) {
                    StaffCategory obj = new StaffCategory();
                    obj.setStaffCatId(staffcat.getStaffCatId());
                    obj.setStaffCatName(staffcat.getStaffCatName());
                    staffCatlist[i] = obj;
                    i++;
                }
            }
            return staffCatlist;

        } catch (Exception e) {
            e.printStackTrace();
            staffCatlist = new StaffCategory[1];
            return staffCatlist;
        }
    }
}
