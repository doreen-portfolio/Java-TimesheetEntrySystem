package com.corejsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

/**
 * A timesheet object that stores information about the user's week. Timesheets
 * have timesheet entries that are stored within them that contain even more
 * information.
 * 
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 * 
 */
public class Timesheet implements Serializable {

    /**
     * The logged in user.
     */
    @Inject
    private LoginBean user;

    /**
     * The user's ID.
     */
    private int id;

    /**
     * TimesheetEntry ArrayList.
     */
    private static ArrayList<TimesheetEntry> timesheetEntries = new ArrayList<TimesheetEntry>();

    /**
     * The week number.
     */
    private int weekNumber;

    /**
     * The date of the ending day of the week.
     */
    private String weekEnding;

    /**
     * The total hours worked in a week.
     */
    private double totalHours;

    /**
     * The total hours worked on Saturday.
     */
    private double totalSat;

    /**
     * The total hours worked on Sunday.
     */
    private double totalSun;

    /**
     * The total hours worked on Monday.
     */
    private double totalMon;

    /**
     * The total hours worked on Tuesday.
     */
    private double totalTue;

    /**
     * The total hours worked on Wednesday.
     */
    private double totalWed;

    /**
     * The total hours worked on Thursday.
     */
    private double totalThu;

    /**
     * The total hours worked on Friday.
     */
    private double totalFri;

    /**
     * A flag to tell if the timeheet can be edited.
     */
    private boolean editable;

    /**
     * The number of rows that a timesheet starts out with.
     */
    private final int rows = 5;

    /**
     * The number of rows that a timesheet has.
     */
    private int numRows;

    /**
     * The manager for timesheet entries.
     */
    @Inject
    private TimesheetEntryManager timesheetEntryManager;

    /**
     * Timesheet constructor that doesn't take any arguments.
     */
    public Timesheet() {
        timesheetEntries = new ArrayList<TimesheetEntry>();
        setEditable(false);
        for (int i = 1; i <= rows; i++) { // enters 5 empty rows
            createEntry(i);
        }
        id = 0;
        weekNumber = 0;
        weekEnding = null;
        totalHours = 0.0;
        setTotalSat(0.0);
        setTotalSun(0.0);
        setTotalMon(0.0);
        setTotalTue(0.0);
        setTotalWed(0.0);
        setTotalThu(0.0);
        setTotalFri(0.0);
        editable = false;
        numRows = timesheetEntries.size();
    }

    /**
     * Timesheet constructor that does take arguments.
     * 
     * @param weeknum
     *            - week number
     * @param weekEnd
     *            - day that the week ends
     * @param tHours
     *            - total hours worked
     */
    public Timesheet(int userid, int weeknum, String weekEnd, double tHours,
            double totalSat, double totalSun, double totalMon, double totalTue,
            double totalWed, double totalThu, double totalFri) {
        id = userid;
        weekNumber = weeknum;
        weekEnding = weekEnd;
        totalHours = tHours;
        this.totalSat = totalSat;
        this.totalSun = totalSun;
        this.totalMon = totalMon;
        this.totalTue = totalTue;
        this.totalWed = totalWed;
        this.totalThu = totalThu;
        this.totalFri = totalFri;
        editable = false;
    }

    /**
     * The getter for the user.
     * 
     * @return the user
     */
    public LoginBean getUser() {
        return user;
    }

    /**
     * The setter for the user.
     * 
     * @param newValue
     *            the user to be set
     */
    public void setUser(LoginBean newValue) {
        user = newValue;
    }

    /**
     * Getter for timesheetEntries.
     * 
     * @return timesheetEntries - The rows of a timesheet
     */
    public ArrayList<TimesheetEntry> getTimesheetEntries() {
        return timesheetEntries;
    }

    /**
     * Setter for timesheetEntries.
     * 
     * @param sheet
     *            - the new entry
     */
    public void setTimesheetEntries(ArrayList<TimesheetEntry> sheet) {
        timesheetEntries = sheet;
    }

    /**
     * Sets editable to true if the timesheet's week is the current week.
     */
    public boolean curWeek() {
        Calendar cal = Calendar.getInstance();
        if (getWeekNumber() == (cal.get(Calendar.WEEK_OF_YEAR))) {
            editable = true;
        } else {
            editable = false;
        }

        return editable;
    }

    /**
     * Getter for the week Number.
     * 
     * @return the weekNumber
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * Setter for the week Number.
     * 
     * @param weekNum
     *            the weekNumber to set
     */
    public void setWeekNumber(final int weekNum) {
        this.weekNumber = weekNum;
    }

