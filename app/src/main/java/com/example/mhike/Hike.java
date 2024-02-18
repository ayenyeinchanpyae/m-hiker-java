package com.example.mhike;

import java.io.Serializable;

public class Hike implements Serializable {

    private long id; // Primary key
    private String hikeName; // Name of the hike
    private String location; // Location of the hike
    private String date; // Date of the hike
    private String parkingAvailable; // Parking availability (Yes or No)
    private Integer hikeLength; // Length of the hike
    private String difficultyLevel; // Level of difficulty
    private String description; // Description
    private String trailConditions; // Optional: Trail conditions
    private String recommendedGear; // Optional: Recommended gear

    public Hike(String hikeName, String location, String date, String parkingAvailable, Integer hikeLength, String difficultyLevel, String description, String trailConditions, String recommendedGear) {
        this.hikeName = hikeName;
        this.location = location;
        this.date = date;
        this.parkingAvailable = parkingAvailable;
        this.hikeLength = hikeLength;
        this.difficultyLevel = difficultyLevel;
        this.description = description;
        this.trailConditions = trailConditions;
        this.recommendedGear = recommendedGear;
    }

    public Hike(long id,String hikeName, String location, String date, String parkingAvailable, Integer hikeLength, String difficultyLevel, String description, String trailConditions, String recommendedGear) {
        this.hikeName = hikeName;
        this.location = location;
        this.date = date;
        this.parkingAvailable = parkingAvailable;
        this.hikeLength = hikeLength;
        this.difficultyLevel = difficultyLevel;
        this.description = description;
        this.trailConditions = trailConditions;
        this.recommendedGear = recommendedGear;
        this.id = id;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String isParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(String parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public Integer getHikeLength() {
        return hikeLength;
    }

    public void setHikeLength(Integer hikeLength) {
        this.hikeLength = hikeLength;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailConditions() {
        return trailConditions;
    }

    public void setTrailConditions(String trailConditions) {
        this.trailConditions = trailConditions;
    }

    public String getRecommendedGear() {
        return recommendedGear;
    }

    public void setRecommendedGear(String recommendedGear) {
        this.recommendedGear = recommendedGear;
    }
}
