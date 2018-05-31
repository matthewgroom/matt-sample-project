package model

import com.fasterxml.jackson.annotation.JsonInclude.Include
import model.MarvelLibrary.getFilms
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class MarvelLibraryTest extends PlaySpec with GuiceOneAppPerTest with Injecting {

  val testFilm1 = MarvelFilm("Avengers",2018,5000000)
  val testFilm2 = MarvelFilm("Deadpool", 2016, 5000000)

  "addMarvelFilm" should {
    "check empty library" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
        MarvelLibrary.films.size mustBe (0)
    }

    "input one film" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
        MarvelLibrary.addFilm(testFilm1)
        MarvelLibrary.films.size mustBe (1)
    }

    "input two film" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
        MarvelLibrary.addFilm(testFilm1)
        MarvelLibrary.addFilm(testFilm2)
        MarvelLibrary.films.size mustBe (2)
    }

    "input two of the same film" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
        MarvelLibrary.addFilm(testFilm1)
        MarvelLibrary.addFilm(testFilm1)
        MarvelLibrary.films.size mustBe (1)
    }

  }

  "list films" should {
    "with empty library return nothing" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
      MarvelLibrary.films mustBe empty
    }

    "with one film list that film" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
      MarvelLibrary.addFilm(testFilm1)
      MarvelLibrary.films mustBe List(MarvelFilm("Avengers",2018,5000000))
    }

    "with two films list that film" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
      MarvelLibrary.addFilm(testFilm1)
      MarvelLibrary.addFilm(testFilm2)
//      MarvelLibrary.getFilms mustBe "Avengers, Deadpool"
    }


    "with two of the same films list that film" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
      MarvelLibrary.addFilm(testFilm1)
      MarvelLibrary.addFilm(testFilm1)
      MarvelLibrary.films mustBe List(MarvelFilm("Avengers",2018,5000000))
    }

    "with two of the same films list, but different revenue parameters" in {
      MarvelLibrary.films = List.empty[MarvelFilm]
      MarvelLibrary.addFilm(testFilm1)
      MarvelLibrary.addFilm(testFilm1.copy(revenue=123))
      MarvelLibrary.films mustBe List(MarvelFilm("Avengers",2018,5000000))
    }

  }
}
