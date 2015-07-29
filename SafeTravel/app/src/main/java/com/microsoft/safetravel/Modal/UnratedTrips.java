package com.microsoft.safetravel.Modal;

/**
 * Created by sikanted on 7/29/2015.
 */
/*
"reviewTime": "2015-07-29T15:53:04.844Z",
    "_id": "55b8f6e026a0c60740a70ec9",
    "reviewerId": "a73042ee-63bf-4aed-ac27-7eca418b860e",
    "reviewerName": "Shiva Prasad",
    "driverId": "dc634c8d-2b20-4d1e-bdc1-588f2895a80f",
    "tripId": "C17913273",
    "drivingSpeedRating": 0,
    "driverBehaviorRating": 0,
    "vehicleConditionRating": 0,
    "driverRecommendation": false,
    "reviewComment": null,
    "status": 0
 */
public class UnratedTrips {
    private String reviewerId;
    private String tripId;
    private String reviewTime;
    private String driverId;
    private String driverName;

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
