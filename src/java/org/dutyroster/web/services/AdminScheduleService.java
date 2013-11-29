/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.LearningToLearn;
import org.dutyroster.hibernate.database.PreOrientation;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Registration;
import org.dutyroster.hibernate.database.RegistrationType;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.hibernate.database.Sessioninfo;
import org.dutyroster.web.util.Constants;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author mfh684
 */
public class AdminScheduleService implements Serializable {

    private static final long serialVersionUID = 1L;
    private org.hibernate.Session session;

    private short regId;
    private Department department;
    private Program program;
    private Sessioninfo sessioninfo;
    private RegistrationType registrationType;
    private Center center;
    private Date regDate;
    private short regYear;
    private String distribution;
    Registration[] registrationlist = null;
    int i;


    public short getRegId() {
        return regId;
    }

    public void setRegId(short regId) {
        this.regId = regId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Sessioninfo getSessioninfo() {
        return sessioninfo;
    }

    public void setSessioninfo(Sessioninfo sessioninfo) {
        this.sessioninfo = sessioninfo;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public short getRegYear() {
        return regYear;
    }

    public void setRegYear(short regYear) {
        this.regYear = regYear;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String addRegistration(Registration registration) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Registration add = new Registration();
            add.setRegId(registration.getRegId());
            add.setRegDate(registration.getRegDate());
            add.setRegYear(registration.getRegYear());
            add.setProgram(registration.getProgram());
            add.setDepartment(registration.getDepartment());
            add.setCenter(registration.getCenter());
            add.setSessioninfo(registration.getSessioninfo());
            add.setRegistrationType(registration.getRegistrationType());
            add.setDistribution(registration.getDistribution());
            session.saveOrUpdate(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editRegistration(Registration registration) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Registration update = new Registration();
            update.setRegId(registration.getRegId());
            update.setRegDate(registration.getRegDate());
            update.setRegYear(registration.getRegYear());
            update.setProgram(registration.getProgram());
            update.setDepartment(registration.getDepartment());
            update.setCenter(registration.getCenter());
            update.setSessioninfo(registration.getSessioninfo());
            update.setRegistrationType(registration.getRegistrationType());
            update.setDistribution(registration.getDistribution());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteRegistration(Registration registration) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Registration del = new Registration();
            del.setRegId(registration.getRegId());
            del.setRegDate(registration.getRegDate());
            del.setRegYear(registration.getRegYear());
            del.setProgram(registration.getProgram());
            del.setDepartment(registration.getDepartment());
            del.setCenter(registration.getCenter());
            del.setSessioninfo(registration.getSessioninfo());
            del.setRegistrationType(registration.getRegistrationType());
            del.setDistribution(registration.getDistribution());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastRegistrationID() {
        Short lastCId = 0;
        List<Registration> registrations = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(regId) FROM Registration");
            registrations = (List<Registration>) q.list();
            session.disconnect();
            if (registrations != null && registrations.size() > 0) {
                lastCId = registrations.get(registrations.size() - 1).getRegId();
            }
            return lastCId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastCId;
        }
    }

    public Registration[] searchAllRegistrations() {
        List<Registration> registrations = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Registration");
            registrations = (List<Registration>) q.list();
            session.disconnect();
            if (registrations != null && registrations.size() > 0) {
                registrationlist = new Registration[registrations.size()];
                i = 0;
                for (Registration registration : registrations) {
                    Registration obj = new Registration();
                    obj.setRegId(registration.getRegId());
                    obj.setRegDate(registration.getRegDate());
                    obj.setRegYear(registration.getRegYear());
                    obj.setProgram(registration.getProgram());
                    obj.setDepartment(registration.getDepartment());
                    obj.setCenter(registration.getCenter());
                    obj.setSessioninfo(registration.getSessioninfo());
                    obj.setRegistrationType(registration.getRegistrationType());
                    obj.setDistribution(registration.getDistribution());
                    registrationlist[i] = obj;
                    i++;
                }
            }
            return registrationlist;

        } catch (Exception e) {
            e.printStackTrace();
            registrationlist = new Registration[1];
            return registrationlist;
        }
    }

    public Registration[] searchRegistrationsByFilter(String Filter) {
        List<Registration> registrations = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Registration R where R.regDate = '%"+Filter+"%' ");
            registrations = (List<Registration>) q.list();
            session.disconnect();
            if (registrations != null && registrations.size() > 0) {
                registrationlist = new Registration[registrations.size()];
                i = 0;
                for (Registration registration : registrations) {
                    Registration obj = new Registration();
                    obj.setRegId(registration.getRegId());
                    obj.setRegDate(registration.getRegDate());
                    obj.setRegYear(registration.getRegYear());
                    obj.setProgram(registration.getProgram());
                    obj.setDepartment(registration.getDepartment());
                    obj.setCenter(registration.getCenter());
                    obj.setSessioninfo(registration.getSessioninfo());
                    obj.setRegistrationType(registration.getRegistrationType());
                    obj.setDistribution(registration.getDistribution());
                    registrationlist[i] = obj;
                    i++;
                }
            }
            return registrationlist;

        } catch (Exception e) {
            e.printStackTrace();
            registrationlist = new Registration[1];
            return registrationlist;
        }
    }
    public AdminScheduleService() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
     public List<Session> getSessions() {
        List<Session> sessionList = new ArrayList<Session>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Session");
            sessionList = q.list();
            tx.commit();
            sessionList = (sessionList == null) ? new ArrayList<Session>() : sessionList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return sessionList;
    }

    public List<Center> getCenters() {
        List<Center> centerList = new ArrayList<Center>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Center");
            centerList = q.list();
            tx.commit();
            centerList = (centerList == null) ? new ArrayList<Center>() : centerList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return centerList;
    }

    public List<Program> getPrograms() {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        List<Program> programList = new ArrayList<Program>();
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Program");
            programList = q.list();
            tx.commit();
            programList = (programList == null) ? new ArrayList<Program>() : programList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return programList;
    }
    
     public List<Department> getDepartments() {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        List<Department> deptList = new ArrayList<Department>();
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Department");
            deptList = q.list();
            tx.commit();
            deptList = (deptList == null) ? new ArrayList<Department>() : deptList;
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return deptList;
    }
     public void savePreOrientationEntry(Date date, Date year, Program program, Center center, Department dep, Session ses ){
       
        PreOrientation orientation = new PreOrientation();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            orientation.setCenter(center);
            orientation.setDepartment(dep);
            orientation.setPreDate(date);
            orientation.setPreYear(year);
            orientation.setProgram(program);
            orientation.setSession(ses);
            session.save(orientation);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
     }
     public void saveLearningtoLearn(Date date, Date year, Program program, Center center, Department dep, Session ses ){
         LearningToLearn learningToLearn = new LearningToLearn();
         if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            learningToLearn.setCenter(center);
            learningToLearn.setDepartment(dep);
            learningToLearn.setLerDate(date);
            learningToLearn.setLerYear(year);
            learningToLearn.setProgram(program);
            learningToLearn.setSession(ses);
            session.save(learningToLearn);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
         
     }

}
