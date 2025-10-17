package com.tinder.tinder.dto.response;


import com.tinder.tinder.model.Images;
import com.tinder.tinder.model.Interests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class UserMatchResult {
    private Long userId;
    private String fullName;
    private Integer age;
    private String location;
    private Double distanceKm;
    private double finalScore;
    private List<String> imagesList;
    private List<String> interestsList;
    public UserMatchResult() {
    }

    public UserMatchResult(Long userId, String fullName, Integer age, String location, Double distanceKm,
                           double finalScore, List<String> imagesList, List<String> interestsList) {
        this.userId = userId;
        this.fullName = fullName;
        this.age = age;
        this.location = location;
        this.distanceKm = distanceKm;
        this.finalScore = finalScore;
        this.imagesList = imagesList;
        this.interestsList = interestsList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public List<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
    }

    public List<String> getInterestsList() {
        return interestsList;
    }

    public void setInterestsList(List<String> interestsList) {
        this.interestsList = interestsList;
    }
}
