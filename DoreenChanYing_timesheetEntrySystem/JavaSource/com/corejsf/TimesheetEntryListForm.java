package com.corejsf;

import java.util.ArrayList;
import java.util.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Helper class for displaying the current 
 * user's selected timesheet's entries.
 * @author Aleeza Arcangel, Doreen Chan-Ying
 *
 */
@Named("timesheetEntryListForm")
@ApplicationScoped
public class TimesheetEntryListForm {

    /**  
     * Manager for TimesheetEntry objects. 
     */
    @Inject
    private TimesheetEntryManager timesheetEntryManager;

    /** 
     * Manager for Timesheetobjects. 
     */
    @Inject
    private TimesheetManager timesheetManager;

    /** 
     * The logged in user. 
     */
    @Inject
    private LoginBean logUser;

    /**
     * Refreshes the entryList by adding all timesheet entries that belong to
     * the current user from the database.
     */
    public void refreshList() {
        Calendar cal = Calendar.getInstance();
        Timesheet t = timesheetManager.findTimesheet(logUser.getLogUser().getUserid(), cal.get(Calendar.WEEK_OF_YEAR));
        TimesheetEntry[] entries = timesheetEntryManager.findEntries(logUser
                .getLogUser().getUserid(), cal.get(Calendar.WEEK_OF_YEAR));
        t.setTimesheetEntries(null);
        for (int i = 0; i < entries.length; i++) {
            t.getTimesheetEntries().add(entries[i]);
        }
    }

    /**
     * Adds an entry to the database.
     * @param te - the entry
     * @param week - the week id
     * @param rowid - the row id
     */
    public void addEntry(TimesheetEntry te, int week, int rowid) {
       
        timesheetEntryManager.addEntry(te, logUser.getLogUser().getUserid(), week, rowid);
    }

    /**
     * Getter for the logged in user.
     * @return the logUser
     */
    public LoginBean getLogUser() {
        return logUser;
    }

    /**
     * Setter for the logged in user.
     * @param logUser - the logUser to set
     */
    public void setLogUser(LoginBean logUser) {
        this.logUser = logUser;
    }

    /**
     * Getter for the timesheer manager.
     * @return timesheetManager - the timesheet manager
     */
    public TimesheetManager getTimesheetManager() {
        return timesheetManager;
    }

    /**
     * Setter for the timesheet manager.
     * @param timesheetManager - the timesheet manager
     */
    public void setTimesheetManager(TimesheetManager timesheetManager) {
        this.timesheetManager = timesheetManager;
    }

    /**
     * Redirects to Main Menu and refreshes the TimesheetEntries to display.
     * 
     * @return String that returns the appropriate main menu
     */
    public String goToMainMenu() {
        if (logUser.getLogUser().isSuperuserStatus()) { // checks if the user is
                                                        // a superuser
            return "adminMenu";
        }
        return "userMenu";
    }
    
    /**
     * Saves the timesheet entries and returns to the main menu.
     * 
     * @return Navigation string
     */
    public String save() {
        Calendar cal = Calendar.getInstance(); //gets current calendar information
        Timesheet t = timesheetManager.findTimesheet(logUser.getLogUser()
                .getUserid(), cal.get(Calendar.WEEK_OF_YEAR)); //finds a timesheet specified by the week number and user id
        ArrayList<TimesheetEntry> te = t.getTimesheetEntries();
        for (int i = 1; i <= te.size(); i++) { //adds entries
            timesheetEntryManager.merge(te.get(i), logUser.getLogUser()
                    .getUserid(), i);
        }
        return goToMainMenu();
    }
}
