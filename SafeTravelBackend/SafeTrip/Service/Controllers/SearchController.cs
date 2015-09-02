using MongoDB.Driver;
using Service.Models;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Web.Http;

namespace Service.Controllers
{
    public class SearchController : ApiController
    {
        private static IMongoDatabase db = new MongoClient("mongodb://safetrip:safetr!p@ds051738.mongolab.com:51738/safetrip").GetDatabase("safetrip");
        private static IMongoCollection<Driver> driverColl;

        public SearchController()
        {
            driverColl = db.GetCollection<Driver>("driver");
        }

        public List<Driver> Get([FromUri] string query)
        {
            var builder = Builders<Driver>.Filter;
            var filter = builder.Regex(x => x.driverName, string.Format("/^{0}/i", query)) | builder.Regex(x => x.vehicleNumber, string.Format("/^{0}/i", query)) | builder.Text(query);

            var drivers = driverColl
                    .Find<Driver>(filter)
                    .Limit(10)
                    .ToListAsync().Result;

            FillDriverReviewDetails(drivers);
            return drivers;
        }

        private void FillDriverReviewDetails(List<Driver> drivers)
        {
            if (drivers == null || drivers.Count == 0)
            {
                return;
            }

            Parallel.ForEach(drivers, driver =>
                    FillDriverReviewDetails(driver)
                );
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
        }
    }
}
