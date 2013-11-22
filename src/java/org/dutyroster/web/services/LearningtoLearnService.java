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
import org.dutyroster.hibernate.database.DayType;
import org.dutyroster.hibernate.database.DayTypeWeghtage;
import org.dutyroster.hibernate.database.DayTypeWeghtageId;
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.hibernate.database.Holiday;
import org.dutyroster.hibernate.database.LearningStaff;
import org.dutyroster.hibernate.database.LearningStaffCat;
import org.dutyroster.hibernate.database.LearningStaffCatId;
import org.dutyroster.hibernate.database.LearningStaffId;
import org.dutyroster.hibernate.database.LearningToLearn;
import org.dutyroster.hibernate.database.PreOrientationStaff;
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.hibernate.database.StaffDistribution;
import org.dutyroster.hibernate.database.StaffMember;
import org.dutyroster.hibernate.database.Workstation;
import org.dutyroster.hibernate.database.WorkstationCenter;
import org.dutyroster.hibernate.database.WorkstationCenterId;
import org.dutyroster.hibernate.database.WorkstationPriority;
import org.dutyroster.web.dto.LearningToLearnStaffDTO;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author mfh684
 */
public class LearningtoLearnService implements Serializable {

    private static final long serialVersionUID = 1L;
    private org.hibernate.Session session;

    public LearningtoLearnService() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public List<LearningToLearn> getLearningtoLearns(StaffMember staff, Date fromDate, Date toDate) {
        List<LearningToLearn> results = new ArrayList<LearningToLearn>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        System.out.println(fromDate);
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningStaff ls "
                    + "left join fetch ls.id.lerId lsil "
                    + "left join fetch ls.id.staffId lsi "
                    + "left join fetch lsil.department "
                    + "left join fetch lsil.program "
                    + "left join fetch lsil.session "
                    + "left join fetch lsil.center "
                    + "where lsi=:staffMember and lsil.lerDate between :startDate and :endDate");
            q.setEntity("staffMember", staff);
            q.setDate("startDate", fromDate);
            q.setDate("endDate", toDate);
            List<LearningStaff> learningStaff = q.list();
            for (LearningStaff ls : learningStaff) {
                results.add(ls.getId().getLerId());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return results;
    }

