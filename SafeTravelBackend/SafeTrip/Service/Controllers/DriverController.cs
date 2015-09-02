using MongoDB.Driver;
using Service.Models;
using System;
using System.Web;
using System.Web.Http;

namespace Service.Controllers
{
    public class DriverController : ApiController
    {
        private static IMongoDatabase db = new MongoClient("mongodb://safetrip:safetr!p@ds051738.mongolab.com:51738/safetrip").GetDatabase("safetrip");
        private static IMongoCollection<Driver> driverColl;

        public DriverController()
        {
            driverColl = db.GetCollection<Driver>("driver");
            driverColl.Indexes.CreateOneAsync(Builders<Driver>.IndexKeys.Text(d => d.searchColumn));
            driverColl.Indexes.CreateOneAsync(Builders<Driver>.IndexKeys.Ascending(d => d.driverId));
            driverColl.Indexes.CreateOneAsync(Builders<Driver>.IndexKeys.Ascending(d => d.driverName));
            driverColl.Indexes.CreateOneAsync(Builders<Driver>.IndexKeys.Ascending(d => d.vehicleNumber));
        }

        public Driver Get([FromUri] string driverId, bool reviewDetails = true)
        {
            try
            {
                var driver = driverColl
                    .Find<Driver>(x => x.driverId == driverId)
                    .FirstOrDefaultAsync().Result;

                if (driver == null)
                {
                    throw new HttpException(200, "No driver found");
                }

                if (reviewDetails)
                {
                    FillDriverReviewDetails(driver);
                }

                return driver;
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        public string Post([FromBody] Driver driver)
        {
            try
            {
                driver.driverId = Guid.NewGuid().ToString();
                driver.searchColumn = string.Format("{0} {1}", driver.driverName, driver.vehicleNumber);
                driverColl.InsertOneAsync(driver);
                return driver.driverId;
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }

        private void FillDriverReviewDetails(Driver driver)
        {
            var reviewController = new ReviewController();
            var reviews = reviewController.Get(driver.driverId, "", ReviewStatus.RATED);
            if (reviews == null || reviews.Count == 0)
            {
                return;
            }

            driver.totalReviews = reviews.Count;
            driver.recommendationScore = reviews.FindAll(review => review.driverRecommendation).Count * 100 / reviews.Count;
            int ratingSum = 0;
            foreach (var review in reviews)
            {
                ratingSum += (review.drivingSpeedRating + review.driverBehaviorRating + review.vehicleConditionRating + 3 * (review.driverRecommendation ? 1 : 0)) * 100 / 12;
            }
            driver.ratingColor = (ratingSum / 30) % 3;

            var unratedReviews = reviewController.Get(driver.driverId, "", ReviewStatus.UNRATED);
            if (unratedReviews != null && unratedReviews.Count > 0)
            {
                driver.lastUnratedTripTime = unratedReviews[0].reviewTime;
            }

            var drivingSpeedRatingSum = 0.0;
            foreach (var review in reviews)
            {
                drivingSpeedRatingSum += review.drivingSpeedRating;
            }
            driver.drivingSpeedRating = (int) (drivingSpeedRatingSum / reviews.Count) * 100 / 3;

            var driverBehaviorRatingSum = 0.0;
            foreach (var review in reviews)
            {
                driverBehaviorRatingSum += review.driverBehaviorRating;
            }
            driver.driverBehaviorRating = (int)(driverBehaviorRatingSum / reviews.Count) * 100 / 3;

            var vehicleConditionRatingSum = 0.0;
            foreach (var review in reviews)
            {
                vehicleConditionRatingSum += review.vehicleConditionRating;
            }
            driver.vehicleConditionRating = (int)(vehicleConditionRatingSum / reviews.Count) * 100 / 3;
        }
    }
}
