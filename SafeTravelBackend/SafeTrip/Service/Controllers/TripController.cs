using MongoDB.Driver;
using Service.Models;
using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;

namespace Service.Controllers
{
    public class TripController : ApiController
    {
        private static IMongoDatabase db = new MongoClient("mongodb://safetrip:safetr!p@ds051738.mongolab.com:51738/safetrip").GetDatabase("safetrip");
        private static IMongoCollection<Driver> driverColl;
        private static IMongoCollection<Review> reviewColl;

        public TripController()
        {
            driverColl = db.GetCollection<Driver>("driver");
            reviewColl = db.GetCollection<Review>("review");
        }

        public Review Get([FromUri] string tripId)
        {
            var review = reviewColl
                    .Find<Review>(x => x.tripId == tripId)
                    .FirstOrDefaultAsync().Result;

            return review;
        }

        public void Post([FromBody] List<Trip> trips)
        {
            if (trips == null || trips.Count == 0)
            {
                return;
            }

            trips.ForEach(trip => Put(trip));
        }

        public string Put([FromBody] Trip trip)
        {
            try
            {
                var builder = Builders<Driver>.Filter;
                var filter = builder.Eq(f => f.driverName, trip.driverName)
                    & (builder.Eq(f => f.vehicleNumber, trip.vehicleNumber) | builder.Eq(f => f.contactNumber, trip.contactNumber));

                var driver = driverColl
                    .Find<Driver>(filter)
                    .FirstOrDefaultAsync().Result;

                if (driver == null)
                {
                    //throw new HttpException(500, "Driver not found");
                    return "";
                }

                var existingTrip = Get(trip.tripId);
                if (existingTrip != null)
                {
                    return trip.tripId;
                }

                // create review
                var reviewController = new ReviewController();
                var review = new Review();
                review.tripId = trip.tripId;
                review.reviewerId = trip.reviewerId;
                review.driverId = driver.driverId;
                review.status = ReviewStatus.UNRATED;

                return reviewController.Post(review);
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}
