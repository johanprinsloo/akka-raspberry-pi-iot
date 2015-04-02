package io.pi

import java.net.{NetworkInterface, InetAddress}

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import com.typesafe.config.ConfigFactory
import io.pi.Messages.{HealthCheck, Report}

//internal messages
case class Heartbeat()

class ThingMicroBot extends Actor {

  val applog = Logging(context.system, this)
  val myNet : InetAddress = InetAddress.getLocalHost
  val myIp : String = myNet.getHostAddress
  val myMac : String = NetworkInterface.getByInetAddress(myNet).getHardwareAddress.map("%02X" format _).mkString
  val myId : String = myNet.getCanonicalHostName + ":" + myMac
  val config = ConfigFactory.load("microbot")
  val serverAddress = config.getString("io.pi.server.address")
  val serverPort = config.getInt("io.pi.server.port")


  override def receive: Receive = {
    case HealthCheck( serverAddress ) => {
      sender() ! Report(myId, "OK")
      applog.info("received a health check message from" + serverAddress)
    }
  }
}
