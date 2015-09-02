using MongoDB.Bson;

namespace Service.Models
{
    public class User
    {
        public ObjectId _id { get; set; }

        public string userId { get; set; }

        public string name { get; set; }

        public string email { get; set; }

        public string mobile { get; set; }
    }
}
