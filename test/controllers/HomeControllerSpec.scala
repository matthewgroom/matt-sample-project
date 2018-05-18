package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

//    "render the index page from a new instance of controller" in {
//      val controller = new HomeController(stubControllerComponents())
//      val home = controller.index().apply(FakeRequest(GET, "/"))
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("text/html")
//      contentAsString(home) must include ("Marvel Film Database")
//    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Marvel Film Database")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Marvel Film Database")
    }

    "render the add_Marvel_Film" in {
      val request = FakeRequest(GET, "/add_Marvel_Film")
      val add_Marvel_Film = route(app, request).get

      status(add_Marvel_Film) mustBe OK
      contentType(add_Marvel_Film) mustBe Some("text/html")
      contentAsString(add_Marvel_Film) must include ("Add Marvel Film")
    }
  }

}
