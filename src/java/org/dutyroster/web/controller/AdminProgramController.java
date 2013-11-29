/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.dutyroster.hibernate.database.Center;

import org.dutyroster.hibernate.database.Program;
import org.dutyroster.web.services.AdminProgramServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "program")
//@SessionScoped
@RequestScoped
public class AdminProgramController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Short programId;
    private String programName;
    private String dayTypeName;
    public AdminProgramServices adminProgramService = new AdminProgramServices();
    private Program[] programs = null;
    private Program selected;
    private Short pIDSelected;
    private String filterText;

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public Short getpIDSelected() {
        return pIDSelected;
    }

    public void setpIDSelected(Short pIDSelected) {
        this.pIDSelected = pIDSelected;
    }

    public Program[] getPrograms() {
        return programs;
    }

    public void setPrograms(Program[] programs) {
        this.programs = programs;
    }

    public Program getSelected() {
        return selected;
    }

    public void setSelected(Program selected) {
        this.selected = selected;
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

    public String getDayTypeName() {
        return dayTypeName;
    }

    public void setDayTypeName(String dayTypeName) {
        this.dayTypeName = dayTypeName;
    }

    public AdminProgramServices getAdminProgramService() {
        return adminProgramService;
    }

    public void setAdminProgramService(AdminProgramServices adminProgramService) {
        this.adminProgramService = adminProgramService;
    }

    public AdminProgramController() {
        try {
            programs = adminProgramService.searchAllPrograms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProgramInfo() {
        String response = null;
        Program dtoProgram = new Program();
        dtoProgram.setProgramId(this.getProgramId());
        dtoProgram.setProgramName(this.getProgramName());
        this.setProgramId(null);
        this.setProgramName(null);

        if (dtoProgram.getProgramId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Program ID left blank. Please select a Program ID to update."));
        } else if (dtoProgram.getProgramName() == null || dtoProgram.getProgramName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Program Name left blank. Please Enter Program Name to update."));
        } else {
            response = adminProgramService.addProgram(dtoProgram);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "New Record added to the Database."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));

            }
        }
    }

    public void editProgramInfo() {
        String response = null;
        Program dtoProgram = new Program();
        dtoProgram.setProgramId(this.getProgramId());
        dtoProgram.setProgramName(this.getProgramName());
        this.setProgramId(null);
        this.setProgramName(null);

        if (dtoProgram.getProgramId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Program ID left blank. Please select a Program ID to update."));
        } else if (dtoProgram.getProgramName() == null || dtoProgram.getProgramName().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Save.", "Program Name left blank. Please Enter Program Name to update."));
        } else {
            response = adminProgramService.editProgram(dtoProgram);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Saved", "Record sucesfully updated. "));
                programs = adminProgramService.searchAllPrograms();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Data not Saved. Please check your inputs."));
            }
        }
    }

    public void deleteProgramInfo() {
        String response = null;
        Program dtoProgram = new Program();
        dtoProgram.setProgramId(this.getProgramId());
        dtoProgram.setProgramName(this.getProgramName());
        this.setProgramId(null);
        this.setProgramName(null);

        if (dtoProgram.getProgramId() == (short) 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Data to Detele.", "Program ID left blank. Please select a program ID to delete."));
        } else {
            response = adminProgramService.deleteProgram(dtoProgram);
            if (response.equalsIgnoreCase(Constants.SUCCESS)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succefully Deleted.", "Record was sucessfully deleted from the database."));
                programs = adminProgramService.searchAllPrograms();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sever Error Occured.", "Integrity Constraint Violated. References Found in other tables."));
            }
        }
    }

    public void newProgramInfo() {
        short lastProgramId = 0;
        int newProgramId = 0;
        lastProgramId = adminProgramService.getLastProgramID();
        newProgramId = lastProgramId + 1;
        this.setProgramId((short) newProgramId);
        this.setProgramName(null);
    }

    public String logout() throws Exception {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //Removing the User from Session
        if (request.getSession().getAttribute(Constants.SESSION_USER) != null) {
            request.getSession().removeAttribute(Constants.SESSION_USER);
            request.getSession().invalidate();
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("../user/userLogin.jsf");
        return Constants.SUCCESS;
    }

    public void resetProgramInfo() {
        selected = null;
        this.setProgramId(null);
        this.setProgramName(null);
    }

    public void selectedProgram() {
        if (selected != null) {
            this.setProgramId(selected.getProgramId());
            this.setProgramName(selected.getProgramName());
            selected = null;
        }
    }

    public void searchProgramInfo() {
        if (filterText == null && filterText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Criteria to Search.", "No Search Criteria Supplied. Please Enter some value to Search."));
        } else {
            programs = adminProgramService.searchProgramsByFilter(filterText);
        }
    }
}
