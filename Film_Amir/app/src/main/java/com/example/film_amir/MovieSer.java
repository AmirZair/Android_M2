package com.example.film_amir;

import java.io.Serializable;

public class MovieSer implements Serializable {

    private static final long serialVersionUID = -299482035708790407L;

    private String title;
    private String year;
    private String director;
    private String producer;
    private String cost;
    private byte[] image;

    public MovieSer(String title, String year, String director, String producer, String cost, byte[] image) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.producer = producer;
        this.cost = cost;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
