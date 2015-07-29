package com.microsoft.safetravel.Modal;

/**
 * Created by sikanted on 7/27/2015.
 */
/*
"reviewTime": "2015-07-27T15:02:35.505Z",
    "_id": "55b6480b26a0c607f01566b7",
    "reviewerId": "reviewer14",
    "driverId": "driver1",
    "tripId": "trip1",
    "drivingSpeedRating": 1,
    "driverBehaviorRating": 2,
    "vehicleConditionRating": 3,
    "driverRecommendation": true,
    "reviewComment": "this is comment1",
    "likes": 0
 */
public class DriverReview {
    private String reviewTime;
    private String reviewerId;
    private String reviewComment;
    private String driverId;
    private String reviewerName;

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }
}
