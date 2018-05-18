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

  def add_Marvel_Film() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.add_Marvel_Film(marvelFilmForm))
  }

  def get_Marvel_Film() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.get_Marvel_Film(marvelFilmForm))
  }

  def save() = Action { implicit request =>
    marvelFilmForm.bindFromRequest.fold(
      formWithErrors => {
        println("That wasn't correct" + formWithErrors)
        BadRequest(views.html.add_Marvel_Film(formWithErrors))
      },
      marvelFilm => {
        println("New Marvel Film" + marvelFilm)
        MarvelLibrary.addFilm(marvelFilm)
        Redirect(routes.HomeController.index)
      }
    )
  }

  def getMarvelFilm() = Action { implicit request =>
    marvelFilmForm.bindFromRequest.fold(
      formWithErrors => {
        println("That wasn't correct" + formWithErrors)
        BadRequest(views.html.get_Marvel_Film(formWithErrors))
      },
      foundFilm => {
        println(foundFilm)
        MarvelLibrary.getFilm(foundFilm)
        Redirect(routes.HomeController.index)
      }
    )
  }

  val marvelFilmForm = Form[MarvelFilm](
    mapping(
      "name" -> text,
      "release" -> number,
      "revenue" -> number
    )(MarvelFilm.apply)(MarvelFilm.unapply)
  )

}
