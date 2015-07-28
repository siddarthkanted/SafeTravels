package com.microsoft.safetravel.Modal;

/**
 * Created by sikanted on 7/28/2015.
 */
public class ReviewersTravelWithDriver {
    private String reviewTime;
    private String tripId;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}
