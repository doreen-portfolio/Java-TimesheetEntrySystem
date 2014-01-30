package com.corejsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * Handle CRUD actions for Timesheet class.
 * 
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 * 
 */
@Named("timesheetManager")
@ApplicationScoped
public class TimesheetManager {

    /** dataSource for connection pool on JBoss AS 7 or higher. */
    @Resource(mappedName = "java:jboss/datasources/timesheet")
    private DataSource ds;

    /**
     * Finds Timesheets based on the ID of the user.
     * 
     * @param id
     *            the user's ID
     * @return the Timesheets of a user
     */
    public Timesheet[] findTimesheets(int id) {
        Statement stmt = null;
        Connection connection = null;
        ArrayList<Timesheet> t = new ArrayList<Timesheet>();
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt
                            .executeQuery("SELECT * FROM Timesheets "
                                    + "WHERE UserID = '" + id + "'");
                    while (result.next()) {
                        Timesheet sheet = new Timesheet(id,
                                result.getInt("WeekNumber"),
                                result.getString("WeekEnding"),
                                result.getDouble("TotalHours"),
                                result.getDouble("TotalSat"),
                                result.getDouble("TotalSun"),
                                result.getDouble("TotalMon"),
                                result.getDouble("TotalTue"),
                                result.getDouble("TotalWed"),
                                result.getDouble("TotalThu"),
                                result.getDouble("TotalFri"));
                        t.add(sheet);
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }

                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in find " + id);
            ex.printStackTrace();
            return null;
        }
        Timesheet[] timesheetArray = new Timesheet[t.size()];
        return t.toArray(timesheetArray);
    }

    /**
     * Returns one timesheet specified by user id and week number.
     * 
     * @param id - the userid of the owner of the timesheet.
     * @param weekNum - the weekNum of the timesheet.
     * @return the timesheet to be found.
     */
    public Timesheet findTimesheet(int id, int weekNum) {
        Statement stmt = null;
        Connection connection = null;
        Timesheet sheet = new Timesheet();
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt
                            .executeQuery("SELECT * FROM Timesheets "
                                    + "WHERE UserID = '" + id
                                    + "' AND WeekNumber ='" + weekNum + "'");
                    while (result.next()) {
                        sheet = new Timesheet(id, result.getInt("WeekNumber"),
                                result.getString("WeekEnding"),
                                result.getDouble("TotalHours"),
                                result.getDouble("TotalSat"),
                                result.getDouble("TotalSun"),
                                result.getDouble("TotalMon"),
                                result.getDouble("TotalTue"),
                                result.getDouble("TotalWed"),
                                result.getDouble("TotalThu"),
                                result.getDouble("TotalFri"));
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }

                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in findTimesheet");
            ex.printStackTrace();
            return null;
        }
        return sheet;
    }

    /**
     * Add Timesheet record into database.
     * 
     * @param timesheet - the record to be added.
     */
    public void add(Timesheet timesheet) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection
                            .prepareStatement("INSERT INTO Timesheets VALUES" 
                                    +" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    stmt.setInt(1, timesheet.getId());
                    stmt.setInt(2, timesheet.getWeekNumber());
                    stmt.setString(3, timesheet.getWeekEnding());
                    stmt.setDouble(4, timesheet.getTotalHours());
                    stmt.setDouble(5, timesheet.getTotalSat());
                    stmt.setDouble(6, timesheet.getTotalSun());
                    stmt.setDouble(7, timesheet.getTotalMon());
                    stmt.setDouble(8, timesheet.getTotalTue());
                    stmt.setDouble(9, timesheet.getTotalWed());
                    stmt.setDouble(10, timesheet.getTotalThu());
                    stmt.setDouble(11, timesheet.getTotalFri());
                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in persist " + timesheet);
            ex.printStackTrace();
        }
    }

    /**
     * merge Timesheet record fields into existing database record.
     * 
     * @param timesheet - the record to be merged.
     * @param id - the id of the user.
     */
    public void merge(Timesheet timesheet, int id, int weekNum) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection
                            .prepareStatement("UPDATE Timesheets SET UserId = '"
                                    + id
                                    + "' , WeekNumber = ? , WeekEnding = ? , TotalHours = ? , "
                                    + "TotalSat = ? , TotalSun = ? , TotalMon = ? , TotalTue = ? , "
                                    + "TotalWed = ? , TotalThu = ? , TotalFri = ? "
                                    + "WHERE UserID = '" + id + "' AND weekNumber = '" + weekNum + "'");
                    stmt.setInt(1, timesheet.getWeekNumber());
                    stmt.setString(2, timesheet.getWeekEnding());
                    stmt.setDouble(3, timesheet.getTotalHours());
                    stmt.setDouble(4, timesheet.getTotalSat());
                    stmt.setDouble(5, timesheet.getTotalSun());
                    stmt.setDouble(6, timesheet.getTotalMon());
                    stmt.setDouble(7, timesheet.getTotalTue());
                    stmt.setDouble(8, timesheet.getTotalWed());
                    stmt.setDouble(9, timesheet.getTotalThu());
                    stmt.setDouble(10, timesheet.getTotalFri());
                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }

                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in merge " + timesheet);
            ex.printStackTrace();
        }
    }
}
