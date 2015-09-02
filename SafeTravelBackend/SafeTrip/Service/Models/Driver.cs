using MongoDB.Bson;
using System;

namespace Service.Models
{
    public class Driver
    {
        public ObjectId _id { get; set; }

        public string driverId { get; set; }

        public string driverName { get; set; }

        public string licenseNumber { get; set; }

        public string licenseExpiry { get; set; }

        public string vehicleNumber { get; set; }

        public string contactNumber { get; set; }

        public string policeIdNumber { get; set; }

        public int totalReviews { get; set; }

        public int drivingSpeedRating { get; set; }

        public int driverBehaviorRating { get; set; }

        public int vehicleConditionRating { get; set; }

        public int recommendationScore { get; set; }

        public int ratingColor { get; set; }

        public DateTime lastUnratedTripTime { get; set; }

        public string searchColumn { get; set; }
    }
}
