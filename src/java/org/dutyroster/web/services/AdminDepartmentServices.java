/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.web.util.Constants;


/**
 *
 * @author Administrator
 */
public class AdminDepartmentServices {
    
     Session session = null;
     private Short deptId;
     private String deptName;
     private String deptHead;
     Department[] departmentlist = null;
    int i;
    int result = 0;
     
     public AdminDepartmentServices() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
     

    public String getDeptHead() {
        return deptHead;
    }

    public void setDeptHead(String deptHead) {
        this.deptHead = deptHead;
    }

    public Short getDeptId() {
        return deptId;
    }

    public void setDeptId(Short deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
     
     
     public String addDepartment(Department department) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Department add = new Department();
            add.setDeptId(department.getDeptId());
            add.setDeptName(department.getDeptName());
            add.setDeptHead(department.getDeptHead());
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;    
        }
        return Constants.SUCCESS;
    }
    
     public String editDepartment(Department department) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.createQuery("UPDATE Center SET centerName= :cName WHERE centerId = :cID");
            //q.setString("cName", center.getCenterName());
            //q.setString("cID", center.getCenterId().toString());
            //result = q.executeUpdate();
            Department update = new Department();
            update.setDeptId(department.getDeptId());
            update.setDeptName(department.getDeptName());
            update.setDeptHead(department.getDeptHead());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }
     
      public Department[] searchAllDepartments() {
        List<Department> departments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Department");
            departments = (List<Department>) q.list();
            session.disconnect();
            if (departments != null && departments.size() > 0) {
                departmentlist = new Department[departments.size()];
                i = 0;
                for (Department department : departments) {
                    Department obj = new Department();
                    obj.setDeptId(department.getDeptId());
                    obj.setDeptName(department.getDeptName());
                    obj.setDeptHead(department.getDeptHead());
                    departmentlist[i] = obj;
                    i++;
                }
            }
            return departmentlist;

        } catch (Exception e) {
            e.printStackTrace();
            departmentlist = new Department[1];
            return departmentlist;
        }
    }
    
      public String deleteDepartment(Department department) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Department del = new Department();
            del.setDeptId(department.getDeptId());
            del.setDeptName(department.getDeptName());
            del.setDeptHead(department.getDeptHead());
            
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }
      
      public Short getLastDepartmentID() {
        Short lastDeptId = 0;
        List<Department> departments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(deptId) FROM Department");
            departments = (List<Department>) q.list();
            session.disconnect();
            if (departments != null && departments.size() > 0) {
                lastDeptId = departments.get(departments.size() - 1).getDeptId();
            }
            return lastDeptId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastDeptId;
        }
    }
      
      public Department[] searchDepartmentsByFilter(String Filter) {
        List<Department> departments = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Department D where D.deptName like '%"+Filter+"%' ");
            //Query q = session.createQuery("from Center C where (C.centerId like :cID or C.centerName like :cName ) ");
            //q.setString("cID", "%"+center.getCenterId().toString()+"%");
            //q.setString("cName", "%"+center.getCenterName()+"%");
            departments = (List<Department>) q.list();
            session.disconnect();
            if (departments != null && departments.size() > 0) {
                departmentlist = new Department[departments.size()];
                i = 0;
                for (Department dept : departments) {
                    Department obj = new Department();
                    obj.setDeptId(dept.getDeptId());
                    obj.setDeptName(dept.getDeptName());
                    departmentlist[i] = obj;
                    i++;
                }
            }
            return departmentlist;

        } catch (Exception e) {
            e.printStackTrace();
            departmentlist = new Department[1];
            return departmentlist;
        }
    }
}
