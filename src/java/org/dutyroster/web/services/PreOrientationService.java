/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.PreOrientation;
import org.dutyroster.hibernate.database.PreOrientationStaff;
import org.dutyroster.hibernate.database.PreOrientationStaffId;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.web.dto.PreOrienatationStaffDTO;
import org.dutyroster.web.dto.PreOrientationApprovalDTO;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author mfh684
 */
public class PreOrientationService implements Serializable {

    private static final long serialVersionUID = 1L;
    private org.hibernate.Session session;

    public PreOrientationService() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public boolean savePreOrientation(StaffMember staffMember, PreOrientation preorientation) {
        boolean result = false;


        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {

            PreOrientationStaff pos = null;

            PreOrientationStaffId id = new PreOrientationStaffId();
            id.setPreId(preorientation);
            id.setStaffId(staffMember);
            //pos.setId(id);
            //pos.setApplyStatus("Pending");
             pos = (PreOrientationStaff) session.get(PreOrientationStaff.class, id);
             if (pos==null) {
                pos = new PreOrientationStaff();
                pos.setId(id);
                pos.setApplyStatus("Pending");
                session.save(pos);
                result = true;
            }else{
                 result = false;
             }
           // session.saveOrUpdate(pos);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return result;
    }

    public List<PreOrientation> getPreorientationforDepartment(Department department) {
        List<PreOrientation> list = new ArrayList<PreOrientation>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program "
                    + "left join fetch p.session "
                    + "left join fetch p.center "
                    + "where pd=:deptId");
            q.setEntity("deptId", department);
            list = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }

    public List<PreOrientation> getProgramsByDept(Department department) {
        List<PreOrientation> list = new ArrayList<PreOrientation>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program "
                    + "where pd=:deptId");
            q.setEntity("deptId", department);
            list = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }

    public List<Center> getCentersbyDeptPrg(Department deptId, Program prgId) {
        List<Center> centerList = new ArrayList<Center>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program pp "
                    + "left join fetch p.center "
                    + "where pd=:deptId and pp=:prgId");
            q.setEntity("deptId", deptId);
            q.setEntity("prgId", prgId);
            List<PreOrientation> list = q.list();
            for (PreOrientation preOrientation : list) {
                centerList.add(preOrientation.getCenter());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return centerList;
    }

    public List<Session> getSessbyDeptPrgCent(Department deptId, Program prgId, Center centId) {
        List<Session> sessionList = new ArrayList<Session>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program pp "
                    + "left join fetch p.session "
                    + "left join fetch p.center pc "
                    + "where pd=:deptId and pp=:prgId and pc=:centId");
            q.setEntity("deptId", deptId);
            q.setEntity("prgId", prgId);
            q.setEntity("centId", centId);

            List<PreOrientation> list = q.list();
            for (PreOrientation preOrientation : list) {
                sessionList.add(preOrientation.getSession());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return sessionList;
    }

    public List<String> getDatesbyDeptPrgCentSess(Department deptId, Program prgId, Center centId, Session sessId) {
        List<String> dateList = new ArrayList<String>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program pp "
                    + "left join fetch p.session ps "
                    + "left join fetch p.center pc "
                    + "where pd=:deptId and pp=:prgId and pc=:centId and ps=:sessId");
            q.setEntity("deptId", deptId);
            q.setEntity("prgId", prgId);
            q.setEntity("centId", centId);
            q.setEntity("sessId", sessId);
            List<PreOrientation> list = q.list();
            for (PreOrientation preOrientation : list) {
                dateList.add(preOrientation.getPreDate().toString());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return dateList;
    }

    public PreOrientation getPre(Department deptId, Program prgId, Session sessId, Center centId, Date date) {
        PreOrientation orientation = null;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        System.out.println(this.getClass().getCanonicalName() + " Date is : " + date);

        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientation p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program pp "
                    + "left join fetch p.session ps "
                    + "left join fetch p.center pc "
                    + "where pd=:deptId and pp=:prgId and pc=:centId and ps=:sessId "
                    + "and p.preDate=:preDate");
            q.setEntity("deptId", deptId);
            q.setEntity("prgId", prgId);
            q.setEntity("centId", centId);
            q.setEntity("sessId", sessId);
            //DateFormat sysDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

