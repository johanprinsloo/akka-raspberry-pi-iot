# Akka Raspberry Pi IOT
Raspberry Pi swarm data with Akka 

##Built with
* Akka [akka.io/](http://akka.io/) for resilient networking
* Pi4J [pi4j.com](http://pi4j.com) for driving PI hardware

##How to run
You need [SBT](http://www.scala-sbt.org/) installed on both the servers (machines in the cloud) and clients (Raspberry Pi's in the field).

**To run the server:**

```bash
$ sbt
[info] Loading global plugins from /.sbt/0.13/plugins
[info] Set current project to akka-raspberry-pi-iot (in build file:/Users/pi/wa/se/akka-raspberry-pi-iot/)
> run -server
```

**To run the client:**

```bash
$ sudp sbt
[info] Loading global plugins from /.sbt/0.13/plugins
[info] Set current project to akka-raspberry-pi-iot (in build file:/Users/pi/wa/se/akka-raspberry-pi-iot/)
> run
```

The client has to run a root to get access to the PI hardware.


