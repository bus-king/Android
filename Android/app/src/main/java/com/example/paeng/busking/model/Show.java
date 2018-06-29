package com.example.paeng.busking.model;

import java.io.Serializable;

/**
 * Created by paeng on 2018. 6. 29..
 */

public class Show implements Serializable {

    private int id;
    private String userId;
    private String showName;
    private String showTitle;
    private String showLocation;
    private int showGenre;
    private int showHeart;
    private String showTime;
    private String showDescription;
    private String created_at;

    public void setId(int id){this.id = id; }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public void setShowLocation(String showLocation) {
        this.showLocation = showLocation;
    }

    public void setShowGenre(int showGenre){
        this.showGenre = showGenre;
    }

    public void setShowHeart(int showHeart) { this.showHeart = showHeart; }

    public void setShowTime(String showTime) {this.showTime = showTime; }

    public void setShowDescription(String showDescription) {this.showDescription = showDescription; }

    public int getId() { return this.id; }

    public String getUserId() { return this.userId; }

    public String getShowName() {
        return this.showName;
    }
    public String getShowTitle() {
        return this.showTitle;
    }
    public String getShowLocation() {
        return showLocation;
    }
    public int getShowGenre() { return this.showGenre; }
    public int getShowHeart() { return this.showHeart; }
    public String getShowTime() { return this.showTime; }
    public String getShowDescription() { return this.showDescription; }

    public String getCreated_at() {
        return created_at;
    }

}
