package com.corejsf;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * User information. This includes The employee ID, username, password,
 * first name, last name, and the timesheets attached to the user.
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 *
 */
public class UserBean implements Serializable {
    
    /**
     * A table that stores all the users.
     */
    @Inject private UserListForm userlistform;
    
    /**
     * The employee ID.
     */
    private int userid;

    /**
     * The user's username.
     */
    private String username;

    /**
     * The user's first name.
     */
    private String fname;

    /**
     * The user's last name.
     */
    private String lname;

    /**
     * The user's password.
     */
    private String password;
    
    /**
     * The boolean that tells if the user is a superuser.
     */
    private boolean superuserStatus;
    
    /**
     * The timesheets linked to the user.
     */
    private ArrayList<Timesheet> userTimesheets;

    /**
     * The boolean that tells if the user is editable.
     */
    private boolean editable;
    
    /**
     * The boolean that tells if the userid is invalid.
     */
    private boolean invalidID = false;
    
    /**
     * The boolean that tells if the username is taken.
     */
    private boolean usernameTaken = false;
    
    /**
     * The boolean that tells if user has a timesheet 
     * for the current week.
     */
    private boolean hasCurrentWeek = false;
    
    /**
     * Constructor for a UserBean.
     * @param userid
     * @param username
     * @param fname
     * @param lname
     * @param password
     * @param superuserStatus
     */
    public UserBean(int userid, String username, String fname,
            String lname, String password, boolean superuserStatus) {
        this.userid = userid;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.setSuperuserStatus(superuserStatus);
        setHasCurrentWeek(false);
        userTimesheets = new ArrayList<Timesheet>();
    }

    /**
     * Constructor for UserBean that doesn't take arguments.
     */
    public UserBean() {
        this.userid = 0;
        this.username = null;
        this.fname = null;
        this.lname = null;
        this.password = null;
        this.setSuperuserStatus(superuserStatus);
        setHasCurrentWeek(false);
        userTimesheets = new ArrayList<Timesheet>();
    }
    
    /**
     * Getter for userlistform.
     * @return the userlistform
     */
    public UserListForm getUserlistform() {
        return userlistform;
    }

    /**
     * Setter for userlistform.
     * @param userlistform the userlistform to set
     */
    public void setUserlistform(UserListForm userlistform) {
        this.userlistform = userlistform;
    }
    
    /**
     * Getter for userid.
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * Setter for userid.
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        if(userid < 1){
            setInvalidID(true);
        } else {
            setInvalidID(false);
        }
        this.userid = userid;
    }

    /**
     * Getter for username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for fname.
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Setter for fname.
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Getter for lname.
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * Setter for  lname.
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Getter for password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the superuserStatus
     */
    public boolean isSuperuserStatus() {
        return superuserStatus;
    }

    /**
     * @param superuserStatus the superuserStatus to set
     */
    public void setSuperuserStatus(boolean superuserStatus) {
        this.superuserStatus = superuserStatus;
    }

    /**
     * Getter for userTimesheets.
     * @return
     */
    public ArrayList<Timesheet> getUserTimesheets() {
        return userTimesheets;
    }

    /**
     * Setter for userTimesheets.
     * @param userTimesheets
     */
    public void setUserTimesheets(ArrayList<Timesheet> userTimesheets) {
        this.userTimesheets = userTimesheets;
    }

    /**
     * Adds a timesheet to the user's userTimesheet ArrayList.
     * @param timesheet
     */
    public void addTimesheet(Timesheet timesheet) {
        userTimesheets.add(timesheet);
    }

    /**
     * Getter for editable boolean.
     * @return editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Setter for editable boolean.
     * @param newValue
     */
    public void setEditable(boolean newValue) {
        editable = newValue;
    }

    /**
     * Clears the UserBean fields.
     */
    public void clear(){
        userid = 0;
        username = null;
        fname = null;
        lname = null;
        password = null;
    }

    /**
     * Boolean to indicate if the userid is invalid.
     * @return the invalidID
     */
    public boolean isInvalidID() {
        return invalidID;
    }

    /**
     * Setter to indicate if the userid is invalid.
     * @param invalidID the invalidID to set
     */
    public void setInvalidID(boolean invalidID) {
        this.invalidID = invalidID;
    }

    /**
     * Boolean to indicate if the username is taken.
     * @return the usernameTaken
     */
    public boolean isUsernameTaken() {
        return usernameTaken;
    }

    /**
     * Setter to indicate if the username is taken.
     * @param usernameTaken the usernameTaken to set
     */
    public void setUsernameTaken(boolean usernameTaken) {
        this.usernameTaken = usernameTaken;
    }

    /**
     * Boolean to indicate if the user has a timesheet 
     * for the current week.
     * @return the hasCurrentWeek
     */
    public boolean isHasCurrentWeek() {
        return hasCurrentWeek;
    }

    /**
     * Setter to indicate if the user has a timesheet 
     * for the current week.
     * @param hasCurrentWeek the hasCurrentWeek to set
     */
    public void setHasCurrentWeek(boolean hasCurrentWeek) {
        this.hasCurrentWeek = hasCurrentWeek;
    }
}
