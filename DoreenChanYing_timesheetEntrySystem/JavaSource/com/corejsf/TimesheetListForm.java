package com.corejsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Helper class for displaying the current 
 * user's timesheets.
 * 
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 */ 
@Named("timesheetListForm")
@ApplicationScoped
public class TimesheetListForm implements Serializable {

    /** Manager for Timesheet objects. */
    @Inject private TimesheetManager timesheetManager;
    
    /** Manager for TimesheetEntryListForm objects. */
    @Inject private TimesheetEntryListForm timesheetEntryListForm;
    
    /** Manager for TimesheetEntry objects. */
    @Inject private TimesheetEntryManager timesheetEntryManager;
    
    /** The logged in user. */
    @Inject private LoginBean logUser;
    
    /**
     * The number of working days in a week.
     */
    private final int workingDaysInAWeek = 5;
    
    /**
     * A timesheet object for creating, viewing or editing.
     */
    private Timesheet timesheet = new Timesheet();
    
    /**
     * List of timesheets.
     */
    private List<Timesheet> timesheetList;
    
    /**
     * Flag for out of range Saturday hours.
     */
    private boolean satOutofRange = false;
    
    /**
     * Flag for out of range Sunday hours.
     */
    private boolean sunOutofRange = false;
    
    /**
     * Flag for out of range Monday hours.
     */
    private boolean monOutofRange = false;
    
    /**
     * Flag for out of range Tuesday hours.
     */
    private boolean tueOutofRange = false;
    
    /**
     * Flag for out of range Wednesday hours.
     */
    private boolean wedOutofRange = false;
    
    /**
     * Flag for out of range Thursday hours.
     */
    private boolean thuOutofRange = false;
    
    /**
     * Flag for out of range Friday hours.
     */
    private boolean friOutofRange = false;
    
    /**
     * Flag for Project number and WP combination.
     */
    private boolean invalidWPProject = false;
    
    /**
     * The getter for timesheet entry list form.
     * @return the timesheet entry list form
     */
    public TimesheetEntryListForm getTimesheetEntryListForm() {
        return timesheetEntryListForm;
    }
    
    /**
     * The setter for timesheet entry list form.
     * @param timesheetEntryListForm the timesheetEntryListForm to set
     */
    public void setTimesheetEntryListForm(TimesheetEntryListForm timesheetEntryListForm) {
        this.timesheetEntryListForm = timesheetEntryListForm;
    }
    
    /**
     * Getter for timesheetList
     * @return timesheetList
     */
    public List<Timesheet> getTimesheetList(){
        if(timesheetList == null){
            refreshList();
        }
        return timesheetList;
    }
    
    /**
     * Setter for timesheetList.
     * @return timesheetList - The new timesheetList.
     */
    public void setTimesheetList(List<Timesheet> timesheetList) {
        this.timesheetList = timesheetList;
    }
    
