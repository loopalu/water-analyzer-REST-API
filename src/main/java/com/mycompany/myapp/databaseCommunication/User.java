package com.mycompany.myapp.databaseCommunication;

/**
 * User of the desktop application.
 */
public class User {
    /**
     * Name of the user.
     */
    private String name;
    /**
     * Class that the user belongs to. Shows the access rights. 1 - administrators, 2 - scientists, 3 - regular users.
     */
    private int userClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserClass() {
        return userClass;
    }

    void setUserClass(int userClass) {
        this.userClass = userClass;
    }
}
