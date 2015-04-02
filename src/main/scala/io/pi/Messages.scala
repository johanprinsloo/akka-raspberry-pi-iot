package io.pi

import java.net.URI

object Messages {

  // from sensors
  case class RegisterMe( id : String, address : URI )
  case class Report( id : String, statusReport : String )
  case class Measurement( timestamp : Long, datapoint : Double, uom : String )

  // from server
  case class HealthCheck( serverAddress : URI )
  case class ServerError( msg : String )

}
