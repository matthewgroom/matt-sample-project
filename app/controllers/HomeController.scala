package controllers

import ch.qos.logback.core.Context
import javax.inject._
import model.{Actor, ActorLibrary, MarvelFilm, MarvelLibrary}
import play.api._
import play.api.data.Form
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.routing.Router
import play.api.routing.Router.Routes
import play.modules.reactivemongo.ReactiveMongoApi
import repository.{MarvelFilmRepository, MongoMarvelFilmRepository}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(mongoMarvelFilmRepository: MongoMarvelFilmRepository, cc: ControllerComponents, messagesApi: MessagesApi) extends AbstractController(cc) with I18nSupport{
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

//  def getMarvelFilm() = Action { implicit request: Request[AnyContent] =>
//    val sortedListAlpha = MarvelLibrary.films.sortBy(_.name)
//    Ok(views.html.getMarvelFilm(sortedListAlpha, "List in Alphabetical Order"))
//  }

  def getMarvelFilm() = Action(parse.json) { implicit request: Request[AnyContent] =>
    val sortedList = mongoMarvelFilmRepository.get(_)
    Ok(views.html.getMarvelFilm(sortedList,"Return Film"))
  }

  def listActors() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.listActors(ActorLibrary.actors, "List of Actors"))
  }

  def sortListByReleaseDate() = Action { implicit request: Request[AnyContent] =>
    val sortListByReleaseDate = MarvelLibrary.films.sortBy(_.release)
    Ok(views.html.getMarvelFilm(sortListByReleaseDate,"List in date order"))
  }

  def sortListByRevenue() = Action { implicit request: Request[AnyContent] =>
    val sortListByRevenue = MarvelLibrary.films.sortBy(_.revenue)
    Ok(views.html.getMarvelFilm(sortListByRevenue, "List in order of revenue created"))
  }

  def addActor() = Action {implicit request: Request[AnyContent] =>
    Ok(views.html.addActor(actorForm))
  }

  def save() = Action { implicit request =>
    marvelFilmForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.addMarvelFilm(formWithErrors))
      },
      marvelFilm => {
        if (MarvelLibrary.doesFilmExist(marvelFilm)){
          BadRequest(views.html.addMarvelFilm(marvelFilmForm.fill(marvelFilm))).flashing("asasd"->"adads")
//          MarvelLibrary.addFilm(marvelFilm)
//          Redirect(routes.HomeController.index())
        } else {
//          BadRequest(views.html.addMarvelFilm(marvelFilmForm.fill(marvelFilm).withGlobalError("Dup Film")))
          MarvelLibrary.addFilm(marvelFilm)
          Redirect(routes.HomeController.index())
        }
      }
    )
  }

  def saveActor() = Action { implicit request =>
    actorForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.addActor(formWithErrors))
      },
      actor => {
        if (ActorLibrary.doesActorExist(actor)) {
          BadRequest(views.html.index()).flashing("adasd" -> "adadad")
        } else {
          ActorLibrary.addActor(actor)
          Redirect(routes.HomeController.index())
        }
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

  val actorForm = Form[Actor](
    mapping(
      "name" -> text,
      "age" -> number
    )(Actor.apply)(Actor.unapply)
  )

}
