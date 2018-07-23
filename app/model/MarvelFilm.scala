package model

import play.api.libs.json.Json

case class MarvelFilm(name: String, release: Int, revenue: Int)

object MarvelFilm {

  implicit val format = Json.format[MarvelFilm]
}
