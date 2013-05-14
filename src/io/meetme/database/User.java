package io.meetme.database;

import java.util.Date;

public class User {
    private String name;
    private Date meetDate;
    private String userID;

    User(String userId, String name, Date meetDate) {
	userID = userId;
	this.name = name;
	this.meetDate = meetDate;
    }

    public User() {
    }

    public static User meetUser(String userId, String name) {
	return new User(userId, name, new Date(System.currentTimeMillis()));
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getMeetDate() {
	return meetDate;
    }

    public void setMeetDate(Date meetDate) {
	this.meetDate = meetDate;
    }

    public String getUserID() {
	return userID;
    }

    public void setUserID(String userID) {
	this.userID = userID;
    }

}
