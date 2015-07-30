package com.microsoft.safetravel.Modal;

/**
 * Created by sikanted on 7/28/2015.
 */
public class DriverProfile {
    private String driverId;
    private String driverName;
    private String licenseNumber;
    private String contactNumber;
    private String policeIdNumber;
    private String licenseExpiry;
    private int ratingColor;
    private int recommendationScore;
    private int drivingSpeedRating;
    private int driverBehaviorRating;
    private int vehicleConditionRating;

    public int getDrivingSpeedRating() {
        return drivingSpeedRating;
    }

    public void setDrivingSpeedRating(int drivingSpeedRating) {
        this.drivingSpeedRating = drivingSpeedRating;
    }

    public int getDriverBehaviorRating() {
        return driverBehaviorRating;
    }

    public void setDriverBehaviorRating(int driverBehaviorRating) {
        this.driverBehaviorRating = driverBehaviorRating;
    }

    public int getVehicleConditionRating() {
        return vehicleConditionRating;
    }

    public void setVehicleConditionRating(int vehicleConditionRating) {
        this.vehicleConditionRating = vehicleConditionRating;
    }

    public int getRecommendationScore() {
        return recommendationScore;
    }

    public void setRecommendationScore(int recommendationScore) {
        this.recommendationScore = recommendationScore;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPoliceIdNumber() {
        return policeIdNumber;
    }

    public void setPoliceIdNumber(String policeIdNumber) {
        this.policeIdNumber = policeIdNumber;
    }

    public String getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(String licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public int getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(int ratingColor) {
        this.ratingColor = ratingColor;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    private int totalReviews;
}
