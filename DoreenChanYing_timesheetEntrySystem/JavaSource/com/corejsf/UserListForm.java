package com.corejsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Helper class for displaying, editing, and deleting 
 * existing Users.
 * 
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 */
@Named("userListForm")
@ApplicationScoped
public class UserListForm implements Serializable {

    /** 
     * Manager for User objects. 
     */
    @Inject private UserManager userManager;
    
    /** 
     * The logged in user. 
     */
    @Inject private LoginBean logUser;
    
    /**
     * The list of existing users.
     */
    private List<EditableUser> userlist;
    
    /**
     * A new user.
     */
    private UserBean newUser = new UserBean();
    
    /**
     * A boolean to show if a username is taken.
     */
    private boolean usernameTaken;
    
    /**
     * A boolean to show if a userid is taken.
     */
    private boolean useridTaken = false;
    
    /**
     * A boolean to show if fields are empty;
     */
    private boolean nullField = false;
    
    /**
     * Getter for userlist.
     * @return userlist.
     */
    public List<EditableUser> getUserlist() {
        if (userlist == null) {
            refreshList();
        }
        return userlist;
    }

    /**
     * Setter for userList.
     * @return userlist- The new userList.
     */
    public void setUserlist(List<EditableUser> userlist) {
        this.userlist = userlist;
    }

    /**
     * Refreshes the userlist by adding all users from the database.
     * @return userlist- The new userList.
     */
    public void refreshList() {
        UserBean[] users = userManager.getAll();
        userlist = new ArrayList<EditableUser>();
        for (int i = 0; i < users.length; i++) {
            userlist.add(new EditableUser(users[i]));
        }
    }

    /**
     * Getter for newUser.
     * @return newUser - The new user.
     */
    public UserBean getNewUser() {
        return newUser;
    }

    /**
     * Setter for newUser.
     * @return newUser - The new user.
     */
    public void setNewUser(UserBean newValue) {
        this.newUser = newValue;
    }

    /**
     * Adds a user to the list of existing users 
     * provided that it is valid.
     * @return a Navigation String.
     */
    public String addUser() {
        if (logUser.isLoggedIn()) {
            
            EditableUser user = new EditableUser(new UserBean(
                    newUser.getUserid(), newUser.getUsername(),
                    newUser.getFname(), newUser.getLname(),
                    newUser.getPassword(), false));
            if (newUser.getUsername().equals("")
                    || newUser.getFname().equals("")
                    || newUser.getLname().equals("")
                    || newUser.getPassword().equals("")) {
                setNullField(true);
            } else {
                setNullField(false);
            }
            
            if (!hasUsername(newUser.getUsername())
                    && !hasUserid(newUser.getUserid())
                    && newUser.getUserid() > 0 && nullField == false) {
                setUsernameTaken(false);
                setUseridTaken(false);
                userlist.add(user);
                userManager.add(new UserBean(newUser.getUserid(), newUser
                        .getUsername(), newUser.getFname(), newUser.getLname(),
                        newUser.getPassword(), false));
                newUser.clear();
                return "adminMenu";
            }
            if (hasUsername(newUser.getUsername())) {
                setUsernameTaken(true);
            } else {
                setUsernameTaken(false);
            }
            if (hasUserid(newUser.getUserid())) {
                setUseridTaken(true);
            } else {
                setUseridTaken(false);
            }
            return null;
        } else {
            return "login";
        }

    }

    /**
     * Delete the user and return to the same page.
     * @param u the user to be deleted
     * @return Navigation string
     */
    public String deleteRow(UserBean u) {
        if (logUser.isLoggedIn()) {
            userManager.remove(u);
            userlist.remove(u);
            //timesheetManager.remove(u);
            refreshList();
            return null;
        } else {
            return "login";
        }
    }

    /**
     * Saves the users and returns to the same page.
     * @return Navigation string
     */
    public String save() {
        if (logUser.isLoggedIn()) {
            for (EditableUser u : userlist) {
                if (u.isEditable()) { 
                    setUsernameTaken(false);
                    userManager.merge(u.getUser());
                    u.setEditable(false);
                }
            }
            return null;
        } else {
            return "logout";
        }
    }

    /**
     * Redirects to the Create User page.
     * @return Navigation String
     */
    public String goToCreateNewUser() {
        setUsernameTaken(false);
        if (logUser.isLoggedIn()) {
            return "createNewUser";
        } else {
            return "login";
        }
    }

    /**
     * Looks for specified username in list of existing users.
     * @param s - the username to look for.
     * @return true or false. Returns true if it was found. 
     * Returns false if it was not found.
     */
    public boolean hasUsername(String s) {
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getUser().getUsername().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Looks for specified userid in list of existing users.
     * @param id - the userid to look for.
     * @return true or false. Returns true if it was found. 
     * Returns false if it was not found.
     */
    public boolean hasUserid(int id) {
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getUser().getUserid() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the User that was specified.
     * @param s - the username to look for.
     * @return the user or null.
     */
    public UserBean getUserByUsername(String s) {
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getUser().getUsername().equalsIgnoreCase(s)) {
                return userlist.get(i).getUser();
            }
        }
        return null;
    }

    /**
     * Boolean to indicate if username is taken.
     * @return the isUsernameTaken
     */
    public boolean isUsernameTaken() {
        return usernameTaken;
    }

    /**
     * Setter to indicate if username is taken.
     * @param isUsernameTaken the isUsernameTaken to set
     */
    public void setUsernameTaken(boolean newValue) {
        this.usernameTaken = newValue;
    }

    /**
     * Boolean to indicate if userid is taken.
     * @return the isUseridTaken
     */
    public boolean isUseridTaken() {
        return useridTaken;
    }

    /**
     * Setter to indicate if userid is taken.
     * @param isUseridTaken the isUseridTaken to set
     */
    public void setUseridTaken(boolean newValue) {
        this.useridTaken = newValue;
    }

    /**
     * Redirects users to main menu page.
     * @return Navigation string
     */
    public String goToMain() { 
        if (logUser.isLoggedIn()) {
            newUser.setInvalidID(false);
            cleanupErrors();
            if (logUser.getLogUser().isSuperuserStatus()) {
                return "adminMenu";
            } else {
                return "userMenu";
            }
        } else {
            return "login";
        }
    }

    /**
     * Turns off all error flags.
     */
    public void cleanupErrors() {
        setUseridTaken(false);
        setUsernameTaken(false);
        setNullField(false);
        newUser.clear();
    }

    /**
     * Boolean to indicate if there are empty fields.
     * @return the nullField
     */
    public boolean isNullField() {
        return nullField;
    }

    /**
     * Setter to indicate if there are empty fields.
     * @param nullField the nullField to set
     */
    public void setNullField(boolean nullField) {
        this.nullField = nullField;
    }

    /**
     * Getter for LogUser.
     * @return the logUser
     */
    public LoginBean getLogUser() {
        return logUser;
    }

    /**
     * Setter for LogUser.
     * @param logUser the logUser to set
     */
    public void setLogUser(LoginBean logUser) {
        this.logUser = logUser;
    }

}
