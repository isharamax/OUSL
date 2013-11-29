/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.UserRole;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
public class AdminUserRoleService {
    
    Session session = null;
    private Short userRoleID;
    private String userRoleName;
    UserRole[] userRolelist = null;
    int i;
    
    public AdminUserRoleService() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public Short getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(Short userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String addUserRole(UserRole userRole) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            UserRole add = new UserRole();
            add.setRoleId(userRole.getRoleId());
            add.setRoleName(userRole.getRoleName());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editUserRole(UserRole userRole) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            UserRole update = new UserRole();
            update.setRoleId(userRole.getRoleId());
            update.setRoleName(userRole.getRoleName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteUserRole(UserRole userRole) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            UserRole del = new UserRole();
            del.setRoleId(userRole.getRoleId());
            del.setRoleName(userRole.getRoleName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastUserRoleID() {
        Short lastCId = 0;
        List<UserRole> userRoles = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(roleId) FROM UserRole");
            userRoles = (List<UserRole>) q.list();
            session.disconnect();
            if (userRoles != null && userRoles.size() > 0) {
                lastCId = userRoles.get(userRoles.size() - 1).getRoleId();
            }
            return lastCId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastCId;
        }
    }
        
    public UserRole[] searchAllUserRoles() {
        List<UserRole> userRoles = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM UserRole");
            userRoles = (List<UserRole>) q.list();
            session.disconnect();
            if (userRoles != null && userRoles.size() > 0) {
                userRolelist = new UserRole[userRoles.size()];
                i = 0;
                for (UserRole userRole : userRoles) {
                    UserRole obj = new UserRole();
                    obj.setRoleId(userRole.getRoleId());
                    obj.setRoleName(userRole.getRoleName());
                    userRolelist[i] = obj;
                    i++;
                }
            }
            return userRolelist;

        } catch (Exception e) {
            e.printStackTrace();
            userRolelist = new UserRole[1];
            return userRolelist;
        }
    }
    
    
    public UserRole[] searchUserRolesByFilter(String Filter) {
        List<UserRole> userRoles = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM UserRole U where U.roleName like '%"+Filter+"%' ");
            userRoles = (List<UserRole>) q.list();
            session.disconnect();
            if (userRoles != null && userRoles.size() > 0) {
                userRolelist = new UserRole[userRoles.size()];
                i = 0;
                for (UserRole userRole : userRoles) {
                    UserRole obj = new UserRole();
                    obj.setRoleId(userRole.getRoleId());
                    obj.setRoleName(userRole.getRoleName());
                    userRolelist[i] = obj;
                    i++;
                }
            }
            return userRolelist;

        } catch (Exception e) {
            e.printStackTrace();
            userRolelist = new UserRole[1];
            return userRolelist;
        }
    }
}
