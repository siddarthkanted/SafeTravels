using System.Web.Http;
using System.Web.Mvc;

namespace Service.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";

            return View();
        }

        public string Get([FromUri] string id)
        {
            return "JAGADISH H S";
        }
    }
}
