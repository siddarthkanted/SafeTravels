using MongoDB.Bson;

namespace Service.Models
{
    public class Trip
    {
        public ObjectId _id { get; set; }

        public string reviewerId { get; set; }

        public string tripId { get; set; }

        public string driverName { get; set; }

        public string vehicleNumber { get; set; }

        public string contactNumber { get; set; }
    }
}
