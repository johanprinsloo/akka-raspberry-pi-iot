name := "akka-raspberry-pi-iot"

version := "2.3.9"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-remote" % "2.3.9",
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)



fork in run := true