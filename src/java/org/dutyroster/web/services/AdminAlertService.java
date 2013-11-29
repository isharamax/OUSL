/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Alert;
import org.dutyroster.web.util.Constants;
/**
 *
 * @author Gift
 */
public class AdminAlertService {
    
    Session session = null;
    private Short alertID;
    private String alertName;
    private String alertContent;
    Alert[] alertlist = null;
    int i;
    int result = 0;

    public AdminAlertService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Short getAlertID() {
        return alertID;
    }

    public void setAlertID(Short alertID) {
        this.alertID = alertID;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }
    
    public String addAlert(Alert alert) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Alert add = new Alert();
            add.setAlertId(alert.getAlertId());
            add.setAlertName(alert.getAlertName());
            add.setAlertBody(alert.getAlertBody());
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;    
        }
        return Constants.SUCCESS;
    }
    
    public String editAlert(Alert alert) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.createQuery("UPDATE Center SET centerName= :cName WHERE centerId = :cID");
            //q.setString("cName", center.getCenterName());
            //q.setString("cID", center.getCenterId().toString());
            //result = q.executeUpdate();
            Alert update = new Alert();
            update.setAlertId(alert.getAlertId());
            update.setAlertName(alert.getAlertName());
            update.setAlertBody(alert.getAlertBody());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }
    
    public String deleteAlert(Alert alert) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Alert del = new Alert();
            del.setAlertId(alert.getAlertId());
            del.setAlertName(alert.getAlertName());
            del.setAlertBody(alert.getAlertBody());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }
    
    public Short getLastalertID() {
        Short lastAId = 0;
        List<Alert> alerts = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(alertId) FROM Alert");
            alerts = (List<Alert>) q.list();
            session.disconnect();
            if (alerts != null && alerts.size() > 0)
            {
                lastAId= alerts.get(alerts.size()-1).getAlertId();
            }
            return lastAId;
            
        } catch (Exception e) {
            e.printStackTrace();
            return lastAId;
        }
    }
    
    public Alert[] searchAllAlerts() {
        List<Alert> alerts = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Alert");
            alerts = (List<Alert>) q.list();
            session.disconnect();
            if (alerts != null && alerts.size() > 0) {
                alertlist = new Alert[alerts.size()];
                i = 0;
                for (Alert alert : alerts) {
                    Alert obj = new Alert();
                    obj.setAlertId(alert.getAlertId());
                    obj.setAlertName(alert.getAlertName());
                    obj.setAlertBody(alert.getAlertBody());
                    alertlist[i] = obj;
                    i++;
                }
            }
            return alertlist;

        } catch (Exception e) {
            e.printStackTrace();
            alertlist = new Alert[1];
            return alertlist;
        }
    }
    
    public Alert[] searchAlertsByFilter(String Filter) {
        List<Alert> alerts = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Alert A where A.alertName like '%"+Filter+"%' ");
            alerts = (List<Alert>) q.list();
            session.disconnect();
            if (alerts != null && alerts.size() > 0) {
                alertlist = new Alert[alerts.size()];
                i = 0;
                for (Alert alert : alerts) {
                    Alert obj = new Alert();
                    obj.setAlertId(alert.getAlertId());
                    obj.setAlertName(alert.getAlertName());
                    obj.setAlertBody(alert.getAlertBody());
                    alertlist[i] = obj;
                    i++;
                }
            }
            return alertlist;

        } catch (Exception e) {
            e.printStackTrace();
            alertlist = new Alert[1];
            return alertlist;
        }
    }
    
}
