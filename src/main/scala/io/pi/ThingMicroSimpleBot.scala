package io.pi

import com.pi4j.io.gpio._
import com.pi4j.system.{NetworkInfo, SystemInfo}
import java.net.{NetworkInterface, InetAddress}
import java.time.Instant

import akka.actor.Actor
import akka.event.Logging
import com.typesafe.config.ConfigFactory
import io.pi.Messages.Measurement
import scala.concurrent.duration._

//internal messages
case class BotHeartbeat()

class ThingMicroSimpleBot extends Actor {

  import context.dispatcher

  val applog = Logging(context.system, "io.pi.applog")
  val myNet : InetAddress = InetAddress.getLocalHost
  val config = ConfigFactory.load("microbot")
  val serverActor = context.actorSelection(config.getString("io.pi.server.actor.path"))
  // see http://www.ietf.org/rfc/rfc3986.txt

  val sw1 = systemInfoDescription() match {
    case Some(msg) => {
      applog.info(msg)
      applog.info(systemResources())
      Some( prepPins(GpioFactory.getInstance()) )
    }
    case None => None
  }

  context.system.scheduler.schedule(2 seconds, 500 milliseconds, self, BotHeartbeat)

  override def receive: Receive = {
    case BotHeartbeat => {
      val out = sw1 match {
        case Some(pn) => if( pn.isHigh ) 1.0 else 0.0
        case None => -0.021
      }
      serverActor ! Measurement( Instant.now().getEpochSecond, out, "switch 1" )
      applog.info(s" ${self.path} sent a measurement")
    }
  }

  def systemInfoDescription() : Option[String] = {
    try {
      Some( SystemInfo.getBoardType().name() )
    } catch {
      case ex : Exception => {
        applog.error(ex.getCause, "Could not explore the PI system")
        None
      }
    }
  }

  def systemResources() : String = {
    s"HostName: ${NetworkInfo.getHostname()} \n\t Hardware: ${SystemInfo.getHardware} \n\t " +
      s"Processor: ${SystemInfo.getProcessor} \n\t IP(s) : ${NetworkInfo.getIPAddresses.mkString("\n")}" +
      s"Domain names: ${NetworkInfo.getFQDNs.mkString("\n")}"
  }

  def prepPins( gpio: GpioController) : GpioPinDigitalInput = {
    gpio.provisionDigitalInputPin(  RaspiPin.GPIO_02,             // PIN NUMBER
                                    "Switch1",                   // PIN FRIENDLY NAME (optional)
                                    PinPullResistance.PULL_DOWN); // PIN RESISTANCE (optional)
  }
}
