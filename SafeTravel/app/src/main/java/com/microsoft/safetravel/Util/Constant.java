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
    public static final String driverName = "driverName";
    public static final String tripId = "tripId";
    public static final String drivingSpeedRating = "drivingSpeedRating";
    public static final String driverBehaviorRating = "driverBehaviorRating";
    public static final String vehicleConditionRating = "vehicleConditionRating";
    public static final String driverRecommendation = "driverRecommendation";
    public static final String reviewComment = "reviewComment";
    public static final String defaultDriverId = "dc634c8d-2b20-4d1e-bdc1-588f2895a80f";
    public static final String defaultTripId = "TFS-PP-C17876021";
    public static final String defaultReviewerId="a73042ee-63bf-4aed-ac27-7eca418b860e";
    public static final String status = "status";
    public static final String driversSearchResult = "driversSearchResult";
    public static final String vehicleNumber = "vehicleNumber";
    public static final String contactNumber = "contactNumber";

    //azure url
    public static final String apiUrl = "http://safetrip2.cloudapp.net/";
    public static final String addReviewUrlPath = "api/review";
    public static final String getAllReviewsOfDriver = "api/review?driverId=%s";
    public static final String imageOfReviewer = "Content/Images/%s.png";
    public static final String driverProfile = "api/driver?driverId=%s";
    public static final String getLastTravelledDate = "api/review?driverId=%s&reviewerId=%s&status=0";
    public static final String getDriversList = "api/search?query=%s";
    public static final String addTripPath = "api/trip";
    public static final String getDriverId = "api/search?query=%s %s";
    public static final String getAllUnratedTrips = "api/review?reviewerId=%s&status=0";

    //time format
    public static final String utcTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String displatTimeFormat = "dd MMMM yyyy";

    //boolean
    public static final String yes="Yes";
    public static final String no="No";

    public static final String notificationMessage = "%s users have recommended %s. This is low. Read other user feedback before your travel on %s";
}
