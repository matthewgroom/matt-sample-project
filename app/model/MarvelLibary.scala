package model

import scala.collection.mutable
import scala.collection.immutable

object MarvelLibrary {

  //var filmsI = immutable.List.empty[MarvelFilm]

  var films = List(MarvelFilm("Iron Man", 2008, 318412101),MarvelFilm("Avengers: Infinity War",2018,626441638),MarvelFilm("Captain America: Civil War",2016,120000000))//List.empty[MarvelFilm]


  def addFilm(film: MarvelFilm): Unit = {
   if (!films.contains(film))
     films = films :+ film
  }

//  def addFilm(film: MarvelFilm) = {
//   if (!films.contains(film))
//     films = films :+ film
//  }
//
  def getFilms(film: MarvelFilm): MarvelFilm = {
    if (films.contains(film)) {
      film
    } else {
      film
    }
  }

//  def checkFilmName(film: MarvelFilm) = {
//    val check = films.contains(film)
//  }

}




/*
1. number of films in empty library should be 0
2  add 2 films to the library, total should be 2
3. add a film, total films, total should be 1 (NOT 3)

MarvelLibrary.addFilm( new MarvelFilm("fred",1,2)
var x = MarvelLibrary.getFilms
x + new MarvelFilm("bob", 12, 8)

 */

