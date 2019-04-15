package com.example.user.demotablayout.model;

import android.widget.Switch;

public class time {
    private int tID;
    private String gio;
    private String phut;
    private Switch aSwitch;

    public time(String gio, String phut, Switch aSwitch) {
        this.gio = gio;
        this.phut = phut;
        this.aSwitch = aSwitch;
    }

    public time(int tID, String gio, String phut, Switch aSwitch) {
        this.tID = tID;
        this.gio = gio;
        this.phut = phut;
        this.aSwitch = aSwitch;
    }

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getPhut() {
        return phut;
    }

    public void setPhut(String phut) {
        this.phut = phut;
    }

    public Switch getaSwitch() {
        return aSwitch;
    }

    public void setaSwitch(Switch aSwitch) {
        this.aSwitch = aSwitch;
    }
}
