package com.example.user.demotablayout.model;

import android.widget.Switch;

public class ThoiGian {
    private int mID;
    private String mgio;
    private String mphut;
    private String mtenbaothuc;

    public ThoiGian() {
    }

    public ThoiGian(String mgio, String mphut, String mtenbaothuc) {
        this.mgio = mgio;
        this.mphut = mphut;
        this.mtenbaothuc = mtenbaothuc;
    }

    public ThoiGian(int mID, String mgio, String mphut, String mtenbaothuc) {
        this.mID = mID;
        this.mgio = mgio;
        this.mphut = mphut;
        this.mtenbaothuc = mtenbaothuc;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getMgio() {
        return mgio;
    }

    public void setMgio(String mgio) {
        this.mgio = mgio;
    }

    public String getMphut() {
        return mphut;
    }

    public void setMphut(String mphut) {
        this.mphut = mphut;
    }

    public String getMtenbaothuc() {
        return mtenbaothuc;
    }

    public void setMtenbaothuc(String mtenbaothuc) {
        this.mtenbaothuc = mtenbaothuc;
    }
}
