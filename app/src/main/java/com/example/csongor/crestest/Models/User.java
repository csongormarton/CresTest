package com.example.csongor.crestest.Models;


import com.orm.SugarRecord;

/**
 * Created by Medea on 2015-12-6.
 */
public class User extends SugarRecord<User> {
    int userID;
    String name;
    String email;
    String password;
    boolean admin;

    public User() {
    }

    public User(int userID, String name, String email, String password, boolean admin) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}
