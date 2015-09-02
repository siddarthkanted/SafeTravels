using MongoDB.Driver;
using Service.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;

namespace Service.Controllers
{
    public class ReviewController : ApiController
    {
        private static IMongoDatabase db = new MongoClient("mongodb://safetrip:safetr!p@ds051738.mongolab.com:51738/safetrip").GetDatabase("safetrip");
        private static IMongoCollection<Review> reviewColl;

        public ReviewController()
        {
            reviewColl = db.GetCollection<Review>("review");
            reviewColl.Indexes.CreateOneAsync(Builders<Review>.IndexKeys.Ascending(d => d.tripId));
            reviewColl.Indexes.CreateOneAsync(Builders<Review>.IndexKeys.Ascending(d => d.driverId));
            reviewColl.Indexes.CreateOneAsync(Builders<Review>.IndexKeys.Ascending(d => d.reviewerId));
            reviewColl.Indexes.CreateOneAsync(Builders<Review>.IndexKeys.Ascending(d => d.status));
        }

        public List<Review> Get([FromUri] string driverId = "", string reviewerId = "", ReviewStatus status = ReviewStatus.RATED)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(driverId) && string.IsNullOrWhiteSpace(reviewerId))
                {
                    return null;
                }

                var builder = Builders<Review>.Filter;
                FilterDefinition<Review> filter = null;
                if (!string.IsNullOrWhiteSpace(driverId) && !string.IsNullOrWhiteSpace(reviewerId))
                {
                    filter = builder.Eq("driverId", driverId) & builder.Eq("reviewerId", reviewerId) & builder.Eq("status", status);
                }
                else if (!string.IsNullOrWhiteSpace(driverId))
                {
                    filter = builder.Eq("driverId", driverId) & builder.Eq("status", status);
                }
                else if (!string.IsNullOrWhiteSpace(reviewerId))
                {
                    filter = builder.Eq("reviewerId", reviewerId) & builder.Eq("status", status);
                }

                var reviews = reviewColl
                    .Find<Review>(filter)
                    .SortByDescending(x => x.reviewTime)
                    .ToListAsync().Result;

                Parallel.ForEach(reviews, review =>
                        AddReviewerInfo(review)
                    );
                Parallel.ForEach(reviews, review =>
                        AddDriverInfo(review)
                    );

                return reviews;
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        // POST api/values
        public string Post([FromBody] Review review)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(review.tripId))
                {
                    review.tripId = Guid.NewGuid().ToString();
                }
                review.reviewTime = DateTime.UtcNow;
                reviewColl.InsertOneAsync(review);

                return review.tripId;
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        // PUT api/values/5
        public void Put([FromBody] Review review)
        {
            try
            {
                review.reviewTime = DateTime.UtcNow;

                var filter = Builders<Review>.Filter.Eq(s => s.tripId, review.tripId);
                var update = Builders<Review>.Update
                    .Set(x => x.drivingSpeedRating, review.drivingSpeedRating)
                    .Set(x => x.driverBehaviorRating, review.driverBehaviorRating)
                    .Set(x => x.vehicleConditionRating, review.vehicleConditionRating)
                    .Set(x => x.driverRecommendation, review.driverRecommendation)
                    .Set(x => x.reviewComment, review.reviewComment)
                    .Set(x => x.status, review.status);

                var r = reviewColl.UpdateOneAsync(filter, update);
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        private void AddReviewerInfo(Review review)
        {
            if (review == null)
            {
                return;
            }

            var userController = new UserController();
            var user = userController.Get(review.reviewerId);
            review.reviewerName = user.name;
        }

        private void AddDriverInfo(Review review)
        {
            if (review == null)
            {
                return;
            }

            var driverController = new DriverController();
            var driver = driverController.Get(review.driverId, false);
            review.driverName = driver.driverName;
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}
