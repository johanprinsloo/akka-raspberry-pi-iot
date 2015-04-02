name := "akka-raspberry-pi-iot"

version := "2.3.9"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-remote"    % "2.3.9",
  "com.typesafe.akka" %% "akka-slf4j"     % "2.3.9",
  "com.pi4j"          % "pi4j-core"       % "0.0.5",
  "ch.qos.logback"    % "logback-classic" % "1.0.13"
)



fork in run := true