    public List<LearningToLearn> getLearningToLearnProgramsByDept(Department department) {
        List<LearningToLearn> list = new ArrayList<LearningToLearn>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningToLearn ll "
                    + "left join fetch ll.department lld "
                    + "left join fetch ll.program "
                    + "where lld=:dept");
            q.setEntity("dept", department);
            list = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;
    }

    public List<LearningStaff> getLearningtoLearnStaffbyStaffId(StaffMember member) {
        List<LearningStaff> list = new ArrayList<LearningStaff>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningStaff ls "
                    + "left join fetch ls.id.staffId lsi "
                    + "left join fetch ls.id.lerId lsl "
                    + "left join fetch lsi.staffCategory "
                    + "left join fetch lsl.department "
                    + "left join fetch lsl.program "
                    + "left join fetch lsl.session "
                    + "left join fetch lsl.center "
                    + "where lsi=:staff and (ls.applyStatus <> 'Approved' and ls.applyStatus <> 'Rejected')");
            q.setEntity("staff", member);
            list = q.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return list;

    }

    public List<Center> getCentersbyDeptPrg(Department department, Program attendedProgram) {
        List<Center> centerList = new ArrayList<Center>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningToLearn l "
                    + "left join fetch l.department pd "
                    + "left join fetch l.program pp "
                    + "left join fetch l.center "
                    + "where pd=:department and pp=:program");
            q.setEntity("department", department);
            q.setEntity("program", attendedProgram);
            List<LearningToLearn> list = q.list();
            for (LearningToLearn learToLer : list) {
                centerList.add(learToLer.getCenter());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return centerList;
    }

    public List<Session> getSessbyDeptPrgCent(Department department, Program attendedProgram, Center attendedCenter) {
        List<Session> sessionList = new ArrayList<Session>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningToLearn l "
                    + "left join fetch l.department pd "
                    + "left join fetch l.program pp "
                    + "left join fetch l.session "
                    + "left join fetch l.center pc "
                    + "where pd=:department and pp=:program and pc=:center");
            q.setEntity("department", department);
            q.setEntity("program", attendedProgram);
            q.setEntity("center", attendedCenter);

            List<LearningToLearn> list = q.list();
            for (LearningToLearn preOrientation : list) {
                sessionList.add(preOrientation.getSession());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return sessionList;
    }

    public List<Date> getDatesbyDeptPrgCentSess(Department department, Program attendedProgram, Center attendedCenter, Session attendedSession) {
        List<Date> dateList = new ArrayList<Date>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningToLearn p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program pp "
                    + "left join fetch p.session ps "
                    + "left join fetch p.center pc "
                    + "where pd=:department and pp=:program and pc=:center and ps=:session");
            q.setEntity("department", department);
            q.setEntity("program", attendedProgram);
            q.setEntity("center", attendedCenter);
            q.setEntity("session", attendedSession);
            List<LearningToLearn> list = q.list();

            for (LearningToLearn lerToLer : list) {
                dateList.add(lerToLer.getLerDate());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return dateList;
    }

    public void saveLearningToLearn(StaffMember staffMember, LearningToLearn learnin) {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {

            LearningStaff ler = new LearningStaff();

            LearningStaffId id = new LearningStaffId();
            id.setLerId(learnin);
            id.setStaffId(staffMember);
            ler.setId(id);
            ler.setApplyStatus("Pending");
            session.saveOrUpdate(ler);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public LearningToLearn getLearningToLearn(Department department, Program attendedProgram, Session attendedSession, Center attendedCenter, Date attendedDate) {
        LearningToLearn learningToLearn = null;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        System.out.println(this.getClass().getCanonicalName() + " Date is : " + attendedDate);

        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningToLearn p "
                    + "left join fetch p.department pd "
                    + "left join fetch p.program pp "
                    + "left join fetch p.session ps "
                    + "left join fetch p.center pc "
                    + "where pd=:department and pp=:program and pc=:center and ps=:session "
                    + "and p.lerDate=:date");
            q.setEntity("department", department);
            q.setEntity("program", attendedProgram);
            q.setEntity("center", attendedCenter);
            q.setEntity("session", attendedSession);
            q.setDate("date", attendedDate);
            learningToLearn = (LearningToLearn) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return learningToLearn;
    }

    public LearningStaff getLearningToLearn(Short staffId, Short learningToLearnID) {
        LearningStaff learningStaff = new LearningStaff();

        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {
            Query q = session.createQuery("from LearningStaff ps "
                    + "left join fetch ps.id.lerId psp "
                    + "left join fetch ps.id.staffId pss "
                    + "left join fetch psp.department "
                    + "left join fetch psp.program "
                    + "left join fetch psp.session "
                    + "left join fetch psp.center "
                    + "where psp.lerId=:learningToID and pss.staffId=:staffId");
            q.setShort("staffId", staffId);
            q.setShort("learningToID", learningToLearnID);
            learningStaff = (LearningStaff) q.list().get(0);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return learningStaff;
    }

    public boolean updateLearningStaff(LearningStaff currentLearningStaff, LearningToLearn ler, StaffMember staffMember) {
        boolean result = false;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {

            Query q = session.createQuery("from StaffDistribution sd join fetch sd.staffCategory sds "
                    + "where sds=:staffCategory and sd.scheduleType=:scheduleType");
            q.setEntity("staffCategory", staffMember.getStaffCategory());
            q.setString("scheduleType", "Learn");
            StaffDistribution distribution = (StaffDistribution) q.list().get(0);
            LearningStaff learningStaff = null;
            LearningStaffId learningStaffId = new LearningStaffId();
            learningStaffId.setLerId(ler);
            learningStaffId.setStaffId(staffMember);
            learningStaff = (LearningStaff) session.get(LearningStaff.class, learningStaffId);
            if (learningStaff != null) {
                result = false;
                tx.commit();
            } else {
//            LearningStaffCat cat;
//            LearningStaffCatId learningStaffCatId = new LearningStaffCatId();
//            learningStaffCatId.setLerId(currentLearningStaff.getId().getLerId());
//            learningStaffCatId.setStaffCatId(staffMember.getStaffCategory());
//
//            cat = (LearningStaffCat) session.get(LearningStaffCat.class, learningStaffCatId);
//            cat.setVacantSlots(cat.getVacantSlots() + 1);
//            session.update(cat);

                LearningStaffCat newCat;
                LearningStaffCatId newCatId = new LearningStaffCatId();
                newCatId.setLerId(ler);
                newCatId.setStaffCatId(staffMember.getStaffCategory());

                newCat = (LearningStaffCat) session.get(LearningStaffCat.class, newCatId);
                if (newCat == null) {
                    newCat = new LearningStaffCat();
                    newCat.setId(newCatId);
                    newCat.setNoOfMembers(distribution.getNoOfMembers());
                    newCat.setVacantSlots(distribution.getNoOfMembers() - 1);
                    LearningStaff lerStaff = new LearningStaff();
                    LearningStaffId id = new LearningStaffId();
                    id.setLerId(ler);
                    id.setStaffId(staffMember);
                    lerStaff.setId(id);
                    lerStaff.setApplyStatus("Pending");
                    LearningStaffCat cat;
                    LearningStaffCatId learningStaffCatId = new LearningStaffCatId();
                    learningStaffCatId.setLerId(currentLearningStaff.getId().getLerId());
                    learningStaffCatId.setStaffCatId(staffMember.getStaffCategory());

                    cat = (LearningStaffCat) session.get(LearningStaffCat.class, learningStaffCatId);
                    cat.setVacantSlots(cat.getVacantSlots() + 1);
                    session.update(cat);
                    session.delete(currentLearningStaff);
                    session.save(lerStaff);
                    session.save(newCat);
                    lerStaff.setPoints(getUserPoints(ler, staffMember));

                    tx.commit();
                    result = true;
                } else {
                    if (newCat.getVacantSlots() != 0) {
                        newCat.setVacantSlots(newCat.getVacantSlots() - 1);
                        LearningStaff lerStaff = new LearningStaff();
                        LearningStaffId id = new LearningStaffId();
                        id.setLerId(ler);
                        id.setStaffId(staffMember);
                        lerStaff.setId(id);
                        lerStaff.setApplyStatus("Pending");
                        lerStaff.setPoints(Short.valueOf("0"));
                        LearningStaffCat cat;
                        LearningStaffCatId learningStaffCatId = new LearningStaffCatId();
                        learningStaffCatId.setLerId(currentLearningStaff.getId().getLerId());
                        learningStaffCatId.setStaffCatId(staffMember.getStaffCategory());
                        cat = (LearningStaffCat) session.get(LearningStaffCat.class, learningStaffCatId);
                        cat.setVacantSlots(cat.getVacantSlots() + 1);
                        session.delete(currentLearningStaff);
                        session.saveOrUpdate(lerStaff);
                        session.update(cat);
                        session.update(newCat);
                        lerStaff.setPoints(getUserPoints(ler, staffMember));
                        tx.commit();
                        result = true;
                    } else {
                        tx.commit();
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }

        return result;
    }

    public boolean saveLearningToLearnStaff(StaffMember staffMember, LearningToLearn ler) {
        boolean result = false;


        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {

            LearningStaff lerStaff = null;

            LearningStaffId id = new LearningStaffId();
            id.setLerId(ler);
            id.setStaffId(staffMember);

            lerStaff = (LearningStaff) session.get(LearningStaff.class, id);
            if (lerStaff
                    == null) {
                lerStaff = new LearningStaff();
                lerStaff.setId(id);
                lerStaff.setApplyStatus("Pending");
                lerStaff.setPoints(getUserPoints(ler, staffMember));

                LearningStaffCat cat = null;
                LearningStaffCatId staffCatId = new LearningStaffCatId();
                staffCatId.setLerId(ler);
                staffCatId.setStaffCatId(staffMember.getStaffCategory());
                Query q = session.createQuery("from StaffDistribution sd join fetch sd.staffCategory sds "
                        + "where sds=:staffCategory and sd.scheduleType=:scheduleType");
                q.setEntity("staffCategory", staffMember.getStaffCategory());
                q.setString("scheduleType", "Learn");
                StaffDistribution distribution = (StaffDistribution) q.list().get(0);
                cat = (LearningStaffCat) session.get(LearningStaffCat.class, staffCatId);
                if (cat == null) {
                    cat = new LearningStaffCat();
                    cat.setId(staffCatId);
                    cat.setNoOfMembers(distribution.getNoOfMembers());
                    cat.setVacantSlots(distribution.getNoOfMembers() - 1);
                    session.save(lerStaff);
                    session.save(cat);
                    tx.commit();
                    result = true;
//                } else if (cat.getVacantSlots() < distribution.getNoOfMembers() && cat.getVacantSlots() != 0) {
                } else if (cat.getVacantSlots() != 0) {
                    session.save(lerStaff);
                    cat.setVacantSlots(cat.getVacantSlots() - 1);
                    session.update(cat);
                    tx.commit();
                    result = true;
                } else {
                    result = false;
                    tx.commit();
                }
            } else {
                result = false;
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return result;
    }

    public void deleteLearningStaff(LearningStaff currentLearningStaff, StaffMember staffMember) {
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {
            LearningStaffCatId learningStaffCatId = new LearningStaffCatId();
            learningStaffCatId.setLerId(currentLearningStaff.getId().getLerId());
            learningStaffCatId.setStaffCatId(staffMember.getStaffCategory());
            LearningStaffCat learningStaffCat = (LearningStaffCat) session.get(LearningStaffCat.class, learningStaffCatId);
            learningStaffCat.setVacantSlots(learningStaffCat.getVacantSlots()+1);
            session.update(learningStaffCat);
            session.delete(currentLearningStaff);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<LearningToLearnStaffDTO> getLearningStaff(StaffMember staffMember, Date filterDate) {
        List<LearningToLearnStaffDTO> dTOs = new ArrayList<LearningToLearnStaffDTO>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from LearningStaff ps "
                    + "left join fetch ps.id.lerId psp "
                    + "left join fetch ps.id.staffId pss "
                    + "left join fetch psp.department "
                    + "left join fetch psp.program "
                    + "left join fetch psp.session "
                    + "left join fetch psp.center "
                    + "where pss=:staffMember and psp.lerDate=:lerDate and (ps.applyStatus <> 'Approved' and ps.applyStatus <> 'Rejected')");
            q.setEntity("staffMember", staffMember);
            q.setDate("lerDate", filterDate);
            List<LearningStaff> list = q.list();
            LearningToLearnStaffDTO o;
            for (LearningStaff learningStaff : list) {
                o = new LearningToLearnStaffDTO();
                o.setLearningStaff(learningStaff);
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

    public List<LearningToLearnStaffDTO> getLearningStaffbyStaffId(StaffMember staffMember, String criteriaValue, String userCriteriaValue) {
        List<LearningToLearnStaffDTO> dTOs = new ArrayList<LearningToLearnStaffDTO>();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            Query q;
            StringBuilder sb = new StringBuilder();
            sb.append("from LearningStaff ps ");
            sb.append("left join fetch ps.id.lerId psp ");
            sb.append("left join fetch ps.id.staffId pss ");
            sb.append("left join fetch psp.department ");
            sb.append("left join fetch psp.program pspp ");
            sb.append("left join fetch psp.session psps ");
            sb.append("left join fetch psp.center pspc ");
            sb.append("where pss=:staffMember and (ps.applyStatus <> 'Approved' and ps.applyStatus <> 'Rejected') and ");
            List<LearningStaff> list = new ArrayList<LearningStaff>();
            if (criteriaValue.equals("Program")) {
                sb.append("pspp.programName like :program");
                q = session.createQuery(sb.toString());
                q.setString("program", "%" + userCriteriaValue + "%");
                q.setEntity("staffMember", staffMember);
                list = q.list();
            }
            if (criteriaValue.equals("Center")) {
                sb.append("pspc.centerName like :center");
                q = session.createQuery(sb.toString());
                q.setString("center", "%" + userCriteriaValue + "%");
                q.setEntity("staffMember", staffMember);
                list = q.list();
            }
            if (criteriaValue.equals("Session")) {
                sb.append("psps.sessionDesc like :session");

                q = session.createQuery(sb.toString());
                q.setString("session", "%" + userCriteriaValue + "%");
                q.setEntity("staffMember", staffMember);
                list = q.list();
            }

            LearningToLearnStaffDTO o;
            for (LearningStaff learningStaff : list) {
                o = new LearningToLearnStaffDTO();
                o.setLearningStaff(learningStaff);
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

    public boolean checkAvailability(StaffMember staffMember, LearningToLearn ler) {
        boolean available = false;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();
        try {
            LearningStaff lerStaff = null;

            LearningStaffId lerStaffId = new LearningStaffId();
            lerStaffId.setLerId(ler);
            lerStaffId.setStaffId(staffMember);

            lerStaff = (LearningStaff) session.get(LearningStaff.class, lerStaffId);
            if (lerStaff!=null) {
                return false;
            }
            
            StaffDistribution distribution;
            Query q = session.createQuery("from StaffDistribution sd join fetch sd.staffCategory sds "
                    + "where sds=:staffCategory and sd.scheduleType=:scheduleType");
            q.setEntity("staffCategory", staffMember.getStaffCategory());
            q.setString("scheduleType", "Learn");
            distribution = (StaffDistribution) q.list().get(0);
            LearningStaffCat cat;
            LearningStaffCatId id = new LearningStaffCatId();
            id.setLerId(ler);
            id.setStaffCatId(staffMember.getStaffCategory());

            cat = (LearningStaffCat) session.get(LearningStaffCat.class, id);
            if (cat
                    == null) {
                available = true;
            } else if (cat.getVacantSlots()
                    < distribution.getNoOfMembers() && cat.getVacantSlots() != 0) {
                available = true;
            } else {
                available = false;
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return available;
    }

    public int getUserAccumulatedPoints(StaffMember staffMember) {
        int accumulatedPoints = 0;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        Transaction tx = session.beginTransaction();

        try {
            Query q = session.createQuery("select sum(staff.points) "
                    + "from PreOrientationStaff staff "
                    + "left join fetch staff.id.staffId sis "
                    + "where sis=:staffMember");
            q.setEntity("staffMember", staffMember);
            List staffList = q.list();
            accumulatedPoints += staffList == null ? 0 : (staffList.get(0) == null ? 0 : (Long) staffList.get(0));
            q = session.createQuery("select sum(staff.points) "
                    + "from RegistationStaff staff "
                    + "where staff.id.staffId=:staffMember");
            q.setShort("staffMember", staffMember.getStaffId());
            List registrationList = q.list();
            accumulatedPoints += registrationList == null ? 0 : (registrationList.get(0) == null ? 0 : (Long) registrationList.get(0));
            q = session.createQuery("select sum(staff.points) "
                    + "from LearningStaff staff "
                    + "left join fetch staff.id.staffId sis "
                    + "where sis=:staffMember");
            q.setEntity("staffMember", staffMember);
            List learningStaff = q.list();
            accumulatedPoints += learningStaff == null ? 0 : (learningStaff.get(0) == null ? 0 : (Long) learningStaff.get(0));
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return accumulatedPoints;
    }
    public short getUserPoints(LearningToLearn lerSchedule, StaffMember member) {
        short X = 0;
        short Y = 0;
        short Z = 0;
        short points = 0;
        Workstation workstation = null;
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        //Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Holiday h "
                    + "where h.holiday=:holiday");
            q.setDate("holiday", lerSchedule.getLerDate());
            final List holidayList = q.list();
            Holiday holiday = holidayList == null ? null : (Holiday) holidayList.get(0);
            q = session.createQuery("from WorkstationPriority wp "
                    + "left join fetch wp.id.staffId wpis "
                    + "left join fetch wp.id.workstationId wpiw "
                    + "left join fetch wp.id.deptId wpid "
                    + "where wpis=:staffMember and wpid=:department");
            q.setEntity("staffMember", member);
            q.setEntity("department", lerSchedule.getDepartment());
            final List priorityList = q.list();
            WorkstationPriority priority = priorityList == null ? null : (WorkstationPriority) priorityList.get(0);
            if (priority != null) {
                workstation = priority.getId().getWorkstationId();
                if (holiday != null && workstation != null) {
                    DayType dayType = new DayType();
                    dayType.setDayTypeId(Short.valueOf("1"));
                    dayType.setDayTypeName("holiday");
                    DayTypeWeghtageId id = new DayTypeWeghtageId(lerSchedule.getCenter(), workstation, dayType);
                    DayTypeWeghtage dayTypeWeghtage = (DayTypeWeghtage) session.get(DayTypeWeghtage.class, id);
                    if (dayTypeWeghtage != null) {
                        X = dayTypeWeghtage.getPoints();
                    } else {
                    }
                } else {
                    Y = 1;
                }
                WorkstationCenterId workstationCenterId = new WorkstationCenterId(lerSchedule.getCenter(), workstation);
                WorkstationCenter center = (WorkstationCenter) session.get(WorkstationCenter.class, workstationCenterId);
                if (center != null) {
                    Z = center.getPoints();
                }
            }
            points = (short) (X + Y + Z);
           // tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //tx.rollback();
        }
        return points;
    }
}
