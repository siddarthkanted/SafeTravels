package com.microsoft.safetravel.Modal;

/**
 * Created by sikanted on 7/28/2015.
 */
public class DriverSearchResult {
    private String driverId;
    private String driverName;
    private int totalReviews;
    private int ratingColor;

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

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(int ratingColor) {
        this.ratingColor = ratingColor;
    }
}
