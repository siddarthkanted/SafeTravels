package com.microsoft.safetravel.Util;

/**
 * Created by sikanted on 7/27/2015.
 */
public class Constant {

    public static final String UTF_8 = "UTF-8";
    public static final String Content_Type = "Content-Type";

    //constants used as key in hashMap
    public static final String reviewerId = "reviewerId";
    public static final String driverId = "driverId";
    public static final String tripId = "tripId";
    public static final String drivingSpeedRating = "drivingSpeedRating";
    public static final String driverBehaviorRating = "driverBehaviorRating";
    public static final String vehicleConditionRating = "vehicleConditionRating";
    public static final String driverRecommendation = "driverRecommendation";
    public static final String reviewComment = "reviewComment";
    public static final String defaultDriverId = "5cab0de8-6aa5-4d85-9b31-1938ab922730";
    public static final String defaultTripId = "0f1f89d9-5ce7-4507-9962-51cf3af235ed";
    public static final String defaultReviewerId="d7c97b44-82d6-4adf-aabe-d9969466770b";
    public static final String status = "status";

    //azure url
    public static final String apiUrl = "http://safetrip2.cloudapp.net/";
    public static final String addReviewUrlPath = "api/review";
    public static final String getAllReviewsOfDriver = "api/review?driverId=%s";
    public static final String imageOfReviewer = "Content/Images/%s.png";
    public static final String driverProfile = "api/driver?driverId=%s";
    public static final String getLastTravelledDate = "api/review?driverId=%s&reviewerId=%s&status=0";
    public static final String getDriversList = "api/search?query=%s";

    //time format
    public static final String utcTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String displatTimeFormat = "dd MMMM yyyy";

    //boolean
    public static final String yes="Yes";
    public static final String no="No";
}
