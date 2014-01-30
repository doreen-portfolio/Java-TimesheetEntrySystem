package com.corejsf;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 * A Bean that keeps track of the information of the logged in user.
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 *
 */
@Named("login")
@ApplicationScoped
public class LoginBean implements Serializable {
    /**
     * A UserBean object. This can get the username and password.
     */
    @Inject private UserBean logUser;

    /**
     * A table that stores all the users.
     */
    @Inject private UserListForm userListForm;
    
    /**
     * Stores all the user's timesheets.
     */
    @Inject private TimesheetListForm timesheetListForm;

    /**
     * The input username.
     */
    private String usernameInput;

    /**
     * The input password.
     */
    private String passwordInput;

    /**
     * A boolean to flag if the username and password combination is correct.
     */
    private boolean incorrect = false;

    /**
     * A flag to tell if the user is a superuser.
     */
    private boolean isSuper = false;
    
    /**
     * A flag to tell if the user is logged in.
     */
    private boolean loggedIn;

    /**
     * A getter for the login user.
     * @return LoginBean logUser
     */
    public UserBean getLogUser() {
        return logUser;
    }

    /**
     * @return the userListForm
     */
    public UserListForm getUserListForm() {
        return userListForm;
    }

    /**
     * @param userListForm the userListForm to set
     */
    public void setUserListForm(UserListForm userListForm) {
        this.userListForm = userListForm;
    }
    
    /**
     * @return the timesheetListForm
     */
    public TimesheetListForm getTimesheetListForm() {
        return timesheetListForm;
    }

    /**
     * @param timesheetListForm the timesheetListForm to set
     */
    public void setTimesheetListForm(TimesheetListForm timesheetListForm) {
        this.timesheetListForm = timesheetListForm;
    }

    /**
     * A getter for the input password.
     * @return String passwordInput - the input password
     */
    public String getPasswordInput() {
        return passwordInput;
    }
    
    /**
     * A getter for the input username.
     * @return String usernameInput - the inpu username
     */
    public String getUsernameInput() {
        return usernameInput;
    }

    /**
     * A setter for the input username.
     * @param newValue - the input username
     */
    public void setUsernameInput(final String newValue) {
        usernameInput = newValue;
    }

    /**
     * A setter for the input password.
     * @param newValue - the input password
     */
    public void setPasswordInput(final String newValue) {
        passwordInput = newValue;
    }

    /**
     * A boolean that will be set to indicate a bad
     * username and password combination.
     * @return boolean incorrect - a flag to indicate a bad
     * username password combination
     */
    public boolean isIncorrect() {
        return incorrect;
    }

    /**
     * A setter for the incorrect boolean.
     * @param correct - true or false for if the password and
     * username combination is incorrect
     */
    public void setIncorrect(final boolean correct) {
        this.incorrect = correct;
    }

    /**
     * Returns if the user is a super user.
     * @return boolean isSuper - a flag to indicated if a user is a super user
     */
    public boolean isSuper() {
        return isSuper;
    }

    /**
     * Sets the isSuper boolean.
     * @param is - a flag to indicated if a user is a super user
     */
    public void setSuper(final boolean is) {
        isSuper = is;
    }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Checks if username and password combination is valid.
     * Redirects the user to the correct Main Menu page.
     * @return String - directs to either Admin or User Main Menu
     */
    public String login() {
        userListForm.refreshList();
        if (userListForm.hasUsername(usernameInput)
                && userListForm.getUserByUsername(usernameInput).getPassword()
                        .equals(passwordInput)) {
            logUser = userListForm.getUserByUsername(usernameInput);
            timesheetListForm.refreshList();
            setLoggedIn(true);
            setIncorrect(false);
            if(logUser.isSuperuserStatus() == true) {
                clearInput();
                setSuper(true);
                return "adminMenu";
            } else {
                clearInput();
                setSuper(false);
                return "userMenu";
            }
        }
        setIncorrect(true);
        return "login";
    }

    /**
     * Directs the user to the login page after they log out.
     * @return String - directs to the login page
     */
    public String logout() {
        userListForm.cleanupErrors();
        logUser = null;
        timesheetListForm.setTimesheetList(null);
        userListForm.setUserlist(null);
        setLoggedIn(false);
        return "login";
    }
    
    /**
     * Clears the input text boxes for username and password.
     */
    public void clearInput() {
        usernameInput = null;
        passwordInput = null;
    }

}