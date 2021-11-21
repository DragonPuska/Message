package com.example.message;

public class StateMessage {

    public String name;
    public String time;
    public String nick;

    public StateMessage(String name, String time, String nick) {
        this.name = name;
        this.time = time;
        this.nick = nick;
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}