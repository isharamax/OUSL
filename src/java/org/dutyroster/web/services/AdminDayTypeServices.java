/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.DayType;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
public class AdminDayTypeServices {

    Session session = null;
    private Short dayTypeId;
    private String dayTypeName;
    DayType[] dayTypelist = null;
    int i;
    int result = 0;

    public Short getDayTypeId() {
        return dayTypeId;
    }

    public void setDayTypeId(Short dayTypeId) {
        this.dayTypeId = dayTypeId;
    }

    public String getDayTypeName() {
        return dayTypeName;
    }

    public void setDayTypeName(String dayTypeName) {
        this.dayTypeName = dayTypeName;
    }

    public AdminDayTypeServices() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public String addDayType(DayType dayType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            DayType add = new DayType();
            add.setDayTypeId(dayType.getDayTypeId());
            add.setDayTypeName(dayType.getDayTypeName());
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editDayType(DayType dayType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            DayType update = new DayType();
            update.setDayTypeId(dayType.getDayTypeId());
            update.setDayTypeName(dayType.getDayTypeName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public DayType[] searchAllDayTypes() {
        List<DayType> dayTypes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM DayType");
            dayTypes = (List<DayType>) q.list();
            session.disconnect();
            if (dayTypes != null && dayTypes.size() > 0) {
                dayTypelist = new DayType[dayTypes.size()];
                i = 0;
                for (DayType dayType : dayTypes) {
                    DayType obj = new DayType();
                    obj.setDayTypeId(dayType.getDayTypeId());
                    obj.setDayTypeName(dayType.getDayTypeName());
                    dayTypelist[i] = obj;
                    i++;
                }
            }
            return dayTypelist;

        } catch (Exception e) {
            e.printStackTrace();
            dayTypelist = new DayType[1];
            return dayTypelist;
        }
    }

    public String deleteDayType(DayType dayType) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            DayType del = new DayType();
            del.setDayTypeId(dayType.getDayTypeId());
            del.setDayTypeName(dayType.getDayTypeName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastDayTypeId() {
        Short lastDId = 0;
        List<DayType> dayTypes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(dayId) FROM DayType");
            dayTypes = (List<DayType>) q.list();
            session.disconnect();
            if (dayTypes != null && dayTypes.size() > 0) {
                lastDId = dayTypes.get(dayTypes.size() - 1).getDayTypeId();
            }
            return lastDId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastDId;
        }
    }

    public DayType[] searchDayTypesByFilter(String Filter) {
        List<DayType> dayTypes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from DayType D where D.dayTypeName like '%" + Filter + "%' ");

            dayTypes = (List<DayType>) q.list();
            session.disconnect();
            if (dayTypes != null && dayTypes.size() > 0) {
                dayTypelist = new DayType[dayTypes.size()];
                i = 0;
                for (DayType d : dayTypes) {
                    DayType obj = new DayType();
                    obj.setDayTypeId(d.getDayTypeId());
                    obj.setDayTypeName(d.getDayTypeName());
                    dayTypelist[i] = obj;
                    i++;
                }
            }
            return dayTypelist;

        } catch (Exception e) {
            e.printStackTrace();
            dayTypelist = new DayType[1];
            return dayTypelist;
        }
    }
}
