/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Workstation;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
public class AdminWorkstationService {

    Session session = null;
    private Short workstationID;
    private String workstationName;
    Workstation[] workstationlist = null;
    int i;
    int result = 0;

    public AdminWorkstationService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Short getWorkstationID() {
        return workstationID;
    }

    public void setWorkstationID(Short workstationID) {
        this.workstationID = workstationID;
    }

    public String getWorkstationName() {
        return workstationName;
    }

    public void setWorkstationName(String workstationName) {
        this.workstationName = workstationName;
    }

    public String addWorkstation(Workstation workstation) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Workstation add = new Workstation();
            add.setWorkstationId(workstation.getWorkstationId());
            add.setWorkstationName(workstation.getWorkstationName());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editWorkstation(Workstation workstation) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Workstation update = new Workstation();
            update.setWorkstationId(workstation.getWorkstationId());
            update.setWorkstationName(workstation.getWorkstationName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteWorkstation(Workstation workstation) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Workstation del = new Workstation();
            del.setWorkstationId(workstation.getWorkstationId());
            del.setWorkstationName(workstation.getWorkstationName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastWorkstationID() {
        Short lastCId = 0;
        List<Workstation> workstations = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(workstationId) FROM Workstation");
            workstations = (List<Workstation>) q.list();
            session.disconnect();
            if (workstations != null && workstations.size() > 0) {
                lastCId = workstations.get(workstations.size() - 1).getWorkstationId();
            }
            return lastCId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastCId;
        }
    }

    public Workstation[] searchAllWorkstations() {
        List<Workstation> workstations = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Workstation");
            workstations = (List<Workstation>) q.list();
            session.disconnect();
            if (workstations != null && workstations.size() > 0) {
                workstationlist = new Workstation[workstations.size()];
                i = 0;
                for (Workstation workstation : workstations) {
                    Workstation obj = new Workstation();
                    obj.setWorkstationId(workstation.getWorkstationId());
                    obj.setWorkstationName(workstation.getWorkstationName());
                    workstationlist[i] = obj;
                    i++;
                }
            }
            return workstationlist;

        } catch (Exception e) {
            e.printStackTrace();
            workstationlist = new Workstation[1];
            return workstationlist;
        }
    }

    public Workstation[] searchWorkstationsByFilter(String Filter) {
        List<Workstation> workstations = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Workstation W where W.workstationName like '%"+Filter+"%' ");
            workstations = (List<Workstation>) q.list();
            session.disconnect();
            if (workstations != null && workstations.size() > 0) {
                workstationlist = new Workstation[workstations.size()];
                i = 0;
                for (Workstation workstation : workstations) {
                    Workstation obj = new Workstation();
                    obj.setWorkstationId(workstation.getWorkstationId());
                    obj.setWorkstationName(workstation.getWorkstationName());
                    workstationlist[i] = obj;
                    i++;
                }
            }
            return workstationlist;

        } catch (Exception e) {
            e.printStackTrace();
            workstationlist = new Workstation[1];
            return workstationlist;
        }
    }
}