//String date_to_store = sysDate.format(jXDatePicker1.getDate()).toString();
            q.setDate("preDate", date);
            orientation = (PreOrientation) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return orientation;

    }

    public List<PreOrientationStaff> getPreorientationStaffbyStaffId(Short staffId) {
        List<PreOrientationStaff> list = new ArrayList<PreOrientationStaff>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientationStaff ps where ps.id.staffId=:staffId");
            q.setShort("staffId", staffId);

            list = q.list();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }

    public List<PreOrientationApprovalDTO> getUnApprovedPreorienatations(Date fromDate, Date toDate) {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        List<PreOrientationApprovalDTO> preOrList = new ArrayList<PreOrientationApprovalDTO>();
        Transaction tx = session.beginTransaction();
        try {
            List<PreOrientationStaff> staffs = new ArrayList<PreOrientationStaff>();
            Query q = session.createQuery("from PreOrientationStaff ps "
                    + "left join fetch ps.id.preId psp "
                    + "left join fetch ps.id.staffId pss "
                    + "left join fetch psp.department "
                    + "left join fetch psp.program "
                    + "left join fetch psp.session "
                    + "left join fetch psp.center "
                    + "where (ps.applyStatus <> 'Approved' and ps.applyStatus <> 'Rejected') and psp.preDate between :startDate and :endDate ");
            q.setDate("endDate", toDate);
            q.setDate("startDate", fromDate);
            staffs = q.list();
            PreOrientationApprovalDTO dTO;
            for (PreOrientationStaff p : staffs) {
                dTO = new PreOrientationApprovalDTO();
                dTO.setPos(p);
                preOrList.add(dTO);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return preOrList;
    }

    public List<PreOrientationStaff> getPreOrientations(StaffMember staffId, Date fromDate, Date toDate) {
        List<PreOrientationStaff> results = new ArrayList<PreOrientationStaff>();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientationStaff ps "
                    + "left join fetch ps.id.preId psp "
                    + "left join fetch ps.id.staffId pss "
                    + "left join fetch psp.department "
                    + "left join fetch psp.program "
                    + "left join fetch psp.session "
                    + "left join fetch psp.center "
                    + "where pss=:staffId and psp.preDate between :startDate and :endDate ");
            q.setEntity("staffId", staffId);
            q.setDate("startDate", fromDate);
            q.setDate("endDate", toDate);
            results = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return results;
    }
//    public List<PreOrientationStaff> getPreStaff() {
//        List<PreOrientationStaff> results = new ArrayList<PreOrientationStaff>();
//        if (!session.isOpen()) {
//            session = HibernateUtil.getSessionFactory().getCurrentSession();
//        }
//        Transaction tx = session.beginTransaction();
//        try {
//            Query q = session.createQuery("from PreOrientationStaff ps "
//                    + "left join fetch ps.id.preId as psp "
//                    + "left join fetch ps.id.staffId as pss "
//                    + "left join fetch psp.department "
//                    + "left join fetch psp.program "
//                    + "left join fetch psp.session "
//                    + "left join fetch psp.center "
//                    + "where ps.applyStatus <> 'Approved'");
//            results = q.list();
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        }
//        return results;
//    }

    public void updatePreOrientation(PreOrientationStaff pos, String status) {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            pos.setApplyStatus(status);
            session.saveOrUpdate(pos);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<PreOrienatationStaffDTO> getPreorientationStaff(StaffMember staffMember, Date filterDate) {
        List<PreOrienatationStaffDTO> dTOs = new ArrayList<PreOrienatationStaffDTO>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from PreOrientationStaff ps "
                    + "left join fetch ps.id.preId psp "
                    + "left join fetch ps.id.staffId pss "
                    + "left join fetch psp.department "
                    + "left join fetch psp.program "
                    + "left join fetch psp.session "
                    + "left join fetch psp.center "
                    + "where pss=:staffMember and psp.preDate=:preDate and (ps.applyStatus <> 'Approved' and ps.applyStatus <> 'Rejected')");
            q.setEntity("staffMember", staffMember);
            q.setDate("preDate", filterDate);
            List<PreOrientationStaff> list = q.list();
            PreOrienatationStaffDTO o;
            for (PreOrientationStaff preOrientationStaff : list) {
                o = new PreOrienatationStaffDTO();
                o.setPos(preOrientationStaff);
                o.setChecked(false);
                dTOs.add(o);

            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return dTOs;
    }

    public List<PreOrienatationStaffDTO> getPreorientationStaffbyStaffId(StaffMember staffMember, String criteriaValue, String userCriteriaValue) {
        List<PreOrienatationStaffDTO> dTOs = new ArrayList<PreOrienatationStaffDTO>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q;
            StringBuilder sb = new StringBuilder();
            sb.append("from PreOrientationStaff ps ");
            sb.append("left join fetch ps.id.preId psp ");
            sb.append("left join fetch ps.id.staffId pss ");
            sb.append("left join fetch psp.department ");
            sb.append("left join fetch psp.program pspp ");
            sb.append("left join fetch psp.session psps ");
            sb.append("left join fetch psp.center pspc ");
            sb.append("where (ps.applyStatus <> 'Approved' and ps.applyStatus <> 'Rejected') and ");
            List<PreOrientationStaff> list = new ArrayList<PreOrientationStaff>();
            if (criteriaValue.equals("Program")) {
                sb.append("pspp.programName like :program");
                q = session.createQuery(sb.toString());
                q.setString("program", "%" + userCriteriaValue + "%");
                list = q.list();
            }
            if (criteriaValue.equals("Center")) {
                sb.append("pspc.centerName like :center");
                q = session.createQuery(sb.toString());
                q.setString("center", "%" + userCriteriaValue + "%");
                list = q.list();
            }
            if (criteriaValue.equals("Session")) {
                sb.append("psps.sessionDesc like :session");

                q = session.createQuery(sb.toString());
                q.setString("session", "%" + userCriteriaValue + "%");
                list = q.list();
            }

            PreOrienatationStaffDTO o;
            for (PreOrientationStaff preOrientationStaff : list) {
                o = new PreOrienatationStaffDTO();
                o.setPos(preOrientationStaff);
                o.setChecked(false);
                dTOs.add(o);

            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return dTOs;
    }

    public PreOrientationStaff getPreorientation(Short staffId, Short preOrientataionID) {

        PreOrientationStaff orientationStaff = new PreOrientationStaff();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {
            Query q = session.createQuery("from PreOrientationStaff ps "
                    + "left join fetch ps.id.preId psp "
                    + "left join fetch ps.id.staffId pss "
                    + "left join fetch psp.department "
                    + "left join fetch psp.program "
                    + "left join fetch psp.session "
                    + "left join fetch psp.center "
                    + "where psp.preId=:preOrientataionID and pss.staffId=:staffId");
            q.setShort("staffId", staffId);
            q.setShort("preOrientataionID", preOrientataionID);
            orientationStaff = (PreOrientationStaff) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return orientationStaff;
    }

    public boolean updatePreorientationStaff(PreOrientationStaff currentPreorientation, PreOrientation pre, StaffMember staffMember) {
        boolean result = false;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {
            session.delete(currentPreorientation);
            PreOrientationStaff pos = new PreOrientationStaff();
            PreOrientationStaffId id = new PreOrientationStaffId();
            id.setPreId(pre);
            id.setStaffId(staffMember);
            pos.setId(id);
            pos.setApplyStatus("Pending");
            pos.setPoints(Short.valueOf("0"));
            session.save(pos);
            result = true;
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            result = false;
        }
        return result;

    }

    public void deletePreOrientationStaff(PreOrientationStaff currentPreorientation) {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {
            session.delete(currentPreorientation);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
}
