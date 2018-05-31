package controllers

import ch.qos.logback.core.Context
import javax.inject._
import model.{MarvelFilm, MarvelLibrary}
import play.api._
import play.api.data.Form
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.routing.Router
import play.api.routing.Router.Routes



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, messagesApi: MessagesApi) extends AbstractController(cc) with I18nSupport{

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def addMarvelFilm() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.addMarvelFilm(marvelFilmForm))
  }

  def getMarvelFilm() = Action { implicit request: Request[AnyContent] =>
    val sortedListAlpha = MarvelLibrary.films.sortBy(_.name)  // todo sorted list alphabetically
    Ok(views.html.getMarvelFilm(sortedListAlpha, "List in Alphabetical Order"))
  }

  def sortListByReleaseDate() = Action { implicit request: Request[AnyContent] =>
    val sortListByReleaseDate = MarvelLibrary.films.sortBy(_.release)
    Ok(views.html.getMarvelFilm(sortListByReleaseDate,"List in date order"))
  }

  def sortListByRevenue() = Action { implicit request: Request[AnyContent] =>
    val sortListByRevenue = MarvelLibrary.films.sortBy(_.revenue)
    Ok(views.html.getMarvelFilm(sortListByRevenue, "List in order of revenue created"))
  }

  def save() = Action { implicit request =>
    marvelFilmForm.bindFromRequest.fold(
      formWithErrors => {
        println("That wasn't correct" + formWithErrors)
        BadRequest(views.html.addMarvelFilm(formWithErrors))
      },
      marvelFilm => {
        MarvelLibrary.addFilm(marvelFilm)
        Redirect(routes.HomeController.index())
      }
    )
  }
//
//  def findMarvelFilm() = Action { implicit request =>
//    marvelFilmForm.bindFromRequest.fold(
//      formWithErrors => {
//        println("That wasn't correct" + formWithErrors)
//        BadRequest(views.html.getMarvelFilm(formWithErrors))
//      },
//      foundFilm => {
//        MarvelLibrary.getFilms(foundFilm)
//        Ok(Json.toJson(foundFilm))
//      }
//    )
//  }

  val marvelFilmForm = Form[MarvelFilm](
    mapping(
      "name" -> text,
      "release" -> number,
      "revenue" -> number
    )(MarvelFilm.apply)(MarvelFilm.unapply)
  )

}
