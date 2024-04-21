package com.example.myapplication.dto;

import java.util.List;

public class Peliculas {

    private String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String name) {
        this.Title = Title;
    }
    private String Director;
    public String getDirector() {
        return Director;
    }

    public void setDirector(String name) {
        this.Director = Director;
    }
    private String Lenguage;
    public String getLenguage() {
        return Lenguage;
    }

    public void setLenguage(String name) {
        this.Lenguage = Lenguage;
    }
    private String Released;
    public String getReleased() {
        return Released;
    }

    public void setReleased(String name) {
        this.Released = Released;
    }
    private String Genre;
    public String getGenre() {
        return Genre;
    }

    public void setGenre(String name) {
        this.Genre = Genre;
    }
    private String Runtime;
    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String name) {
        this.Title = Runtime;
    }
    private String Plot;
    public String getPlot() {
        return Plot;
    }

    public void setPlot(String name) {
        this.Title = Plot;
    }
    private List<Ratings>  Ratings;

    public List<Ratings> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Ratings> Ratings) {
        this.Ratings = Ratings;
    }




}
