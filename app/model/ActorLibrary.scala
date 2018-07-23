package model

object ActorLibrary {

  var actors = List(Actor("Robert Downey Jr.", 53),Actor("Chris Evans",36),Actor("Samuel L. Jackson", 69))

  def addActor(actor: Actor) ={
    if(doesActorExist(actor) == false){
      actors = actors :+ actor
    }
  }

  def getActor(actor: Actor): Actor ={
    if(actors.contains(actor)) {
      actor
    } else {
      actor
    }
  }

  def doesActorExist(actor: Actor): Boolean ={
    if(!actors.exists(act => act.name == act.name)) {
      false
    }else{
      true
    }
  }

}
