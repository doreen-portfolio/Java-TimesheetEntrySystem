package com.corejsf;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.enterprise.context.ConversationScoped;

/**
 * Handle CRUD actions for User class.
 * 
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 * 
 */
@ConversationScoped
public class UserManager implements Serializable {
    /** dataSource for connection pool on JBoss AS 7 or higher. */
    @Resource(mappedName = "java:jboss/datasources/timesheet")
    private DataSource ds;

    /**
     * Find User record from database.
     * 
     * @param id primary key for record.
     * @return the User record with key = id, null if not found.
     */
    public UserBean find(int id) {
        Statement stmt = null;
        Connection connection = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt
                            .executeQuery("SELECT * FROM Users"
                                    + " where userID = '" + id + "'");
                    if (result.next()) {
                        return new UserBean(result.getInt("UserID"),
                                result.getString("UserName"), 
                                result.getString("FirstName"), 
                                result.getString("LastName"), 
                                result.getString("Password"),
                                result.getBoolean("SuperuserStatus"));
                    } else {
                        return null;
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
    }
    
    /**
     * Find User record from database by username.
     * 
     * @param username username for record.
     * @return the User record with specified username, null if not found.
     */
    public UserBean findByUsername(String username) {
        Statement stmt = null;
        Connection connection = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt
                            .executeQuery("SELECT * FROM Users"
                                    + "where Username = '" + username + "'");
                    if (result.next()) {
                        return new UserBean(result.getInt("UserID"),
                                result.getString("UserName"), 
                                result.getString("FirstName"), 
                                result.getString("LastName"), 
                                result.getString("Password"),
                                result.getBoolean("SuperuserStatus"));
                    } else {
                        return null;
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
            System.out.println("Error in find " + username);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Merge User record fields into existing database record.
     * 
     * @param user the record to be merged.
     */
    public void merge(UserBean user) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "UPDATE Users SET UserName = ? , FirstName = ? , LastName = ? , Password = ? "
                                    + "WHERE UserID =  ?");
                    stmt.setString(1, user.getUsername());
                    stmt.setString(2, user.getFname());
                    stmt.setString(3, user.getLname());
                    stmt.setString(4, user.getPassword());
                    stmt.setInt(5, user.getUserid());
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
            System.out.println("Error in merge " + user);
            ex.printStackTrace();
        }
    }

    /**
     * Remove user and its timesheets and timesheet entries 
     * from the database.
     * 
     * @param user record to be removed from database
     */
    public void remove(UserBean user) {
        Connection connection = null;        
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "DELETE FROM Users WHERE UserID =  ?");
                    stmt.setInt(1, user.getUserid());
                    stmt.executeUpdate();
                    stmt2 = connection.prepareStatement("DELETE FROM Timesheets WHERE UserID =  ?");
                    stmt2.setInt(1, user.getUserid());
                    stmt2.executeUpdate();
                    stmt3 = connection.prepareStatement("DELETE FROM TimesheetEntries WHERE UserID =  ?");
                    stmt3.setInt(1, user.getUserid());
                    stmt3.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (stmt2 != null) {
                        stmt2.close();
                    }
                    if (stmt3 != null) {
                        stmt3.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in remove " + user);
            ex.printStackTrace();
        }
    }
    
    /**
     * Add user from database.
     * 
     * @param user record to be added to database
     */
    public void add(UserBean user) {
        Connection connection = null;        
        PreparedStatement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?)");
                    stmt.setInt(1, user.getUserid());
                    stmt.setString(2, user.getUsername());
                    stmt.setString(3, user.getFname());
                    stmt.setString(4, user.getLname());
                    stmt.setString(5, user.getPassword());
                    stmt.setBoolean(6, user.isSuperuserStatus());
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
            System.out.println("Error in add " + user);
            ex.printStackTrace();
        }
    }

    /**
     * Return Users table as array of UserBean.
     * 
     * @return UserBean[] of all records in Users table
     */
    public UserBean[] getAll() {
        Connection connection = null;        
        ArrayList<UserBean> userlist = new ArrayList<UserBean>();
        Statement stmt = null;
        try {
            try {
                connection = ds.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Users ORDER BY UserID");
                    while (result.next()) {
                        userlist.add(new UserBean(
                                result.getInt("UserID"), 
                                result.getString("UserName"), 
                                result.getString("FirstName"), 
                                result.getString("LastName"), 
                                result.getString("Password"),
                                result.getBoolean("SuperuserStatus")));
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
            System.out.println("Error in getAll");
            ex.printStackTrace();
            return null;
        }

        UserBean[] userarray = new UserBean[userlist.size()];
        return userlist.toArray(userarray);
    }
}

