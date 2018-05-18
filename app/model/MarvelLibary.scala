package model

import scala.collection.mutable
import scala.collection.immutable

object MarvelLibrary {

  //var filmsI = immutable.List.empty[MarvelFilm]

  val films = List.empty[MarvelFilm]

  def addFilm(film: MarvelFilm): List[MarvelFilm] = {
    films :+ film
  }
  def getFilm(film: MarvelFilm): List[MarvelFilm] = {
    films
  }
}




/*
1. number of films in empty library should be 0
2  add 2 films to the library, total should be 2
3. add a film, total films, total should be 1 (NOT 3)

MarvelLibrary.addFilm( new MarvelFilm("fred",1,2)
var x = MarvelLibrary.getFilms
x + new MarvelFilm("bob", 12, 8)

 */

