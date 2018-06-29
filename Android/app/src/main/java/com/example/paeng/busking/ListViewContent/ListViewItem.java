package com.example.paeng.busking.ListViewContent;

/**
 * Created by paeng on 2018. 4. 8..
 */

public class ListViewItem {

    private int genre;
    private String iconUrl;
    private String name;
    private String title;
    private int heart;
    private double distance;

    public void setGenre(int genre){
        this.genre = genre;
    }
    public void setIconUrl(String icon) {
        this.iconUrl = icon ;
    }
    public void setName(String name) {
        this.name = name ;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setHeart(int heart) {
        this.heart = heart ;
    }
    public void setDistance(double distance){
        this.distance = distance ;
    }
    public String getIconUrl() {
        return this.iconUrl;
    }
    public int getGenre(){
        return this.genre;
    }
    public String getName() {
        return this.name ;
    }
    public String getTitle() {
        return this.title ;
    }
    public int getHeart() {
        return this.heart;
    }
    public double getDistance() {
        return this.distance;
    }
}
