package com.corejsf;

import java.io.Serializable;

/**
 * A row of information found in a timesheet. Contains the project number,
 * wp ID, notes and days of the week.
 * A TimesheetEntry is added to a Timesheet.
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 *
 */
public class TimesheetEntry implements Serializable {
    
    /**
     * The userid of the user that the 
     * entry belongs to.
     */
    private int id;
    
    /**
     * The week number the entry belongs to.
     */
    private int week;
    
    /**
     * The id of the timesheet entry.
     */
    private int rowid;
    
    /**
     * The project number.
     */
    private int project;

    /**
     * The WP ID.
     */
    private String wp;

    /**
     * Total hours worked.
     */
    private double total;

    /**
     * Hours for Saturday.
     */
    private double sat;

    /**
     * Hours for Sunday.
     */
    private double sun;

    /**
     * Hours for Monday.
     */
    private double mon;

    /**
     * Hours for Tuesday.
     */
    private double tue;

    /**
     * Hours for Wednesday.
     */
    private double wed;

    /**
     * Hours for Thursday.
     */
    private double thu;

    /**
     * Hours for Friday.
     */
    private double fri;

    /**
     * Notes about the week.
     */
    private String notes;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Empty constructor for TimesheetEntry.
     */
    public TimesheetEntry() {
        id = 0;
        week = 0;
        rowid = 0;
        project = 0;
        wp = null;
        total = 0.0;
        sat = 0.0;
        sun = 0.0;
        mon = 0.0;
        tue = 0.0;
        wed = 0.0;
        thu = 0.0;
        fri = 0.0;
        notes = null;
    }

    /**
     * TimesheetEntry constructor that initializes variables.
     * @param id
     * @param week
     * @param rowid
     * @param project
     * @param wp
     * @param total
     * @param sat
     * @param sun
     * @param mon
     * @param tue
     * @param wed
     * @param thu
     * @param fri
     * @param notes
     */
    public TimesheetEntry(int id, int week, int rowid, int project, String wp, double total, double sat,
            double sun, double mon, double tue, double wed,
            double thu, double fri, String notes) {
        this.id = id;
        this.week = week;
        this.rowid = rowid;
        this.project = project;
        this.wp = wp;
        this.total = total;
        this.sat = sat;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.notes = notes;
    }
    
    /**
     * Getter for the project number.
     * @return the project
     */
    public int getProject() {
        return project;
    }

    /**
     * Setter for project.
     * @param project the project to set
     */
    public void setProject(int project) {
        this.project = project;
    }

    /**
     * Getter for wp.
     * @return the wp
     */
    public String getWp() {
        return wp;
    }

    /**
     * Setter for wp.
     * @param wp the wp to set
     */
    public void setWp(String wp) {
        this.wp = wp;
    }

    /**
     * Getter for total.
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Setter for total.
     * @param total the total to set
     */
    public void setTotal(double t) {
        t = this.sat + this.sun + this.mon + this.tue + this.wed + this.thu + this.fri; //dunno if this works
        this.total = t;
    }

    /**
     * Getter for sat.
     * @return the sat
     */
    public double getSat() {
        return sat;
    }

    /**
     * Setter for sat.
     * @param sat the sat to set
     */
    public void setSat(double sat) {
        this.sat = sat;
    }

    /**
     * Getter for sun.
     * @return the sun
     */
    public double getSun() {
        return sun;
    }

    /**
     * Setter for sun.
     * @param sun the sun to set
     */
    public void setSun(double sun) {
        this.sun = sun;
    }

    /**
     * Getter for mon.
     * @return the mon
     */
    public double getMon() {
        return mon;
    }

    /**
     * Setter for mon.
     * @param mon the mon to set
     */
    public void setMon(double mon) {
        this.mon = mon;
    }

    /**
     * Getter for tue.
     * @return the tue
     */
    public double getTue() {
        return tue;
    }

    /**
     * Seter for tue.
     * @param tue the tue to set
     */
    public void setTue(double tue) {
        this.tue = tue;
    }

    /**
     * Getter fo wed.
     * @return the wed
     */
    public double getWed() {
        return wed;
    }

    /**
     * Setter for wed.
     * @param wed the wed to set
     */
    public void setWed(double wed) {
        this.wed = wed;
    }

    /**
     * Getter for thu.
     * @return the thu
     */
    public double getThu() {
        return thu;
    }

    /**
     * Setter for thu.
     * @param thu the thu to set
     */
    public void setThu(double thu) {
        this.thu = thu;
    }

    /**
     * Getter for fri.
     * @return the fri
     */
    public double getFri() {
        return fri;
    }

    /**
     * Setter for Fri.
     * @param fri the fri to set
     */
    public void setFri(double fri) {
        this.fri = fri;
    }

    /**
     * Getter for notes.
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Setter for notes.
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Getter for the week number.
     * @return week - the week number
     */
    public int getWeek() {
        return week;
    }

    /**
     * A setter for the week number.
     * @param week - the week the entry will belong to
     */
    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * A getter for the id of the row.
     * @return rowid - the id of the row
     */
    public int getRowid() {
        return rowid;
    }

    /**
     * A setter for rowid.
     * @param rowid - the id of the row
     */
    public void setRowid(int rowid) {
        this.rowid = rowid;
    }
}