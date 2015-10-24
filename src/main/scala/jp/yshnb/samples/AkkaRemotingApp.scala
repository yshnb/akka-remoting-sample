package jp.yshnb.samples

import akka.actor.{Actor, Props, ActorLogging}
import akka.event.Logging
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object AkkaRemotingApp extends App {
  import AkkaActorsProtocol._

  val clientConfig = ConfigFactory.load("application-client.conf")
  val clientSystem = ActorSystem("remoting-sample", clientConfig)
  val clientActor = clientSystem.actorOf(Props[AkkaClientActor], "clientActor")

  val serverConfig = ConfigFactory.load("application-server.conf")
  val serverSystem = ActorSystem("remoting-sample", serverConfig)
  val serverActor1 = serverSystem.actorOf(Props[AkkaServerActor], "serverActor1")
  val serverActor2 = serverSystem.actorOf(Props[AkkaServerActor], "serverActor2")

  Thread.sleep(500)
  clientActor ! Message("hello AkkaClientActor!")
}

/**
 * ClientActor
 */
class AkkaClientActor extends Actor {
  import AkkaActorsProtocol._

  val serverActor = context.actorSelection("akka.tcp://remoting-sample@127.0.0.1:2552/user/serverActor*")

  override def preStart: Unit = {
    println("AkkaClientActor is starting...")
  }

  override def postStop: Unit = {
    println("AkkaClientActor has been stopped.")
  }

  def receive = {
    case Message(text) =>
      println(s"AkkaClientActor get message from ${sender.path.name}: " + text)
      serverActor ! Message("hello AkkaServerActor")
    case Shutdown =>
      println(s"AkkaClientActor get shutdown message from ${sender.path.name}")
      serverActor ! Shutdown
      context.system.shutdown()
  }
}

object AkkaClientActor {}


/**
 * ServerActor
 */
class AkkaServerActor extends Actor {
  import AkkaActorsProtocol._

  val actorName = self.path.name

  override def preStart: Unit = {
    println(s"AkkaServerActor(${actorName}) is starting...")
  }

  override def postStop: Unit = {
    println(s"AkkaServerActor(${actorName}) has been stopped.")
  }

  def receive = {
    case Message(text) => 
      println(s"AkkaServerActor(${actorName}) get message from ${sender.path.name}: " + text)
      sender ! Shutdown
    case Shutdown =>
      println(s"AkkaServerActor(${actorName}) get shutdown message from ${sender.path.name}")
      context.system.shutdown()
  }
}

object AkkaServerActor {}

object AkkaActorsProtocol {
  case class Message(text: String)
  case class Shutdown()
}