    /**
     * Setter for the week Ending.
     * 
     * @return the weekEnding
     */
    public String getWeekEnding() {
        return weekEnding;
    }

    /**
     * Setter for the week ending.
     * 
     * @param weekEnd
     *            the weekEnding to set
     */
    public void setWeekEnding(final String weekEnd) {
        this.weekEnding = weekEnd;
    }

    /**
     * Getter for the total hours.
     * 
     * @return the totalHours
     */
    public double getTotalHours() {
        return totalHours;
    }

    /**
     * Setter for the total hours.
     * 
     * @param tHours
     *            the totalHours to set
     */
    public void setTotalHours(double tHours) {
        totalHours = tHours;
    }

    /**
     * @return the totalSat
     */
    public double getTotalSat() {
        return totalSat;
    }

    /**
     * @param totalSat
     *            the totalSat to set
     */
    public void setTotalSat(double totalSat) {
        this.totalSat = totalSat;
    }

    /**
     * @return the totalSun
     */
    public double getTotalSun() {
        return totalSun;
    }

    /**
     * @param totalSun
     *            the totalSun to set
     */
    public void setTotalSun(double totalSun) {
        this.totalSun = totalSun;
    }

    /**
     * @return the totalMon
     */
    public double getTotalMon() {
        return totalMon;
    }

    /**
     * @param totalMon
     *            the totalMon to set
     */
    public void setTotalMon(double totalMon) {
        this.totalMon = totalMon;
    }

    /**
     * @return the totalTue
     */
    public double getTotalTue() {
        return totalTue;
    }

    /**
     * @param totalTue
     *            the totalTue to set
     */
    public void setTotalTue(double totalTue) {
        this.totalTue = totalTue;
    }

    /**
     * @return the totalWed
     */
    public double getTotalWed() {
        return totalWed;
    }

    /**
     * @param totalWed
     *            the totalWed to set
     */
    public void setTotalWed(double totalWed) {
        this.totalWed = totalWed;
    }

    /**
     * @return the totalThu
     */
    public double getTotalThu() {
        return totalThu;
    }

    /**
     * @param totalThu
     *            the totalThu to set
     */
    public void setTotalThu(double totalThu) {
        this.totalThu = totalThu;
    }

    /**
     * @return the totalFri
     */
    public double getTotalFri() {
        return totalFri;
    }

    /**
     * @param totalFri
     *            the totalFri to set
     */
    public void setTotalFri(double totalFri) {
        this.totalFri = totalFri;
    }

    /**
     * Adds the first five entries to the timesheet.
     * 
     * @param rowid
     *            the id of the timesheet entry
     */
    public void createEntry(int rowid) {
        timesheetEntries.add(new TimesheetEntry(this.id, this.weekNumber,
                rowid, 0, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null));
    }

    /**
     * Adds the additioanl entries to the timesheet.
     * 
     * @param rowid
     *            the id of the timesheet entry
     */
    public void createAnotherEntry(int rowid) {
        timesheetEntries.add(new TimesheetEntry(this.id, this.weekNumber,
                rowid, 0, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null));
        numRows++;
    }

    /**
     * Directs to the View Timesheet page.
     * 
     * @return String - directs to the View Timesheet page
     */
    public String viewTimesheet() {
        return "viewTimesheet";
    }

    /**
     * Directs to the Create Timesheet page.
     * 
     * @return String - directs to the Create Timesheet page
     */
    public String createTimesheet() {
        return "createTimesheet";
    }

    /**
     * Getter for the editable flag.
     * 
     * @return boolean editable - if the Timesheet is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Setter for the editable variable.
     * 
     * @param edit
     *            - true or false value for the editablility of the timesheet
     */
    public void setEditable(boolean edit) {
        this.editable = edit;
    }

    /**
     * Goes to the edit timesheet page.
     */
    public String edit() {
        return "edit";
    }

    /**
     * Getter for id.
     * 
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id.
     * 
     * @param id
     *            the id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for number of rows.
     */
    public int getNumRows() {
        return timesheetEntries.size();
    }

    /**
     * Setter for number of rows.
     * 
     * @param newValue
     *            new number of rows.
     */
    public void setNumRows(int newValue) {
        this.numRows = newValue;
    }

    /**
     * @return the timesheetEntryManager
     */
    public TimesheetEntryManager getTimesheetEntryManager() {
        return timesheetEntryManager;
    }

    /**
     * @param timesheetEntryManager
     *            the timesheetEntryManager to set
     */
    public void setTimesheetEntryManager(
            TimesheetEntryManager timesheetEntryManager) {
        this.timesheetEntryManager = timesheetEntryManager;
    }

}