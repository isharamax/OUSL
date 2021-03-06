/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.UserAccount;

/**
 *
 * @author Gift
 */
public class UserServices {

    Session session = null;
    UserAccount[] users = null;
    UserAccount A = new UserAccount();
    private String userName;
    int i;

    public UserServices() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public UserAccount[] getLoginInfo(String userName) {
        List<UserAccount> usersList = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("FROM UserAccount as U WHERE U.userName='" + userName + "'");
            usersList = (List<UserAccount>) q.list();
            if (usersList != null && usersList.size() > 0) {
                users = new UserAccount[usersList.size()];
                i = 0;
                for (UserAccount useraccount : usersList) {
                    UserAccount obj = new UserAccount();
                    obj.setStaffId(useraccount.getStaffId());
                    obj.setUserName(useraccount.getUserName());
                    obj.setPassword(useraccount.getPassword());
                    users[i] = obj;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            users = new UserAccount[1];
            return users;
        }
        return users;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
