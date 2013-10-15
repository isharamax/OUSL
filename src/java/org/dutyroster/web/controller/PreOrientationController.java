/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "PreOrientation")
@SessionScoped
public class PreOrientationController implements Serializable {

    private String staffNumber;
    private String name;
    private String department;
    private String attendedProgram;
    private String attendedCenter;
    private Date attendedDate;
    private String attendedSession;

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAttendedProgram() {
        return attendedProgram;
    }

    public void setAttendedProgram(String attendedProgram) {
        this.attendedProgram = attendedProgram;
    }

    public String getAttendedCenter() {
        return attendedCenter;
    }

    public void setAttendedCenter(String attendedCenter) {
        this.attendedCenter = attendedCenter;
    }

    public Date getAttendedDate() {
        return attendedDate;
    }

    public void setAttendedDate(Date attendedDate) {
        this.attendedDate = attendedDate;
    }

    public String getAttendedSession() {
        return attendedSession;
    }

    public void setAttendedSession(String attendedSession) {
        this.attendedSession = attendedSession;
    }
}
