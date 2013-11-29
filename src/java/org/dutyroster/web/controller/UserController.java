
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dutyroster.hibernate.database.UserAccount;
import org.dutyroster.web.services.UserServices;
import org.dutyroster.web.util.Constants;

/**
 *
 * @author Gift
 */
@ManagedBean(name = "user")
@SessionScoped
public class UserController implements Serializable {

    private String userName;
    private String passWord;
    private UserServices userService = new UserServices();

    /** Creates a new instance of UserController */
    public UserController() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserServices getUserService() {
        return userService;
    }

    public void setUserService(UserServices userService) {
        this.userService = userService;
    }

    public String loginUser() throws Exception {
        UserAccount dto = new UserAccount();
        dto.setUserName(this.getUserName());
        dto.setPassword(this.getPassWord());
        //this.setUserName(null);
        this.setPassWord(null);

        UserAccount[] responseDto = userService.getLoginInfo();

        //Getting the user Session Information From the external context
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        for (int i = 0; i < responseDto.length; i++) {
            if (responseDto[i].getUserName().equals(dto.getUserName())) {
                if (responseDto[i].getPassword().equals(dto.getPassword())) {
                    //Setting the User Into the Session
                    request.getSession(true).setAttribute(Constants.SESSION_USER, responseDto[0].getUserName());

                    //Remove expired Session variable from context
                    if (request.getSession().getAttribute(Constants.EXPIRED_SESSION) != null) {
                        request.getSession().removeAttribute(Constants.EXPIRED_SESSION);
                    }

                    //this.setUserName(responseDto.getUserName());
                    //this.setUserDomain(responseDto.getDomainName());
                    return Constants.SUCCESS;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect User Name and/or Password.", "Login Failed. Please try again."));
                    return null;
                }
            } else if (i == responseDto.length - 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect User Name and/or Password.", "Login Failed. Please try again."));
                return null;
            }
        }
        return null;
    }

    public String resetUser() throws Exception {
        this.setUserName("");
        this.setPassWord("");
        return null;
    }

    public String logout() throws Exception {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //Removing the User from Session
        if (request.getSession().getAttribute(Constants.SESSION_USER) != null) {
            request.getSession().removeAttribute(Constants.SESSION_USER);
            request.getSession().invalidate();
        }

        this.setUserName(null);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/user/userLogin.jsf");
        return Constants.SUCCESS;
    }

    public String navigateToRegMain() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/registrationMain.xhtml");
        return null;
    }
}
