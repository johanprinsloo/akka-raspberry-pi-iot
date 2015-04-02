package io.pi

import java.net.URI
import java.time.Instant

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import com.typesafe.config.Config
import io.pi.Messages._
import org.slf4j.LoggerFactory

case class ClientRef( address : URI, ref : ActorRef, id : String, lastStatus : String = "", lastMessageTime : Long = -1L )
case class HeartBeat()

/**
 * Thing Server Actor
 */
class ThingServer extends Actor {

  val applog = Logging(context.system, this)
  val datalog = LoggerFactory.getLogger("io.pi")

  var registry : Map[String,ClientRef] = Map()

  /**
   * Message receive loop
   * @return
   */
  override def receive: Receive = {
    case c : RegisterMe => registry = registry + (c.id -> ClientRef( c.address, sender(), c.id))
    case r : Report => {
      registry.get(r.id) match {
        case Some(cr) => registry =
          registry + (r.id -> ClientRef( cr.address,sender(),
            cr.id,lastStatus = r.statusReport,
            lastMessageTime = Instant.now().getEpochSecond ))
        case None => sender() ! ServerError("the server does not know you - please re-register")
      }
    }
    case t : Measurement => datalog.info(s"${t.timestamp} : ${t.datapoint} : ${t.uom}")
    case HeartBeat => {
      registry.foreach( entry => entry._2.ref ! HealthCheck( new URI( self.path.toString )) )
    }
    case x => applog.warning(s"unknown message to Thing Server : /n/t$x")
  }
}
