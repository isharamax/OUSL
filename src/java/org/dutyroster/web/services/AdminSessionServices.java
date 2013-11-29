/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Sessioninfo;
import org.dutyroster.web.util.Constants;


/**
 *
 * @author Administrator
 */

public class AdminSessionServices {
    
    Session session = null;
    private Short sessionId;
    private String sessionDesc;
    private Float fromTime;
    private Float toTime;
    Sessioninfo[] sessioninfolist = null;
    int i;
    int result = 0;

    public Float getFromTime() {
        return fromTime;
    }

    public void setFromTime(Float fromTime) {
        this.fromTime = fromTime;
    }

    public Float getToTime() {
        return toTime;
    }

    public void setToTime(Float toTime) {
        this.toTime = toTime;
    }
    

  

    public String getSessionDesc() {
        return sessionDesc;
    }

    public void setSessionDesc(String sessionDesc) {
        this.sessionDesc = sessionDesc;
    }

    public Short getSessionId() {
        return sessionId;
    }

    public void setSessionId(Short sessionId) {
        this.sessionId = sessionId;
    }

   
    
    public AdminSessionServices() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public String addSession(Sessioninfo sessionInfo) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Sessioninfo add = new Sessioninfo();
            add.setSessionId(sessionInfo.getSessionId());
            add.setSessionDesc(sessionInfo.getSessionDesc());
           add.setFromTime(sessionInfo.getFromTime());
            add.setToTime(sessionInfo.getToTime());
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;    
        }
        return Constants.SUCCESS;
    }

    public String editSessioninfos(Sessioninfo sessioninfo) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.createQuery("UPDATE Center SET centerName= :cName WHERE centerId = :cID");
            //q.setString("cName", center.getCenterName());
            //q.setString("cID", center.getCenterId().toString());
            //result = q.executeUpdate();
            Sessioninfo update = new Sessioninfo();
            update.setSessionId(sessioninfo.getSessionId());
            update.setSessionDesc(sessioninfo.getSessionDesc());
             update.setFromTime(sessioninfo.getFromTime());
              update.setToTime(sessioninfo.getToTime());
            
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteSessioninfos(Sessioninfo sessioninfo) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Sessioninfo del = new Sessioninfo();
            del.setSessionId(sessioninfo.getSessionId());
            del.setSessionDesc(sessioninfo.getSessionDesc());
            del.setFromTime(sessioninfo.getFromTime());
            del.setToTime(sessioninfo.getToTime());
            
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastSessionId() {
        Short lastSeId = 0;
        List<Sessioninfo> sessioninfos = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(sessionId) FROM Sessioninfo");
            sessioninfos = (List<Sessioninfo>) q.list();
            session.disconnect();
            if (sessioninfos != null && sessioninfos.size() > 0) {
                lastSeId = sessioninfos.get(sessioninfos.size() - 1).getSessionId();
            }
            return lastSeId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastSeId;
        }
    }
        
    public Sessioninfo[] searchAllSessioninfos() {
        List<Sessioninfo> sessioninfos = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Sessioninfo");
            sessioninfos = (List<Sessioninfo>) q.list();
            session.disconnect();
            if (sessioninfos != null && sessioninfos.size() > 0) {
                sessioninfolist = new Sessioninfo[sessioninfos.size()];
                i = 0;
                for (Sessioninfo sessioninfo : sessioninfos) {
                    Sessioninfo obj = new Sessioninfo();
                    obj.setSessionId(sessioninfo.getSessionId());
                    obj.setSessionDesc(sessioninfo.getSessionDesc());
                    obj.setFromTime(sessioninfo.getFromTime());
                    obj.setToTime(sessioninfo.getToTime());
                    
                    sessioninfolist[i] = obj;
                    i++;
                }
            }
            return sessioninfolist;

        } catch (Exception e) {
            e.printStackTrace();
            sessioninfolist = new Sessioninfo[1];
            return sessioninfolist;
        }
    }
    
    
    public Sessioninfo[] searchSessionsByFilter(String Filter) {
        List<Sessioninfo> sessioninfos = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Sessioninfo Se where Se.sessionDesc like '%"+Filter+"%' ");
            //Query q = session.createQuery("from Center C where (C.centerId like :cID or C.centerName like :cName ) ");
            //q.setString("cID", "%"+center.getCenterId().toString()+"%");
            //q.setString("cName", "%"+center.getCenterName()+"%");
            sessioninfos = (List<Sessioninfo>) q.list();
            session.disconnect();
            if (sessioninfos != null && sessioninfos.size() > 0) {
                sessioninfolist = new Sessioninfo[sessioninfos.size()];
                i = 0;
                for (Sessioninfo se : sessioninfos) {
                    Sessioninfo obj = new Sessioninfo();
                    obj.setSessionId(se.getSessionId());
                    obj.setSessionDesc(se.getSessionDesc());
                    obj.setFromTime(se.getFromTime());
                    obj.setToTime(se.getToTime());
                    
                    sessioninfolist[i] = obj;
                    i++;
                }
            }
            return sessioninfolist;

        } catch (Exception e) {
            e.printStackTrace();
            sessioninfolist = new Sessioninfo[1];
            return sessioninfolist;
        }
    }
}
