/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Holiday;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
public class AdminHolidayService {
    
    Session session = null;
    private Short holidayID;
    private Date holidayDate;
    private String holidayType;
    Holiday[] holidaylist = null;
    int i;
    int result = 0;
    
    public AdminHolidayService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Short getHolidayID() {
        return holidayID;
    }

    public void setHolidayID(Short holidayID) {
        this.holidayID = holidayID;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    public String addHoliday(Holiday holiday) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Holiday add = new Holiday();
            add.setHolidayId(holiday.getHolidayId());
            add.setHoliday(holiday.getHoliday());
            add.setHolidayType(holiday.getHolidayType());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editHoliday(Holiday holiday) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Holiday update = new Holiday();
            update.setHolidayId(holiday.getHolidayId());
            update.setHoliday(holiday.getHoliday());
            update.setHolidayType(holiday.getHolidayType());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteHoliday(Holiday holiday) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Holiday del = new Holiday();
            del.setHolidayId(holiday.getHolidayId());
            del.setHoliday(holiday.getHoliday());
            del.setHolidayType(holiday.getHolidayType());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastHolidayID() {
        Short lastCId = 0;
        List<Holiday> holidays = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(holidayId) FROM Holiday");
            holidays = (List<Holiday>) q.list();
            session.disconnect();
            if (holidays != null && holidays.size() > 0) {
                lastCId = holidays.get(holidays.size() - 1).getHolidayId();
            }
            return lastCId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastCId;
        }
    }
        
    public Holiday[] searchAllHolidays() {
        List<Holiday> holidays = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Holiday");
            holidays = (List<Holiday>) q.list();
            session.disconnect();
            if (holidays != null && holidays.size() > 0) {
                holidaylist = new Holiday[holidays.size()];
                i = 0;
                for (Holiday holiday : holidays) {
                    Holiday obj = new Holiday();
                    obj.setHolidayId(holiday.getHolidayId());
                    obj.setHoliday(holiday.getHoliday());
                    obj.setHolidayType(holiday.getHolidayType());
                    holidaylist[i] = obj;
                    i++;
                }
            }
            return holidaylist;

        } catch (Exception e) {
            e.printStackTrace();
            holidaylist = new Holiday[1];
            return holidaylist;
        }
    }
    
    
    public Holiday[] searchHolidaysByFilter(String Filter) {
        List<Holiday> holidays = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Holiday H where holidayType like '%"+Filter+"%' ");
            holidays = (List<Holiday>) q.list();
            session.disconnect();
            if (holidays != null && holidays.size() > 0) {
                holidaylist = new Holiday[holidays.size()];
                i = 0;
                for (Holiday holiday : holidays) {
                    Holiday obj = new Holiday();
                    obj.setHolidayId(holiday.getHolidayId());
                    obj.setHoliday(holiday.getHoliday());
                    obj.setHolidayType(holiday.getHolidayType());
                    holidaylist[i] = obj;
                    i++;
                }
            }
            return holidaylist;

        } catch (Exception e) {
            e.printStackTrace();
            holidaylist = new Holiday[1];
            return holidaylist;
        }
    }
}
