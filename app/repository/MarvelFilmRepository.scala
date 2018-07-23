package repository

import javax.inject.{Inject, Named, Singleton}
import model.MarvelFilm
import play.api.libs.json.{Json, OWrites}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.ReadPreference



class MongoMarvelFilmRepository @Inject()(
                                           val reactiveMongoApi: ReactiveMongoApi,
                                           @Named("collectionName") val collectionName: String
                                         ) extends MarvelFilmRepository {

  def create(marvelFilm: MarvelFilm): Future[WriteResult] = {
    coll.flatMap(_.insert(MarvelFilm))
  }

  def get(name: String): Future[Option[MarvelFilm]] = {
    coll.flatMap(_.find(Json.obj("name" -> name)).one[MarvelFilm](ReadPreference.primaryPreferred))
  }

}

trait MarvelFilmRepository extends MongoMarvelFilm {

}

trait MongoMarvelFilm extends ReactiveMongoComponents {

  val collectionName: String
  val reactiveMongoApi: ReactiveMongoApi
  val coll: Future[JSONCollection] = reactiveMongoApi.database.map(db => db.collection[JSONCollection](collectionName))

  sealed trait MongoResult

  case object Success extends MongoResult

  case object Failure extends MongoResult

}
