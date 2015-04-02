package io.pi

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Boot extends App {

  for(arg <- args) {
     println("Program arguments: " + arg)
  }

  if( args.contains("-server") ) {
    startThingServerSystem()
  } else {
    startThingBotSystem()
  }

  def startThingServerSystem() : Unit = {
    val system = ActorSystem("ThingServerSystem", ConfigFactory.load("server"))
    system.actorOf(Props[ThingServerSimple], "server")

    println("Started Thing Server  - waiting for messages")
  }

  def startThingBotSystem() : Unit = {
    val system = ActorSystem("ThingMicroBotSystem", ConfigFactory.load("microbot"))
    system.actorOf(Props[ThingMicroSimpleBot], "bot")

    println("Started Thing MicroBot  - botting about now")
  }

}