    /**
     * Refreshes the timesheetList by adding all timesheets 
     * that belong to the current user from the database.
     */
    public void refreshList() {
        Calendar cal = Calendar.getInstance();
        Timesheet[] timesheets = timesheetManager.findTimesheets(logUser.getLogUser().getUserid());
        timesheetList = new ArrayList<Timesheet>();
        for (int i = 0; i < timesheets.length; i++) {
            timesheetList.add(timesheets[i]);
            if(timesheets[i].getWeekNumber() == cal.get(Calendar.WEEK_OF_YEAR)){
                logUser.getLogUser().setHasCurrentWeek(true);
            }
        }
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
     * @param logUser the logUser to set
     */
    public void setLogUser(LoginBean logUser) {
        this.logUser = logUser;
    }
    
    /**
     * Redirects to Main Menu and refreshes the Timesheets to display.
     * @return String that returns the appropriate main menu
     */
    public String goToMainMenu() {
        refreshList(); //refreshes list then resets all flags
        setFriOutofRange(false);
        setThuOutofRange(false);
        setWedOutofRange(false);
        setTueOutofRange(false);
        setMonOutofRange(false);
        setSunOutofRange(false);
        setSatOutofRange(false);
        setInvalidWPProject(false);
        if(logUser.getLogUser().isSuperuserStatus()) { //checks if the user is a superuser
            return "adminMenu";
        }
        return "userMenu";
    }
    
    /**
     * Getter for the timesheet
     */
    public Timesheet getTimesheet() {
        return timesheet;
    }
    
    /**
     * Setter for the timesheet
     * @param timesheet the timesheet to set
     */
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
    
    /**
     * Creates a timesheet and adds the dates and week.
     * This timesheet will be able to add entry information.
     * @return String - redirects to Create Timesheet page
     */
    public String createNewTimesheet() {
        Calendar cal = Calendar.getInstance();
        timesheet = new Timesheet();
        timesheet.setWeekNumber(cal.get(Calendar.WEEK_OF_YEAR));
        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK,
                  first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));
        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, workingDaysInAWeek);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        timesheet.setWeekEnding(df.format(last.getTime()));
        timesheet.setId(logUser.getLogUser().getUserid());
        return "createTimesheet";
    }
    
    /**
     * Adds the input info into a new timesheet
     * that gets added to the database.
     * @return String directs to appropriate main menu
     */
    public String addTimesheet() {
        double tSat = 0.0;
        double tSun = 0.0;
        double tMon = 0.0;
        double tTue = 0.0;
        double tWed = 0.0;
        double tThu = 0.0;
        double tFri = 0.0;
        TimesheetEntry te[] = timesheet.getTimesheetEntries().toArray(
                new TimesheetEntry[timesheet.getTimesheetEntries().size()]);
        for(int i = 0; i < te.length; i++){ //checks to see if any of the hours are out of bounds
            if(te[i].getSat() > 24 || te[i].getSat() < 0) {
                setSatOutofRange(true);
                return null;
            } else {
                setSatOutofRange(false);
            }
            
            if(te[i].getSun() > 24 || te[i].getSun() < 0) {
                setSunOutofRange(true);
                return null;
            } else {
                setSunOutofRange(false);
            }
            
            if(te[i].getMon() > 24 || te[i].getMon() < 0) {
                setMonOutofRange(true);
                return null;
            } else {
                setMonOutofRange(false);
            }
            
            if(te[i].getTue() > 24 || te[i].getTue() < 0) {
                setTueOutofRange(true);
                return null;
            } else {
                setTueOutofRange(false);
            }
            
            if(te[i].getWed() > 24 || te[i].getWed() < 0) {
                setWedOutofRange(true);
                return null;
            } else {
                setWedOutofRange(false);
            }
            
            if(te[i].getThu() > 24 || te[i].getThu() < 0)
            {
                setThuOutofRange(true);
                return null;
            } else {
                setThuOutofRange(false);
            }
            
            if(te[i].getFri() > 24 || te[i].getFri() < 0)
            {
                setFriOutofRange(true);
                return null;
            } else {
                setFriOutofRange(false);
            }
            
            for(int j = i + 1; j < te.length; j++) { //checks the Project number WP combination
                if(te[i].getWp().compareTo(te[j].getWp()) == 0) {
                    if(te[i].getProject() == te[j].getProject()) {
                        setInvalidWPProject(true);
                        return null;
                    }
                }
            }
            
        }
        
        for(int i = 0; i < te.length; i++) { //adds all entries to the database
            te[i].setTotal(te[i].getSat() + te[i].getSun() + te[i].getMon() + te[i].getTue() 
                    + te[i].getWed() + te[i].getThu() + te[i].getFri());
            tSat += te[i].getSat();
            tSun += te[i].getSun();
            tMon += te[i].getMon();
            tTue += te[i].getTue();
            tWed += te[i].getWed();
            tThu += te[i].getThu();
            tFri += te[i].getFri();
            timesheetEntryListForm.addEntry(te[i], timesheet.getWeekNumber(), i+1);
        }
        //calculates the total hours for each day
        timesheet.setTotalSat(tSat);
        timesheet.setTotalSun(tSun);
        timesheet.setTotalMon(tMon);
        timesheet.setTotalTue(tTue);
        timesheet.setTotalWed(tWed);
        timesheet.setTotalThu(tThu);
        timesheet.setTotalFri(tFri);
        //calculates the overall total hours for the timesheet
        double tHours = tSat + tSun + tMon + tTue + tWed + tThu + tFri;
        timesheet.setTotalHours(tHours);
        timesheetManager.add(timesheet);
        logUser.getLogUser().getUserTimesheets().add(timesheet);
        return goToMainMenu(); //returns to appropriate main menu
    }
    
    /**
     * Saves the timesheet entries and returns to the main menu.
     * 
     * @return Navigation string
     */
    public String saveEntries() { 
		double tSat = 0.0;
        double tSun = 0.0;
        double tMon = 0.0;
        double tTue = 0.0;
        double tWed = 0.0;
        double tThu = 0.0;
        double tFri = 0.0;
        TimesheetEntry te[] = timesheet.getTimesheetEntries().toArray(
                new TimesheetEntry[timesheet.getTimesheetEntries().size()]);
        for(int i = 0; i < te.length; i++) { //sets flags for out of bounds hours and other flags
            if(te[i].getSat() > 24 || te[i].getSat() < 0) {
                setSatOutofRange(true);
                return null;
            } else {
                setSatOutofRange(false);
            }
            
            if(te[i].getSun() > 24 || te[i].getSun() < 0) {
                setSunOutofRange(true);
                return null;
            } else {
                setSunOutofRange(false);
            }
            
            if(te[i].getMon() > 24 || te[i].getMon() < 0) {
                setMonOutofRange(true);
                return null;
            } else {
                setMonOutofRange(false);
            }
            
            if(te[i].getTue() > 24 || te[i].getTue() < 0) {
                setTueOutofRange(true);
                return null;
            } else {
                setTueOutofRange(false);
            }
            
            if(te[i].getWed() > 24 || te[i].getWed() < 0) {
                setWedOutofRange(true);
                return null;
            } else {
                setWedOutofRange(false);
            }
            
            if(te[i].getThu() > 24 || te[i].getThu() < 0)
            {
                setThuOutofRange(true);
                return null;
            } else {
                setThuOutofRange(false);
            }
            
            if(te[i].getFri() > 24 || te[i].getFri() < 0)
            {
                setFriOutofRange(true);
                return null;
            } else {
                setFriOutofRange(false);
            }
            
            for(int j = i + 1; j < te.length; j++) { //sets the flag for invalid Project number and WP combination
                if(te[i].getWp().compareTo(te[j].getWp()) == 0) {
                    if(te[i].getProject() == te[j].getProject()) {
                        setInvalidWPProject(true);
                        return null;
                    }
                }
            }
        }
        for(int i = 0; i < te.length; i++){ //adds all entries to the database
            te[i].setTotal(te[i].getSat() + te[i].getSun() + te[i].getMon() 
                    + te[i].getTue() + te[i].getWed() + te[i].getThu() + te[i].getFri());
			tSat += te[i].getSat();
            tSun += te[i].getSun();
            tMon += te[i].getMon();
            tTue += te[i].getTue();
            tWed += te[i].getWed();
            tThu += te[i].getThu();
            tFri += te[i].getFri();
            timesheetEntryManager.merge(te[i], logUser.getLogUser().getUserid(), i+1);
        }
        timesheet.setTotalSat(tSat);
        timesheet.setTotalSun(tSun);
        timesheet.setTotalMon(tMon);
        timesheet.setTotalTue(tTue);
        timesheet.setTotalWed(tWed);
        timesheet.setTotalThu(tThu);
        timesheet.setTotalFri(tFri);
        //calculates the overall total hours for the timesheet
        double tHours = tSat + tSun + tMon + tTue + tWed + tThu + tFri;
        timesheet.setTotalHours(tHours);
        timesheetManager.merge(timesheet, logUser.getLogUser().getUserid(), timesheet.getWeekNumber());
        return goToMainMenu();
    }
    
    /**
     * Adds the additional entries to the timesheet.
     * @param rowid the id of the timesheet entry
     */
    public String createAnotherEntry(int rowid) {
        TimesheetEntry te = new TimesheetEntry(timesheet.getId(), timesheet.getWeekNumber(), 
                rowid, 0, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null);
        timesheet.getTimesheetEntries().add(te);
        timesheetEntryListForm.addEntry(te, te.getWeek(), rowid);
        timesheet.setNumRows(timesheet.getNumRows()+1);
        return null;
    }
    
    /**
     * Adds the additional entries to the timesheet.
     * @param rowid the id of the timesheet entry
     */
    public void  createAnotherEntryInEdit(int rowid) {
        TimesheetEntry te = new TimesheetEntry(timesheet.getId(), timesheet.getWeekNumber(), 
                rowid, 0, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null);
        timesheet.getTimesheetEntries().add(te);
        timesheetEntryListForm.addEntry(te, te.getWeek(), rowid);

    }
    
    /**
     * Navigates to the View Timesheet page for editing.
     * 
     * @return Navigation string
     */
    public String editTimesheet() {
        Calendar cal = Calendar.getInstance();
        timesheet = timesheetManager.findTimesheet(logUser.getLogUser().getUserid(), cal.get(Calendar.WEEK_OF_YEAR)); //get current week
        timesheet.setEditable(true);
        TimesheetEntry[] te = timesheetEntryManager.findEntries(logUser.getLogUser().getUserid(), cal.get(Calendar.WEEK_OF_YEAR));
        ArrayList<TimesheetEntry> teArray = new ArrayList<TimesheetEntry>();  
        for(int i = 0; i < te.length; i++) {
            teArray.add(te[i]);
        }
        
        timesheet.setTimesheetEntries(teArray);
        return "edit";
    }
    
    /**
     * Navigates to the View Timesheet page for viewing.
     * 
     * @param sheet - the timesheet to view.
     * @return Navigation string
     */
    public String viewTimesheet(Timesheet sheet) { 
        timesheet = timesheetManager.findTimesheet(logUser.getLogUser().getUserid(), sheet.getWeekNumber());
        sheet.setEditable(false);
        TimesheetEntry[] te = timesheetEntryManager.findEntries(logUser.getLogUser().getUserid(), 
                sheet.getWeekNumber());
        ArrayList<TimesheetEntry> teArray = new ArrayList<TimesheetEntry>(); 
        for(int i = 0; i < te.length; i++){
            teArray.add(te[i]);
        }
        timesheet.setTimesheetEntries(teArray);
        return "viewTimesheet";
    }

    /**
     * Getter for the Saturday
     * out of range flag.
     * @return satOutofRange - the flag
     *  for Saturday out of bounds
     */
    public boolean isSatOutofRange() {
        return satOutofRange;
    }

    /**
     * Setter for the Saturday
     * out of range flag.
     * @param satOutofRange - value to set the flag
     */
    public void setSatOutofRange(boolean satOutofRange) {
        this.satOutofRange = satOutofRange;
    }

    /**
     * Getter for the Sunday
     * out of range flag.
     * @return sunOutofRange - the flag
     *  for Sunday out of bounds
     */
    public boolean isSunOutofRange() {
        return sunOutofRange;
    }

    /**
     * Setter for the Sunday
     * out of range flag.
     * @param sunOutofRange - value to set the flag
     */
    public void setSunOutofRange(boolean sunOutofRange) {
        this.sunOutofRange = sunOutofRange;
    }

    /**
     * Getter for the Monday
     * out of range flag.
     * @return monOutofRange - the flag
     *  for Monday out of bounds
     */
    public boolean isMonOutofRange() {
        return monOutofRange;
    }

    /**
     * Setter for the Monday
     * out of range flag.
     * @param monOutofRange - value to set the flag
     */
    public void setMonOutofRange(boolean monOutofRange) {
        this.monOutofRange = monOutofRange;
    }

    /**
     * Getter for the Tuesday
     * out of range flag.
     * @return tueOutofRange - the flag
     *  for Tuesday out of bounds
     */
    public boolean isTueOutofRange() {
        return tueOutofRange;
    }

    /**
     * Setter for the Tuesday
     * out of range flag.
     * @param tueOutofRange - value to set the flag
     */
    public void setTueOutofRange(boolean tueOutofRange) {
        this.tueOutofRange = tueOutofRange;
    }

    /**
     * Getter for the Wednesday
     * out of range flag.
     * @return wedOutofRange - the flag
     *  for Wednesday out of bounds
     */
    public boolean isWedOutofRange() {
        return wedOutofRange;
    }

    /**
     * Setter for the Wednesday
     * out of range flag.
     * @param wedOutofRange - value to set the flag
     */
    public void setWedOutofRange(boolean wedOutofRange) {
        this.wedOutofRange = wedOutofRange;
    }

    /**
     * Getter for the Thursday
     * out of range flag.
     * @return thuOutofRange - the flag
     *  for Thursday out of bounds
     */
    public boolean isThuOutofRange() {
        return thuOutofRange;
    }

    /**
     * Setter for the Thursday
     * out of range flag.
     * @param thuOutofRange - value to set the flag
     */
    public void setThuOutofRange(boolean thuOutofRange) {
        this.thuOutofRange = thuOutofRange;
    }

    /**
     * Getter for the Friday
     * out of range flag.
     * @return friOutofRange - the flag
     *  for Friday out of bounds
     */
    public boolean isFriOutofRange() {
        return friOutofRange;
    }

    /**
     * Setter for the Friday
     * out of range flag.
     * @param friOutofRange - value to set the flag
     */
    public void setFriOutofRange(boolean friOutofRange) {
        this.friOutofRange = friOutofRange;
    }

    /**
     * Getter for the invalid Project number
     * WP combination flag.
     * @return invalidWPProject - the flag for the
     *  Project number WP combination
     */
    public boolean isInvalidWPProject() {
        return invalidWPProject;
    }

    /**
     * Sets the invalid Project number
     * WP combination flag.
     * @param invalidWPProject - value to set
     *  the flag
     */
    public void setInvalidWPProject(boolean invalidWPProject) {
        this.invalidWPProject = invalidWPProject;
    }
}
