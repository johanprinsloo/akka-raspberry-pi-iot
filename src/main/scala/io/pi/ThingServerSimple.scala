package io.pi

import java.net.URI
import java.time.Instant

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import com.typesafe.config.Config
import io.pi.Messages._
import org.slf4j.LoggerFactory


/**
 * Thing Server Actor
 */
class ThingServerSimple extends Actor {

  val applog = Logging(context.system, "io.pi.applog")
  val datalog = LoggerFactory.getLogger("io.pi.datalog")

  applog.info("STAAART")

  /**
   * Message receive loop
   * @return
   */
  override def receive: Receive = {
    case t : Measurement => {
      datalog.info(s"${t.timestamp} : ${t.datapoint} : ${t.uom}")
    }
    case x => applog.warning(s"unknown message to Thing Server : /n/t$x")
  }
}
