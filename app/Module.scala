package app

import java.time.Clock

import com.google.inject
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import javax.inject.{Inject, Named}
import play.api.Configuration
import reactivemongo.api.commands.WriteResult
import repository.{MarvelFilmRepository, MongoMarvelFilm, MongoMarvelFilmRepository}

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.

  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */

class Module @Inject()(configuration: Configuration) extends AbstractModule {
  val mongoFeature = configuration.getBoolean("feature.mongo").getOrElse(false)
  val collectionName = configuration.getString("feature.mongo").get

  override def configure() ={
    if (mongoFeature) {
      bind(classOf[MarvelFilmRepository]).to(classOf[MongoMarvelFilmRepository])
    } else {
      bind(classOf[MarvelFilmRepository]).to(classOf[Null])
    }
    bindConstant().annotatedWith(Names.named("collectionName")).to(collectionName)
  }
}