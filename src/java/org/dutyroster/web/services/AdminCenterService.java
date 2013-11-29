/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
public class AdminCenterService {

    Session session = null;
    private Short centerID;
    private String centerName;
    Center[] centerlist = null;
    int i;

    public AdminCenterService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Short getcenterID() {
        return centerID;
    }

    public void setcenterID(Short centerID) {
        this.centerID = centerID;
    }

    public String getcenterName() {
        return centerName;
    }

    public void setcenterName(String centerName) {
        this.centerName = centerName;
    }
    
    public String addCenter(Center center) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Center add = new Center();
            add.setCenterId(center.getCenterId());
            add.setCenterName(center.getCenterName());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editCenter(Center center) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.createQuery("UPDATE Center SET centerName= :cName WHERE centerId = :cID");
            //q.setString("cName", center.getCenterName());
            //q.setString("cID", center.getCenterId().toString());
            //result = q.executeUpdate();
            Center update = new Center();
            update.setCenterId(center.getCenterId());
            update.setCenterName(center.getCenterName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteCenter(Center center) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Center del = new Center();
            del.setCenterId(center.getCenterId());
            del.setCenterName(center.getCenterName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastCenterID() {
        Short lastCId = 0;
        List<Center> centers = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(centerId) FROM Center");
            centers = (List<Center>) q.list();
            session.disconnect();
            if (centers != null && centers.size() > 0) {
                lastCId = centers.get(centers.size() - 1).getCenterId();
            }
            return lastCId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastCId;
        }
    }
        
    public Center[] searchAllCenters() {
        List<Center> centers = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Center");
            centers = (List<Center>) q.list();
            session.disconnect();
            if (centers != null && centers.size() > 0) {
                centerlist = new Center[centers.size()];
                i = 0;
                for (Center center : centers) {
                    Center obj = new Center();
                    obj.setCenterId(center.getCenterId());
                    obj.setCenterName(center.getCenterName());
                    centerlist[i] = obj;
                    i++;
                }
            }
            return centerlist;

        } catch (Exception e) {
            e.printStackTrace();
            centerlist = new Center[1];
            return centerlist;
        }
    }
    
    
    public Center[] searchCentersByFilter(String Filter) {
        List<Center> centers = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Center C where C.centerName like '%"+Filter+"%' ");
            //Query q = session.createQuery("from Center C where (C.centerId like :cID or C.centerName like :cName ) ");
            //q.setString("cID", "%"+center.getCenterId().toString()+"%");
            //q.setString("cName", "%"+center.getCenterName()+"%");
            centers = (List<Center>) q.list();
            session.disconnect();
            if (centers != null && centers.size() > 0) {
                centerlist = new Center[centers.size()];
                i = 0;
                for (Center c : centers) {
                    Center obj = new Center();
                    obj.setCenterId(c.getCenterId());
                    obj.setCenterName(c.getCenterName());
                    centerlist[i] = obj;
                    i++;
                }
            }
            return centerlist;

        } catch (Exception e) {
            e.printStackTrace();
            centerlist = new Center[1];
            return centerlist;
        }
    }
}
