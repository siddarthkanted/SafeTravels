using MongoDB.Bson;
using System;

namespace Service.Models
{
    public enum ReviewStatus { UNRATED, RATED, DELETED };

    public class Review
    {
        public ObjectId _id { get; set; }

        public string reviewerId { get; set; }

        public string reviewerName { get; set; }

        public string driverId { get; set; }

        public string driverName { get; set; }

        public string tripId { get; set; }

        public int drivingSpeedRating { get; set; }

        public int driverBehaviorRating { get; set; }

        public int vehicleConditionRating { get; set; }

        public bool driverRecommendation { get; set; }

        public string reviewComment { get; set; }

        public ReviewStatus status { get; set; }

        public DateTime reviewTime;
    }
}
