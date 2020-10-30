package com.example.login_at1;

import android.graphics.Bitmap;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private String name,organisation,type,start_date;
    private Bitmap poster;

    public Event() {

    }

    public Event(String name, String organisation, String type, String start_date){   //Bitmap poster) {
        this.name = name;
        this.organisation = organisation;
        this.type = type;
        this.start_date = start_date;
        //this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }
}
