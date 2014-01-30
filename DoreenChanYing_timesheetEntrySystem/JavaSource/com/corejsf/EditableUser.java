package com.corejsf;

/**
 * User which has fields indicating it is editable or to be deleted.
 * @author Aleeza Arcangel, Doreen Chan-Ying
 * @version 1.0
 */
public class EditableUser {
    /** Indicates associated user can be edited on a form.*/
    private boolean editable;
    
    /** Holds user to be displayed, edited or deleted.*/
    private UserBean user;
    
    /**
     * Convenience constructor.
     * @param user user to be displayed, edited or deleted.
     */
    public EditableUser(UserBean user) {
        this.user = user;
    }
    
    /**
     * Returns true if associated user is editable.
     * @return the editable state
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Set whether associated user is editable.
     * @param editable the state of editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the user
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserBean user) {
        this.user = user;
    }
}
