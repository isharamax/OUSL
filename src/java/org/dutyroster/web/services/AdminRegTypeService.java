/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.RegistrationType;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
public class AdminRegTypeService {

    Session session = null;
    private Short regTypeID;
    private String regTypeName;
    RegistrationType[] regTypelist = null;
    int i;
    int result = 0;

    public AdminRegTypeService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Short getRegTypeID() {
        return regTypeID;
    }

    public void setRegTypeID(Short regTypeID) {
        this.regTypeID = regTypeID;
    }

    public String getRegTypeName() {
        return regTypeName;
    }

    public void setRegTypeName(String regTypeName) {
        this.regTypeName = regTypeName;
    }

    public String addRegType(RegistrationType regType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            RegistrationType add = new RegistrationType();
            add.setRegTypeId(regType.getRegTypeId());
            add.setRegTypeName(regType.getRegTypeName());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editRegType(RegistrationType regType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            RegistrationType update = new RegistrationType();
            update.setRegTypeId(regType.getRegTypeId());
            update.setRegTypeName(regType.getRegTypeName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteRegType(RegistrationType regType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            RegistrationType del = new RegistrationType();
            del.setRegTypeId(regType.getRegTypeId());
            del.setRegTypeName(regType.getRegTypeName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastRegTypeID() {
        Short lastRegId = 0;
        List<RegistrationType> regTypes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(regTypeId) FROM RegistrationType");
            regTypes = (List<RegistrationType>) q.list();
            session.disconnect();
            if (regTypes != null && regTypes.size() > 0) {
                lastRegId = regTypes.get(regTypes.size() - 1).getRegTypeId();
            }
            return lastRegId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastRegId;
        }
    }

    public RegistrationType[] searchAllRegTypes() {
        List<RegistrationType> regTypes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM RegistrationType");
            regTypes = (List<RegistrationType>) q.list();
            session.disconnect();
            if (regTypes != null && regTypes.size() > 0) {
                regTypelist = new RegistrationType[regTypes.size()];
                i = 0;
                for (RegistrationType registrationType : regTypes) {
                    RegistrationType obj = new RegistrationType();
                    obj.setRegTypeId(registrationType.getRegTypeId());
                    obj.setRegTypeName(registrationType.getRegTypeName());
                    regTypelist[i] = obj;
                    i++;
                }
            }
            return regTypelist;

        } catch (Exception e) {
            e.printStackTrace();
            regTypelist = new RegistrationType[1];
            return regTypelist;
        }
    }

    public RegistrationType[] searchRegTypesByFilter(String Filter) {
        List<RegistrationType> regTypes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from RegistrationType R where R.regTypeName like '%" + Filter + "%' ");
            regTypes = (List<RegistrationType>) q.list();
            session.disconnect();
            if (regTypes != null && regTypes.size() > 0) {
                regTypelist = new RegistrationType[regTypes.size()];
                i = 0;
                for (RegistrationType center : regTypes) {
                    RegistrationType obj = new RegistrationType();
                    obj.setRegTypeId(center.getRegTypeId());
                    obj.setRegTypeName(center.getRegTypeName());
                    regTypelist[i] = obj;
                    i++;
                }
            }
            return regTypelist;

        } catch (Exception e) {
            e.printStackTrace();
            regTypelist = new RegistrationType[1];
            return regTypelist;
        }
    }
}
