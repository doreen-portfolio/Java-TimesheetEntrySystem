package com.corejsf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Handle CRUD actions for TimesheetEntry class.
 * 
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 * 
 */
public class TimesheetEntryManager {

    /** dataSource for connection pool on JBoss AS 7 or higher. */
    @Resource(mappedName = "java:jboss/datasources/timesheet")
    private DataSource ds;

    /**
     * Finds TimesheetEntries based on the ID of the user and the week number.
     * 
     * @param id the user's ID
     * @param weekNumber the week number of the timesheet
     * @return the TimesheetEntry of a Timesheet
     */
    public TimesheetEntry[] findEntries(int id, int weekNumber) {
        Statement stmt = null;
        Connection connection = null;
        ArrayList<TimesheetEntry> t = new ArrayList<TimesheetEntry>();
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement(); //connects to the database
                    ResultSet result = stmt //a query to get specified timesheet entries
                            .executeQuery("SELECT * FROM TimesheetEntries "
                                    + "WHERE UserID = '" + id
                                    + "' AND WeekNumber = '" + weekNumber + "'");
                    while (result.next()) { //adds all the timesheet entries to the array list
                        t.add(new TimesheetEntry(id, result
                                .getInt("WeekNumber"), result.getInt("RowID"),
                                result.getInt("Project"), result
                                        .getString("WP"), result
                                        .getDouble("TotalHours"), result
                                        .getDouble("Sat"), result
                                        .getDouble("Sun"), result
                                        .getDouble("Mon"), result
                                        .getDouble("Tue"), result
                                        .getDouble("Wed"), result
                                        .getDouble("Thu"), result
                                        .getDouble("Fri"), result
                                        .getString("Notes")));
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
            System.out.println("Error in find " + id + " " + weekNumber);
            ex.printStackTrace();
            return null;
        }
        TimesheetEntry[] timesheetArray = new TimesheetEntry[t.size()];
        return t.toArray(timesheetArray);
    }

    /**
     * Add TimesheetEntry record into database.
     * 
     * @param entry
     *            the record to be persisted.
     */
    public void addEntry(TimesheetEntry entry, int id, int week, int rowid) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection
                            .prepareStatement("INSERT INTO TimesheetEntries VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    stmt.setInt(1, id);
                    stmt.setInt(2, week);
                    stmt.setInt(3, rowid);
                    stmt.setInt(4, entry.getProject());
                    stmt.setString(5, entry.getWp());
                    stmt.setDouble(6, entry.getTotal());
                    stmt.setDouble(7, entry.getSat());
                    stmt.setDouble(8, entry.getSun());
                    stmt.setDouble(9, entry.getMon());
                    stmt.setDouble(10, entry.getTue());
                    stmt.setDouble(11, entry.getWed());
                    stmt.setDouble(12, entry.getThu());
                    stmt.setDouble(13, entry.getFri());
                    stmt.setString(14, entry.getNotes());

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
            System.out.println("Error in persist " + entry);
            ex.printStackTrace();
        }
    }

    /**
     * merge TimesheetEntry record fields into existing database record.
     * 
     * @param entry - the record to be merged
     */
    public void merge(TimesheetEntry entry, int id, int rowid) {
        Connection connection = null;
        PreparedStatement stmt = null;
        int weekNum = entry.getWeek();

        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection
                            .prepareStatement("UPDATE TimesheetEntries SET Project = ? , WP = ? , TotalHours = ? , Sat = ? , Sun = ? , Mon = ? , Tue = ? , Wed = ? , Thu = ? , Fri = ? , Notes = ? "
                                    + "WHERE UserID = '"
                                    + id
                                    + "' AND WeekNumber = '" + weekNum + "' AND RowID = '" + rowid + "'");
                    stmt.setInt(1, entry.getProject());
                    stmt.setString(2, entry.getWp());
                    stmt.setDouble(3, entry.getTotal());
                    stmt.setDouble(4, entry.getSat());
                    stmt.setDouble(5, entry.getSun());
                    stmt.setDouble(6, entry.getMon());
                    stmt.setDouble(7, entry.getTue());
                    stmt.setDouble(8, entry.getWed());
                    stmt.setDouble(9, entry.getThu());
                    stmt.setDouble(10, entry.getFri());
                    stmt.setString(11, entry.getNotes());
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
            System.out.println("Error in merge " + entry);
            ex.printStackTrace();
        }
    }
}
