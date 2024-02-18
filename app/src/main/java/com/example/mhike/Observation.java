package com.example.mhike;

import java.util.Date;

public class Observation {
    private long id; // Primary key
    private String observation;
    private Date timeOfObservation;
    private String additionalComments;

    private long hikeId; // foreign key

    // Constructor
    public Observation(long hikeId, String observation, Date timeOfObservation, String additionalComments) {
        this.hikeId = hikeId;
        this.observation = observation;
        this.timeOfObservation = timeOfObservation;
        this.additionalComments = additionalComments;

    }

    public Observation(long id, String observation, String additionalComments) {
        this.id = id;
        this.observation = observation;
        this.additionalComments = additionalComments;

    }
    public long getId() {
        return id;
    }

    // Getter and Setter methods for hikeId
    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }

    // Getter and Setter methods for Observation
    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    // Getter and Setter methods for Time of Observation
    public Date getTimeOfObservation() {
        return timeOfObservation;
    }

    public void setTimeOfObservation(Date timeOfObservation) {
        this.timeOfObservation = timeOfObservation;
    }

    // Getter and Setter methods for Additional Comments
    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    // Getter and Setter methods for Image Path
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
}
