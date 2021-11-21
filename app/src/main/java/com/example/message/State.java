package com.example.message;

public class State {

    private String lastMessage;
    private String time;
    private String lastUser; // Last user in the message
    private String title;  // Title of Message
    private int profileResource; // Profile Photo


    public State(String title, String lastMessage, int profileResource, String time, String lastUser) {

        this.lastUser = "";
        this.title = title;
        this.profileResource = profileResource;
        this.time = time;
        this.lastMessage = lastMessage;
    }

    public String getLastUser() {
        return this.lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProfileResource() {
        return this.profileResource;
    }

    public void setProfileResource(int profileResource) {
        this.profileResource = profileResource;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
