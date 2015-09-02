using MongoDB.Driver;
using Service.Models;
using System;
using System.Web;
using System.Web.Http;

namespace Service.Controllers
{
    public class UserController : ApiController
    {
        private static IMongoDatabase db = new MongoClient("mongodb://safetrip:safetr!p@ds051738.mongolab.com:51738/safetrip").GetDatabase("safetrip");
        private static IMongoCollection<User> userColl;

        public UserController()
        {
            userColl = db.GetCollection<User>("user");
            userColl.Indexes.CreateOneAsync(Builders<User>.IndexKeys.Ascending(d => d.userId));
        }

        public User Get([FromUri] string userId)
        {
            try
            {
                var user = userColl
                    .Find<User>(x => x.userId == userId)
                    .FirstOrDefaultAsync().Result;

                if (user == null)
                {
                    throw new HttpException(200, "No user found");
                }

                return user;
            }
            catch (Exception ex)
            {
                throw new HttpException(500, ex.Message);
            }
        }

        public string Post([FromBody] User user)
        {
            try
            {
                user.userId = Guid.NewGuid().ToString();
                userColl.InsertOneAsync(user);
                return user.userId;
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
    }
}
