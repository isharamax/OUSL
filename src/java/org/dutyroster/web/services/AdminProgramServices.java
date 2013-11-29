/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
public class AdminProgramServices {

    Session session = null;
    private Short programId;
    private String programName;
    private String dayTypeName;
    Program[] programlist = null;
    int i;
    int result = 0;

    public String getDayTypeName() {
        return dayTypeName;
    }

    public void setDayTypeName(String dayTypeName) {
        this.dayTypeName = dayTypeName;
    }

    public Short getProgramId() {
        return programId;
    }

    public void setProgramId(Short programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public AdminProgramServices() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public String addProgram(Program program) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Program add = new Program();
            add.setProgramId(program.getProgramId());
            add.setProgramName(program.getProgramName());
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }

    public String editProgram(Program program) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.createQuery("UPDATE Center SET centerName= :cName WHERE centerId = :cID");
            //q.setString("cName", center.getCenterName());
            //q.setString("cID", center.getCenterId().toString());
            //result = q.executeUpdate();
            Program update = new Program();
            update.setProgramId(program.getProgramId());
            update.setProgramName(program.getProgramName());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteProgram(Program program) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Program del = new Program();
            del.setProgramId(program.getProgramId());
            del.setProgramName(program.getProgramName());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastProgramID() {
        Short lastPId = 0;
        List<Program> programs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(programId) FROM Program");
            programs = (List<Program>) q.list();
            session.disconnect();
            if (programs != null && programs.size() > 0) {
                lastPId = programs.get(programs.size() - 1).getProgramId();
            }
            return lastPId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastPId;
        }
    }

    public Program[] searchAllPrograms() {
        List<Program> programs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Program");
            programs = (List<Program>) q.list();
            session.disconnect();
            if (programs != null && programs.size() > 0) {
                programlist = new Program[programs.size()];
                i = 0;
                for (Program program : programs) {
                    Program obj = new Program();
                    obj.setProgramId(program.getProgramId());
                    obj.setProgramName(program.getProgramName());
                    programlist[i] = obj;
                    i++;
                }
            }
            return programlist;

        } catch (Exception e) {
            e.printStackTrace();
            programlist = new Program[1];
            return programlist;
        }
    }

    public Program[] searchProgramsByFilter(String Filter) {
        List<Program> programs = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Program P where P.programName like '%" + Filter + "%' ");
            //Query q = session.createQuery("from Center C where (C.centerId like :cID or C.centerName like :cName ) ");
            //q.setString("cID", "%"+center.getCenterId().toString()+"%");
            //q.setString("cName", "%"+center.getCenterName()+"%");
            programs = (List<Program>) q.list();
            session.disconnect();
            if (programs != null && programs.size() > 0) {
                programlist = new Program[programs.size()];
                i = 0;
                for (Program p : programs) {
                    Program obj = new Program();
                    obj.setProgramId(p.getProgramId());
                    obj.setProgramName(p.getProgramName());
                    programlist[i] = obj;
                    i++;
                }
            }
            return programlist;

        } catch (Exception e) {
            e.printStackTrace();
            programlist = new Program[1];
            return programlist;
        }
    }
}
