package com.tinder.tinder.dto.request;


public class UserUpdate {
    private String email;
    private Integer interestedIn;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(Integer interestedIn) {
        this.interestedIn = interestedIn;
    }
}
